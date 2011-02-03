package org.cpntools.accesscpn.engine.highlevel.instance;

import java.util.List;

import org.cpntools.accesscpn.model.PlaceNode;
import org.eclipse.emf.ecore.EObject;


/**
 * @model
 * @author michael
 */
public interface State {
	/**
	 * @param instance
	 * @return
	 */
	Marking getMarking(Instance<? extends PlaceNode> instance);

	/**
	 * @model containment="true"
	 * @return
	 */
	List<Marking> getAllMarkings();
}
