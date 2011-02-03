package org.cpntools.accesscpn.engine.highlevel.instance.adapter;

import org.cpntools.accesscpn.model.util.ModelAdapterFactory;
import org.eclipse.emf.common.notify.Adapter;


/**
 * @author mwesterg
 * 
 */
public class ModelDataAdapterFactory extends ModelAdapterFactory {
	protected static final ModelDataAdapterFactory instance = new ModelDataAdapterFactory();

	/**
	 * @return
	 */
	public static ModelDataAdapterFactory getInstance() {
		return instance;
	}

	protected ModelDataAdapterFactory() {
		// Hide constructor
	}

	/**
	 * @see org.cpntools.accesscpn.model.util.ModelAdapterFactory#createPetriNetAdapter()
	 */
	@Override
	public Adapter createPetriNetAdapter() {
		return new ModelData();
	}

	/**
	 * @see org.cpntools.accesscpn.model.util.ModelAdapterFactory#isFactoryForType(java.lang.Object)
	 */
	@Override
	public boolean isFactoryForType(final Object type) {
		return ModelInstance.class.equals(type);
	}
}
