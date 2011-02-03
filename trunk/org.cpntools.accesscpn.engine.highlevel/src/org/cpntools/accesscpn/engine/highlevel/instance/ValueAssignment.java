package org.cpntools.accesscpn.engine.highlevel.instance;

import org.eclipse.emf.ecore.EObject;

/**
 * @model
 * @author michael
 */
public interface ValueAssignment {
	/**
	 * @model
	 * @return
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.engine.highlevel.instance.ValueAssignment#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * @model
	 * @return
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.engine.highlevel.instance.ValueAssignment#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);
}
