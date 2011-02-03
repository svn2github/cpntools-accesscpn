package org.cpntools.accesscpn.model.cpntypes;

/**
 * @model
 * @author michael
 */
public interface CPNList extends CPNType {

	/**
	 * @return type we are list over
	 * @model required="true"
	 */
	public String getSort();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.CPNList#getSort <em>Sort</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sort</em>' attribute.
	 * @see #getSort()
	 * @generated
	 */
	void setSort(String value);

	/**
	 * @return lower value
	 * @model required="false"
	 */
	public String getLow();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.CPNList#getLow <em>Low</em>}' attribute.
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
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.CPNList#getHigh <em>High</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>High</em>' attribute.
	 * @see #getHigh()
	 * @generated
	 */
	void setHigh(String value);

}
