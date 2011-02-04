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


import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.cpntools.accesscpn.engine.highlevel.HighLevelSimulator;
import org.cpntools.accesscpn.engine.highlevel.InstancePrinter;
import org.cpntools.accesscpn.engine.highlevel.instance.Instance;
import org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNValue;
import org.cpntools.accesscpn.engine.proxy.ProxyDaemon;
import org.cpntools.accesscpn.engine.proxy.ProxySimulator;
import org.cpntools.accesscpn.model.ModelPrinter;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;


public class ScrapeTest {
	public static void main(final String[] args) throws Exception {
		System.out.println("Starting proxy");
		final ProxyDaemon pd = ProxyDaemon.getDefaultInstance();
		System.out.println("Waiting for CPN Tools");
		final ProxySimulator ps = pd.getNext();

		System.out.println("Waiting for syntax check");
		while (ps.getPetriNet() == null)
			Thread.sleep(500);
		final PetriNet petriNet = ps.getPetriNet();

		System.out.println(ModelPrinter.printModel(petriNet));
		System.out.println(InstancePrinter.printModel(petriNet));

		final HighLevelSimulator simulator = ps.getSimulator();

		System.out.println("===================================");
		Map<Instance<? extends PlaceNode>, List<CPNValue>> marking = simulator
				.getStructuredMarking(simulator.getMarking());
		for (final Entry<Instance<? extends PlaceNode>, List<CPNValue>> entry : marking.entrySet())
			System.out.println(entry.getKey() + ": " + entry.getValue());
		System.out.println("===================================");
		simulator.execute(1);
		marking = simulator.getStructuredMarking(simulator.getMarking());
		for (final Entry<Instance<? extends PlaceNode>, List<CPNValue>> entry : marking.entrySet())
			System.out.println(entry.getKey() + ": " + entry.getValue());
		System.out.println("===================================");
		simulator.refreshViews("Hello World", BigInteger.ZERO);
		simulator.destroy();
	}
}
