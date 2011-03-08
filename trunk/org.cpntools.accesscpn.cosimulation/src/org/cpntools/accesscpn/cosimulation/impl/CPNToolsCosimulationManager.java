package org.cpntools.accesscpn.cosimulation.impl;

import java.util.Map;

import org.cpntools.accesscpn.cosimulation.CosimulationManager;
import org.cpntools.accesscpn.cosimulation.PlacePlugin;
import org.cpntools.accesscpn.cosimulation.SubpagePlugin;
import org.cpntools.accesscpn.cosimulation.TransitionPlugin;
import org.cpntools.accesscpn.engine.highlevel.HighLevelSimulator;
import org.cpntools.accesscpn.engine.highlevel.checker.Checker;
import org.cpntools.accesscpn.engine.highlevel.instance.Instance;
import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.Transition;

/**
 * 
 * @author mwesterg
 * 
 */
public class CPNToolsCosimulationManager implements CosimulationManager<CPNToolsCosimulation> {

	@Override
	public CPNToolsSimulation launch(final CPNToolsCosimulation cosimulation) {
		final CPNToolsSimulation simulation = new CPNToolsSimulation(cosimulation);
		simulation.start();
		return simulation;
	}

	@Override
	public void launchInCPNTools(final CPNToolsCosimulation cosimulation) {
		final CPNToolsSimulation simulation = launch(cosimulation);
		final HighLevelSimulator simulator = cosimulation.getSimulator();
		while (true) {

		}
	}

	@Override
	public CPNToolsCosimulation setup(final PetriNet model, final HighLevelSimulator simulator,
			final Map<Instance<Page>, SubpagePlugin> subpagePlugins,
			final Map<Instance<PlaceNode>, PlacePlugin> placePlugins,
			final Map<Instance<Transition>, TransitionPlugin> transitionPlugins) {
		return new CPNToolsCosimulation(model, simulator, subpagePlugins, placePlugins,
				transitionPlugins);
	}

	@Override
	public CPNToolsCosimulation setup(final PetriNet model,
			final Map<Instance<Page>, SubpagePlugin> subpagePlugins,
			final Map<Instance<PlaceNode>, PlacePlugin> placePlugins,
			final Map<Instance<Transition>, TransitionPlugin> transitionPlugins) {
		try {
			final HighLevelSimulator highLevelSimulator = HighLevelSimulator
					.getHighLevelSimulator();
			final Checker checker = new Checker(model, null, highLevelSimulator);
			checker.checkEntireModel();
			return setup(model, highLevelSimulator, subpagePlugins, placePlugins, transitionPlugins);
		} catch (final Exception e) {
			return new EmptyCosimulation();
		}
	}

}
