package org.cpntools.accesscpn.model.cpntypes;

/**
 * @model
 * @author michael
 */
public interface CPNReal extends CPNType {
	/**
	 * @return higher value
	 * @model required="false"
	 */
	public double getHigh();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.CPNReal#getHigh <em>High</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>High</em>' attribute.
	 * @see #getHigh()
	 * @generated
	 */
	void setHigh(double value);

	/**
	 * @return lower value
	 * @model required="false"
	 */
	public double getLow();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.CPNReal#getLow <em>Low</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Low</em>' attribute.
	 * @see #getLow()
	 * @generated
	 */
	void setLow(double value);
}
