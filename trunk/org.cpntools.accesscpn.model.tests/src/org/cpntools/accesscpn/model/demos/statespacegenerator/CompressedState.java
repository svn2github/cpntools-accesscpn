/************************************************************************/
/* Access/CPN */
/* Copyright 2010-2011 AIS Group, Eindhoven University of Technology */
/*                                                                      */
/* This library is free software; you can redistribute it and/or */
/* modify it under the terms of the GNU Lesser General Public */
/* License as published by the Free Software Foundation; either */
/* version 2.1 of the License, or (at your option) any later version. */
/*                                                                      */
/* This library is distributed in the hope that it will be useful, */
/* but WITHOUT ANY WARRANTY; without even the implied warranty of */
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU */
/* Lesser General Public License for more details. */
/*                                                                      */
/* You should have received a copy of the GNU Lesser General Public */
/* License along with this library; if not, write to the Free Software */
/* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, */
/* MA 02110-1301 USA */
/************************************************************************/
package org.cpntools.accesscpn.model.demos.statespacegenerator;

/**
 * 
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cpntools.accesscpn.engine.highlevel.instance.Instance;
import org.cpntools.accesscpn.engine.highlevel.instance.InstanceFactory;
import org.cpntools.accesscpn.engine.highlevel.instance.Marking;
import org.cpntools.accesscpn.engine.highlevel.instance.State;
import org.cpntools.accesscpn.model.Place;

final class CompressedState implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final boolean USE_BITMAP = true;

	private static final byte[] pow2 = new byte[] { 1, 2, 4, 8, 16, 32, 64, (byte) 128 };

	public static State decompress(final List<Instance<Place>> allRealPlaceInstances, final int[] indices) {
		StateSpaceGenerator.profileStart();
		final List<Marking> markings = new ArrayList<Marking>();
		int pos = 0;
		for (final Instance<Place> pi : allRealPlaceInstances) {
			Marking marking;
			if (indices[pos] > 0) {
				marking = InstanceFactory.INSTANCE.createMarking(1, CompressedState.getValues().get(indices[pos])
				        .toString());
			} else {
				marking = InstanceFactory.INSTANCE.createMarking(0, "empty");
			}
			pos++;
			marking.setPlaceInstance(pi);
			markings.add(marking);
		}
		StateSpaceGenerator.profileEnd("decompress");
		return InstanceFactory.INSTANCE.createState(markings);
	}

	/**
	 * @return the values
	 */
	public static ArrayList<String> getValues() {
		return CompressedState.values;
	}

	private byte[] bytes;

	private final static Map<String, Integer> strings = new HashMap<String, Integer>();

	private final static ArrayList<String> values = new ArrayList<String>();
	static {
		values.add("empty");
	}

	public static void setValues(final List<String> values) {
		CompressedState.values.clear();
		CompressedState.strings.clear();
		if (values.size() < 1 || !"empty".equals(values.get(0))) {
			values.add("empty");
		}
		for (int i = 0; i < values.size(); i++) {
			CompressedState.values.add(values.get(i));
			CompressedState.strings.put(values.get(i), i);
		}
	}

	private int predecessor;
	private int stateNumber;

	public CompressedState() {
		setBytes(new byte[0]);
	}

	public CompressedState(final int[] indices) {
		init(indices);
	}

	public CompressedState(final List<Instance<Place>> allRealPlaceInstances, final State s) {
		StateSpaceGenerator.profileStart();
		final int[] indices = new int[allRealPlaceInstances.size()];
		int pos = 0;
		for (final Instance<Place> pi : allRealPlaceInstances) {
			final Marking marking = s.getMarking(pi);
			if (marking.getTokenCount() > 0) {
				final String m = marking.getMarking();
				synchronized (CompressedState.getValues()) {
					final Integer id = CompressedState.strings.get(m);
					if (id == null) {
						CompressedState.getValues().add(m);
						final int max = CompressedState.getValues().size() - 1;
						indices[pos] = max;
						CompressedState.strings.put(m, max);
					} else {
						indices[pos] = id;
					}
				}
			} else {
				indices[pos] = 0;
			}
			pos++;
		}
		init(indices);
	}

	public CompressedState(final List<Instance<Place>> allRealPlaceInstances, final InputStream stream) {
		// TODO Auto-generated constructor stub
	}

	public State decompress(final List<Instance<Place>> allRealPlaceInstances) {
		return CompressedState.decompress(allRealPlaceInstances, getIndices(allRealPlaceInstances.size()));
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		final CompressedState other = (CompressedState) obj;
		if (!Arrays.equals(getBytes(), other.getBytes())) { return false; }
		return true;
	}

	public int getArraySize() {
		return getBytes().length;
	}

	/**
	 * @return the bytes
	 */
	public byte[] getBytes() {
		return bytes;
	}

	public int[] getIndices(final int count) {
		StateSpaceGenerator.profileStart();
		final int[] indices = new int[count];
		final ByteArrayInputStream stream = new ByteArrayInputStream(getBytes());
		load(count, indices, stream);
		StateSpaceGenerator.profileEnd("decompress");
		return indices;
	}

	private static byte[] load(final int count, final int[] indices, final InputStream stream) {
		try {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final DataInputStream ois = new DataInputStream(stream);
			final byte[] enabled = new byte[USE_BITMAP ? (count + 9) / 8 : 1];
			ois.readFully(enabled);
			baos.write(enabled);
			int pos = 2;
			final int size = enabled[0] & 3;
			final byte[] buffer = new byte[size + 1];
			for (int i = 0; i < count; i++) {
				int marking;
				if (!USE_BITMAP || (enabled[pos / 8] & CompressedState.pow2[pos % 8]) != 0) {
					marking = 0;
					ois.readFully(buffer);
					baos.write(buffer);
					for (final byte b : buffer) {
						marking = (marking << 8) + (b < 0 ? b + 256 : b);
					}
				} else {
					marking = 0;
				}
				indices[pos - 2] = marking;
				pos++;
			}
			return baos.toByteArray();
		} catch (final IOException e) {
			assert false;
			e.printStackTrace();
			return new byte[0];
		}
	}

	public int getPredecessor() {
		return predecessor;
	}

	public int getStateNumber() {
		return stateNumber;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(getBytes());
	}

	public void setPredecessor(final int predecessor) {
		this.predecessor = predecessor;
	}

	public void setStateNumber(final int stateNumber) {
		this.stateNumber = stateNumber;
	}

	private void init(final int[] indices) {
		StateSpaceGenerator.profileStart();
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			final byte[] enabled = new byte[USE_BITMAP ? (indices.length + 9) / 8 : 1];
			int pos = 2;
			final List<Integer> v = new ArrayList<Integer>(indices.length);
			int max = 0;
			if (USE_BITMAP) {
				for (final int i : indices) {
					if (i > 0) {
						enabled[pos / 8] |= CompressedState.pow2[pos % 8];
						v.add(i);
						max = Math.max(max, i);
					}
					pos++;
				}
			} else {
				for (final int i : indices) {
					v.add(i);
					max = Math.max(max, i);
				}
			}
			int size = 0;
			if (max >= 256 * 256 * 256) {
				size = 3;
			} else if (max >= 256 * 256) {
				size = 2;
			} else if (max >= 256) {
				size = 1;
			}
			enabled[0] |= size;
			baos.write(enabled);
			for (final int value : v) {
				for (int i = size; i >= 0; i--) {
					baos.write((value >>> 8 * i));
				}
			}
			baos.flush();
			StateSpaceGenerator.profileEnd("compress");
			setBytes(baos.toByteArray());
		} catch (final Exception e) {
			assert false;
			e.printStackTrace();
		}
	}

	private void readObject(final java.io.ObjectInputStream in) throws IOException {
		predecessor = in.readInt();
		setBytes(new byte[in.readInt()]);
		in.readFully(getBytes());
	}

	public void readObject(final DataInputStream in) throws IOException {
		predecessor = in.readInt();
		setBytes(new byte[in.readInt()]);
		in.readFully(getBytes());
	}

	/**
	 * @param bytes
	 *            the bytes to set
	 */
	private void setBytes(final byte[] bytes) {
		this.bytes = bytes;
	}

	private void writeObject(final java.io.ObjectOutputStream out) throws IOException {
		out.writeInt(predecessor);
		out.writeInt(getBytes().length);
		out.write(getBytes());
	}

	public void writeObject(final DataOutputStream out) throws IOException {
		out.writeInt(predecessor);
		out.writeInt(getBytes().length);
		out.write(getBytes());
	}

	public static int hashCode(final InputStream in, final int count) {
		return Arrays.hashCode(load(count, new int[count], in));
	}

	boolean isCheckpoint() {
		return getBytes().length == 0;
	}
}