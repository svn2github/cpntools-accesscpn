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
