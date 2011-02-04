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


import org.cpntools.accesscpn.engine.highlevel.HighLevelSimulator;
import org.cpntools.accesscpn.engine.highlevel.InstancePrinter;
import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelData;
import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelDataAdapterFactory;
import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelInstance;
import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelInstanceAdapterFactory;
import org.cpntools.accesscpn.engine.proxy.ProxyDaemon;
import org.cpntools.accesscpn.engine.proxy.ProxySimulator;
import org.cpntools.accesscpn.model.ModelPrinter;
import org.cpntools.accesscpn.model.PetriNet;


public class AdaptTest {
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
		final ModelInstance modelInstance = (ModelInstance) ModelInstanceAdapterFactory
				.getInstance().adapt(petriNet, ModelInstance.class);
		System.out.println("InstanceNumbers: " + modelInstance.getInstanceNumbers());
		System.out.println("Instances: " + modelInstance.getInstances());
		final ModelData modelData = (ModelData) ModelDataAdapterFactory.getInstance().adapt(
				petriNet, ModelData.class);
		System.out.println("allPlaceNodes: " + modelData.getAllPlaceNodes());
		System.out.println("allTransitions: " + modelData.getAllTransitions());
		System.out.println("allPlaceNodeInstances: " + modelData.getAllPlaceNodeInstances());
		System.out.println("allTransitionInstances: " + modelData.getAllTransitionInstances());
		System.out.println("===================================");
		simulator.destroy();
	}
}
