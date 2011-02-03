package org.cpntools.accesscpn.model.cpntypes;

/**
 * @model
 * @author michael
 */
public interface CPNInt extends CPNType {
	/**
	 * @return lower value
	 * @model required="false"
	 */
	public String getLow();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.CPNInt#getLow <em>Low</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Low</em>' attribute.
	 * @see #getLow()
	 * @generated
	 */
	void setLow(String value);

	/**
	 * @return higher value
	 * @model required="false"
	 */
	public String getHigh();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.CPNInt#getHigh <em>High</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>High</em>' attribute.
	 * @see #getHigh()
	 * @generated
	 */
	void setHigh(String value);
}
