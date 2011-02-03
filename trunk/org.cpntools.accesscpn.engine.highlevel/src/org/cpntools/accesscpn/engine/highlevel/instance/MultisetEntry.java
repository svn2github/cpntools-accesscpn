package org.cpntools.accesscpn.engine.highlevel.instance;

import org.eclipse.emf.ecore.EObject;

/**
 * @model
 * @author michael
 */
public interface MultisetEntry {
	/**
	 * @model
	 * @return
	 */
	int getCoefficient();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.engine.highlevel.instance.MultisetEntry#getCoefficient <em>Coefficient</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Coefficient</em>' attribute.
	 * @see #getCoefficient()
	 * @generated
	 */
	void setCoefficient(int value);

	/**
	 * @model
	 * @return
	 */
	String getValueAsString();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.engine.highlevel.instance.MultisetEntry#getValueAsString <em>Value As String</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value As String</em>' attribute.
	 * @see #getValueAsString()
	 * @generated
	 */
	void setValueAsString(String value);
}
