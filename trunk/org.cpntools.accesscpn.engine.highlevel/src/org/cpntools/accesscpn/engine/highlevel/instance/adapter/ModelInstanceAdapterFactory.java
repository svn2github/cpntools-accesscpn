package org.cpntools.accesscpn.engine.highlevel.instance.adapter;

import org.cpntools.accesscpn.model.util.ModelAdapterFactory;
import org.eclipse.emf.common.notify.Adapter;


/**
 * @author mwesterg
 * 
 */
public class ModelInstanceAdapterFactory extends ModelAdapterFactory {
	protected static final ModelInstanceAdapterFactory instance = new ModelInstanceAdapterFactory();

	/**
	 * @return
	 */
	public static ModelInstanceAdapterFactory getInstance() {
		return instance;
	}

	protected ModelInstanceAdapterFactory() {
		// Hide constructor
	}

	/**
	 * @see org.cpntools.accesscpn.model.util.ModelAdapterFactory#createPetriNetAdapter()
	 */
	@Override
	public Adapter createPetriNetAdapter() {
		return new ModelInstance();
	}

	/**
	 * @see org.cpntools.accesscpn.model.util.ModelAdapterFactory#isFactoryForType(java.lang.Object)
	 */
	@Override
	public boolean isFactoryForType(final Object type) {
		return ModelInstance.class.equals(type);
	}
}
