package org.cpntools.accesscpn.model.demos.statespacegenerator;


import java.util.Collection;

public interface CompressedStateSet extends Iterable<CompressedState> {
	boolean add(CompressedState compressed);

	void addAll(Collection<CompressedState> unchecked);

	void clear();

	void printStats();

	int size();
}
