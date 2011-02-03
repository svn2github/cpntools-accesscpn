package org.cpntools.accesscpn.model.cpntypes;

/**
 * @model
 * @author michael
 */
public interface CPNBool extends CPNType {
	/**
	 * @return the value of true
	 * @model required="false"
	 */
	public String getTrueValue();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.CPNBool#getTrueValue <em>True Value</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>True Value</em>' attribute.
	 * @see #getTrueValue()
	 * @generated
	 */
	void setTrueValue(String value);

	/**
	 * @return the value of false
	 * @model required="false"
	 */
	public String getFalseValue();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.CPNBool#getFalseValue <em>False Value</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>False Value</em>' attribute.
	 * @see #getFalseValue()
	 * @generated
	 */
	void setFalseValue(String value);
}
