package org.cpntools.accesscpn.engine.highlevel.instance.adapter;

import org.cpntools.accesscpn.model.PetriNet;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.util.EContentAdapter;


/**
 * @author mwesterg
 * 
 */
public class PetriNetDataAdapter extends EContentAdapter {
	private PetriNet petriNet;

	/**
	 * @param target
	 */
	@Override
	public void setTarget(final Notifier target) {
		super.setTarget(target);
		if (target != null && target instanceof PetriNet) {
			petriNet = (PetriNet) target;
		}
	}

	/**
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#isAdapterForType(java.lang.Object)
	 */
	@Override
	public boolean isAdapterForType(final Object type) {
		return getClass().equals(type);
	}

	/**
	 * @return
	 */
	public PetriNet getPetriNet() {
		return petriNet;
	}
}
