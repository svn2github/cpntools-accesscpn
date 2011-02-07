/************************************************************************/
/* Access/CPN                                                           */
/* Copyright 2010-2011 AIS Group, Eindhoven University of Technology    */
/*                                                                      */
/* This library is free software; you can redistribute it and/or        */
/* modify it under the terms of the GNU Lesser General Public           */
/* License as published by the Free Software Foundation; either         */
/* version 2.1 of the License, or (at your option) any later version.   */
/*                                                                      */
/* This library is distributed in the hope that it will be useful,      */
/* but WITHOUT ANY WARRANTY; without even the implied warranty of       */
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU    */
/* Lesser General Public License for more details.                      */
/*                                                                      */
/* You should have received a copy of the GNU Lesser General Public     */
/* License along with this library; if not, write to the Free Software  */
/* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,           */
/* MA  02110-1301  USA                                                  */
/************************************************************************/
package org.cpntools.accesscpn.model.demos.statespacegenerator;


import gnu.trove.set.hash.TCustomHashSet;
import gnu.trove.strategy.HashingStrategy;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class TroveSetCompressedStateSet implements CompressedStateSet {
	public static final class ByteArrayHashStrategy implements HashingStrategy<byte[]> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public int computeHashCode(final byte[] arg0) {
			return Arrays.hashCode(arg0);
		}

		public boolean equals(final byte[] arg0, final byte[] arg1) {
			return Arrays.equals(arg0, arg1);
		}

	}

	private final Set<byte[]> storage;

	public TroveSetCompressedStateSet() {
		storage = new TCustomHashSet<byte[]>(new ByteArrayHashStrategy());
	}

	public synchronized boolean add(final CompressedState compressed) {
		return storage.add(compressed.getBytes());
	}

	public synchronized void addAll(final Collection<CompressedState> unchecked) {
		for (final CompressedState c : unchecked)
			add(c);
	}

	public synchronized void clear() {
		storage.clear();
	}

	public Iterator<CompressedState> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized void printStats() {
		System.out.println(storage.size());
	}

	public synchronized int size() {
		return storage.size();
	}

}
