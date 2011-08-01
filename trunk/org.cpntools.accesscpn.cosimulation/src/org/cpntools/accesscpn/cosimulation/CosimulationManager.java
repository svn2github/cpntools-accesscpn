package org.cpntools.accesscpn.cosimulation;

import java.util.Map;

import org.cpntools.accesscpn.cosimulation.impl.CPNToolsCosimulation;
import org.cpntools.accesscpn.cosimulation.impl.CPNToolsCosimulationManager;
import org.cpntools.accesscpn.engine.highlevel.HighLevelSimulator;
import org.cpntools.accesscpn.engine.highlevel.instance.Instance;
import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.Transition;

/**
 * Manage cosimulations.
 * 
 * @author mwesterg
 */
public interface CosimulationManager<C extends Cosimulation> {
	public static final CosimulationManager<CPNToolsCosimulation> INSTANCE = new CPNToolsCosimulationManager();

	/**
	 * @param cosimulation
	 * @return
	 * @throws Exception
	 */
	CPNSimulation launch(C cosimulation) throws Exception;

	/**
	 * @param cosimulation
	 * @throws Exception
	 */
	void launchInCPNTools(C cosimulation) throws Exception;

	/**
	 * @param model
	 * @param simulator
	 * @param subpagePlugins
	 * @param context
	 * @param placePlugins
	 * @param transitionPlugins
	 * @return
	 */
	C setup(PetriNet model, HighLevelSimulator simulator, Map<Instance<Page>, SubpagePlugin> subpagePlugins,
	        Map<Instance<PlaceNode>, PlacePlugin> placePlugins,
	        Map<Instance<Transition>, TransitionPlugin> transitionPlugins);

	/**
	 * @param model
	 * @param context
	 * @param subpagePlugins
	 * @param placePlugins
	 * @param transitionPlugins
	 * @return
	 */
	C setup(PetriNet model, Map<Instance<Page>, SubpagePlugin> subpagePlugins,
	        Map<Instance<PlaceNode>, PlacePlugin> placePlugins,
	        Map<Instance<Transition>, TransitionPlugin> transitionPlugins);
}
