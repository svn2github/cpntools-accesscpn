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


import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.cpntools.accesscpn.engine.highlevel.instance.Instance;
import org.cpntools.accesscpn.model.Place;

import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDDomain;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.JFactory;
import net.sf.javabdd.MicroFactory;

public class BDDCompressedStateSet implements CompressedStateSet {
	private static final int TEST_SIZE = 4;

	public static void dummyCallback() {

	}

	public static void main(final String[] args) {
		final BDDCompressedStateSet instance = new BDDCompressedStateSet(
				BDDCompressedStateSet.TEST_SIZE);
		StringBuilder sb = new StringBuilder();
		final int[] values = new int[BDDCompressedStateSet.TEST_SIZE];
		for (int i = 0; i < BDDCompressedStateSet.TEST_SIZE; i += 2) {
			for (int j = 0; j < BDDCompressedStateSet.TEST_SIZE; j++)
				values[j] = i + j;
			final CompressedState compressed = new CompressedState(values);
			sb.append(instance.add(compressed));
			sb.append(", ");
			// instance.set.printDot();
		}
		instance.set.printSetWithDomains();
		for (final BDDDomain domain : instance.extDomain)
			System.out.println(domain + ": " + Arrays.toString(domain.vars()));
		System.out.println(sb);
		sb = new StringBuilder();
		for (int i = 0; i < BDDCompressedStateSet.TEST_SIZE; i++) {
			for (int j = 0; j < BDDCompressedStateSet.TEST_SIZE; j++)
				values[j] = i + j;
			final CompressedState compressed = new CompressedState(values);
			sb.append(instance.add(compressed));
			sb.append(", ");
		}
		System.out.println(sb);

		for (int i = 0; i < BDDCompressedStateSet.TEST_SIZE; i++)
			System.out.print(instance.extDomain[i].size() + " - ");
		System.out.println();
		System.out.println(instance.set.nodeCount());
		System.out.println(instance.set.satCount());
		// System.out.println(instance.set.toStringWithDomains());
		System.out.println(instance.factory.varNum());
		System.out.println(Arrays.toString(instance.extDomain));
	}

	private final BDD set;
	private final BDDFactory factory;

	private final BDDDomain[] extDomain;

	private final int placeCount;

	int size = 0;

	private static final boolean INTERLEAVED = true;
	private static final boolean REVERSED = true;

	public BDDCompressedStateSet(final int placeCount) {
		this.placeCount = placeCount;
		if (placeCount < 32)
			factory = MicroFactory.init(200, 200);
		else
			factory = JFactory.init(placeCount * 128, 1000);
		factory.autoReorder(BDDFactory.REORDER_WIN2ITE);
		try {
			factory.registerGCCallback(null, null);
		} catch (final Exception _) {

		}
		try {
			factory.registerResizeCallback(null, null);
		} catch (final Exception _) {

		}
		if (BDDCompressedStateSet.INTERLEAVED) {
			final long[] domainSizes = new long[placeCount];
			for (int i = 0; i < placeCount; i++)
				domainSizes[i] = 2l * Integer.MAX_VALUE + 2;

			extDomain = factory.extDomain(domainSizes);
		} else {
			extDomain = new BDDDomain[placeCount];
			for (int i = 0; i < placeCount; i++)
				extDomain[i] = factory.extDomain(2l * Integer.MAX_VALUE + 2);
		}
		set = factory.zero();
	}

	public BDDCompressedStateSet(final List<Instance<Place>> allRealPlaceInstances) {
		this(allRealPlaceInstances.size());
	}

	public synchronized boolean add(final CompressedState compressed) {
		final int[] indices = compressed.getIndices(placeCount);
		final BDD newState = buildState(indices);
		if (newState.and(set).isZero()) {
			set.orWith(newState);
			size += 1;
			return true;
		}
		return false;
	}

	/**
	 * Assumes that all of unchecked is not in set and all are unique (or size calculations will be
	 * wrong)
	 * 
	 * @see CompressedStateSet#addAll(java.util.Collection)
	 */
	public synchronized void addAll(final Collection<CompressedState> unchecked) {
		for (final CompressedState s : unchecked) {
			final int[] indices = s.getIndices(placeCount);
			final BDD newState = buildState(indices);
			set.orWith(newState);
		}
		size += unchecked.size();
	}

	public synchronized void clear() {
		set.andWith(factory.zero());
	}

	
	public Iterator<CompressedState> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized void printStats() {
		System.out.println("Variables: " + placeCount + "; size = " + size);
		System.out.println("SATCount: " + set.satCount() + "; nodeCount: " + set.nodeCount());
		// factory.reorder(BDDFactory.REORDER_WIN2ITE);
		// System.out.println("SATCount: " + set.satCount() + "; nodeCount: " + set.nodeCount());
	}

	public synchronized int size() {
		return size;
	}

	private BDD buildState(final int[] indices) {
		final BDD newState = factory.one();
		for (int i = 0; i < placeCount; i++) {
			long value = indices[i];
			if (value < 0) value = 2l * Integer.MAX_VALUE + 1;
			final BDD varRange = getBDDMatching(BigInteger.valueOf(value), extDomain[i]);
			newState.andWith(varRange);
		}
		return newState;
	}

	private BDD getBDDMatching(BigInteger value, final BDDDomain domain) {
		if (BDDCompressedStateSet.REVERSED)
			return domain.ithVar(value);
		else {
			final BDDFactory factory = domain.getFactory();
			final BDD v = factory.one();
			final int[] ivar = domain.vars();
			for (int n = 0; n < ivar.length; n++) {
				if (value.testBit(0))
					v.andWith(factory.ithVar(ivar[ivar.length - 1 - n]));
				else
					v.andWith(factory.nithVar(ivar[ivar.length - 1 - n]));
				value = value.shiftRight(1);
			}

			return v;
		}
	}

}
