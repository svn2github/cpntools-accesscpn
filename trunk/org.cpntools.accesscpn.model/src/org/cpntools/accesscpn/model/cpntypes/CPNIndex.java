package org.cpntools.accesscpn.model.cpntypes;

/**
 * @model
 * @author michael
 */
public interface CPNIndex extends CPNType {
	/**
	 * @return name to index over
	 * @model required="true"
	 */
	public String getName();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.CPNIndex#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * @return lower value
	 * @model required="true"
	 */
	public String getLow();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.CPNIndex#getLow <em>Low</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Low</em>' attribute.
	 * @see #getLow()
	 * @generated
	 */
	void setLow(String value);

	/**
	 * @return higher value
	 * @model required="true"
	 */
	public String getHigh();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.CPNIndex#getHigh <em>High</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>High</em>' attribute.
	 * @see #getHigh()
	 * @generated
	 */
	void setHigh(String value);
}
