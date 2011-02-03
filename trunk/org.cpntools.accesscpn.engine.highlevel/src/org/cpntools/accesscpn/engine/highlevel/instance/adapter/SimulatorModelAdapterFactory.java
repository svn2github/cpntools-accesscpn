package org.cpntools.accesscpn.engine.highlevel.instance.adapter;

import org.cpntools.accesscpn.engine.highlevel.HighLevelSimulator;
import org.cpntools.accesscpn.model.util.ModelAdapterFactory;
import org.eclipse.emf.common.notify.Adapter;


/**
 * @author mwesterg
 * 
 */
public class SimulatorModelAdapterFactory extends ModelAdapterFactory {
	protected static final SimulatorModelAdapterFactory instance = new SimulatorModelAdapterFactory();

	/**
	 * @return
	 */
	public static SimulatorModelAdapterFactory getInstance() {
		return instance;
	}

	protected SimulatorModelAdapterFactory() {
		// Hide constructor
	}

	/**
	 * @see org.cpntools.accesscpn.model.util.ModelAdapterFactory#createPetriNetAdapter()
	 */
	@Override
	public Adapter createPetriNetAdapter() {
		try {
			return HighLevelSimulator.getHighLevelSimulator();
		} catch (final Exception e) {
			return null;
		}
	}

	/**
	 * @see org.cpntools.accesscpn.model.util.ModelAdapterFactory#isFactoryForType(java.lang.Object)
	 */
	@Override
	public boolean isFactoryForType(final Object type) {
		return HighLevelSimulator.class.equals(type);
	}
}
