package org.cpntools.accesscpn.model.demos.statespacegenerator;


import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CollectionsCompressedStateSet implements CompressedStateSet {
	private final Set<CompressedState> synchronizedSet;

	public CollectionsCompressedStateSet() {
		synchronizedSet = Collections.synchronizedSet(new HashSet<CompressedState>());
	}

	@Override
	public boolean add(final CompressedState compressed) {
		return synchronizedSet.add(compressed);
	}

	@Override
	public void addAll(final Collection<CompressedState> unchecked) {
		synchronizedSet.addAll(unchecked);
	}

	@Override
	public void clear() {
		synchronizedSet.clear();
	}

	@Override
	public Iterator<CompressedState> iterator() {
		return synchronizedSet.iterator();
	}

	@Override
	public void printStats() {
		System.out.println("Size: " + synchronizedSet.size());

	}

	@Override
	public int size() {
		return synchronizedSet.size();
	}

}
