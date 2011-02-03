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

		@Override
		public int computeHashCode(final byte[] arg0) {
			return Arrays.hashCode(arg0);
		}

		@Override
		public boolean equals(final byte[] arg0, final byte[] arg1) {
			return Arrays.equals(arg0, arg1);
		}

	}

	private final Set<byte[]> storage;

	public TroveSetCompressedStateSet() {
		storage = new TCustomHashSet<byte[]>(new ByteArrayHashStrategy());
	}

	@Override
	public synchronized boolean add(final CompressedState compressed) {
		return storage.add(compressed.getBytes());
	}

	@Override
	public synchronized void addAll(final Collection<CompressedState> unchecked) {
		for (final CompressedState c : unchecked)
			add(c);
	}

	@Override
	public synchronized void clear() {
		storage.clear();
	}

	@Override
	public Iterator<CompressedState> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized void printStats() {
		System.out.println(storage.size());
	}

	@Override
	public synchronized int size() {
		return storage.size();
	}

}
