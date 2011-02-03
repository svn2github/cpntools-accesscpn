package org.cpntools.accesscpn.engine.highlevel.instance;

import org.eclipse.emf.ecore.EObject;

/**
 * @model
 * @author mw
 * @param <T>
 *            (Node) type this is an instance of
 */
public interface Instance<T> {
	/**
	 * @model containment="false"
	 * @return the element
	 */
	public T getNode();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.engine.highlevel.instance.Instance#getNode <em>Node</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node</em>' reference.
	 * @see #getNode()
	 * @generated
	 */
	void setNode(T value);

	/**
	 * @return
	 */
	public int getInstanceNumber();

	/**
	 * @model containment="false"
	 * @return
	 */
	public Instance<org.cpntools.accesscpn.model.Instance> getTransitionPath();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.engine.highlevel.instance.Instance#getTransitionPath <em>Transition Path</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transition Path</em>' reference.
	 * @see #getTransitionPath()
	 * @generated
	 */
	void setTransitionPath(Instance<org.cpntools.accesscpn.model.Instance> value);
}
