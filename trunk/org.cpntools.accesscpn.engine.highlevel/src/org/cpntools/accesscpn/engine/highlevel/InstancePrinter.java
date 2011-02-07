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
package org.cpntools.accesscpn.engine.highlevel;

import org.cpntools.accesscpn.engine.highlevel.instance.Instance;
import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelInstance;
import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelInstanceAdapterFactory;
import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;


/**
 * @author mwesterg
 * 
 */
public class InstancePrinter {
	private final StringBuilder sb;

	/**
	 * 
	 */
	public InstancePrinter() {
		sb = new StringBuilder();
	}

	/**
	 * @param petriNet
	 * @return
	 */
	public static String printModel(final PetriNet petriNet) {
		return new InstancePrinter().print(petriNet);
	}

	private String print(final PetriNet petriNet) {
		for (final Page p : petriNet.getPage()) {
			print(p);
		}
		return sb.toString();
	}

	private void print(final Page p) {
		sb.append("Page ");
		sb.append(p.getName().getText());
		sb.append("\n");

		final ModelInstance modelInstance = (ModelInstance) ModelInstanceAdapterFactory
				.getInstance().adapt(p.getPetriNet(), ModelInstance.class);
		for (final Instance<Page> instance : modelInstance.getAllInstances(p)) {
			sb.append("  - ");
			if (instance != null) {
				sb.append(instance);
				sb.append(" - ");
				sb.append(instance.getInstanceNumber());
			} else {
				sb.append("Top - 1");
			}
			sb.append("\n");
		}
	}

}
