package org.cpntools.accesscpn.engine.highlevel.instance;

import java.util.List;

import org.cpntools.accesscpn.model.Transition;
import org.eclipse.emf.ecore.EObject;


/**
 * @model
 * @author michael
 */
public interface Binding {
	/**
	 * @model containment="false"
	 * @return
	 */
	Instance<Transition> getTransitionInstance();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.engine.highlevel.instance.Binding#getTransitionInstance <em>Transition Instance</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transition Instance</em>' reference.
	 * @see #getTransitionInstance()
	 * @generated
	 */
	void setTransitionInstance(Instance<Transition> value);

	/**
	 * @param name
	 * @return
	 */
	ValueAssignment getValueAssignment(String name);

	/**
	 * @model containment="true"
	 * @return
	 */
	List<ValueAssignment> getAllAssignments();
}
