package org.cpntools.accesscpn.model.demos.statespacegenerator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;

import nl.tue.storage.CompressedHashSet;
import nl.tue.storage.CompressedStore;
import nl.tue.storage.Deflater;
import nl.tue.storage.EqualOperation;
import nl.tue.storage.HashOperation;
import nl.tue.storage.StorageException;
import nl.tue.storage.impl.CompressedStoreHashSetImpl;
import nl.tue.storage.impl.CompressedStoreHashSetImpl.Result;

public class EfficientStateSet implements CompressedStateSet {
	private CompressedHashSet<CompressedState> storage;
	private final int count;

	public EfficientStateSet(final int count) {
		this.count = count;
		init();
	}

	private void init() {
		storage = new CompressedStoreHashSetImpl.Int32G<CompressedState>(new Deflater<CompressedState>() {

			@Override
            public void deflate(final CompressedState object, final OutputStream stream) throws IOException {
				stream.write(object.getBytes());
			}
		}, 128 * 1024 * 1024, 1024, new EqualOperation<CompressedState>() {
			@Override
            public boolean equals(final CompressedState object, final CompressedStore<CompressedState> store,
			        final long l) throws StorageException, IOException {
				final InputStream stream = store.getStreamForObject(l);
				if (object == null) { return false; }
				for (final byte b : object.getBytes()) {
					if ((byte) stream.read() != b) { return false; }
				}
				return true;
			}
		}, new HashOperation<CompressedState>() {
			@Override
            public int getHashCode(final CompressedState object) {
				return object.hashCode();
			}

			@Override
            public int getHashCode(final CompressedStore<CompressedState> store, final long l) throws StorageException {
				return CompressedState.hashCode(store.getStreamForObject(l), count);
			}

		});
	}

	@Override
	public Iterator<CompressedState> iterator() {
		return null;
	}

	@Override
	public synchronized boolean add(final CompressedState compressed) {
		try {
			final Result<CompressedState> add = storage.add(compressed);
			return add.isNew;
		} catch (final StorageException e) {
			return false;
		}
	}

	@Override
	public synchronized void addAll(final Collection<CompressedState> unchecked) {
		for (final CompressedState state : unchecked) {
			add(state);
		}
	}

	@Override
	public synchronized void clear() {
		init();
	}

	@Override
	public synchronized void printStats() {
		System.out.println("Memory: " + storage.getMemory());
	}

	@Override
	public synchronized int size() {
		return storage.size();
	}

}
