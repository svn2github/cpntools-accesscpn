package org.cpntools.accesscpn.model.cpntypes;

/**
 * @model
 * @author keblov
 */
public interface CPNSubset extends CPNType {


	/**
	 * @return type we subset
	 * @model required="false"
	 */
	public String getSort();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.CPNSubset#getSort <em>Sort</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sort</em>' attribute.
	 * @see #getSort()
	 * @generated
	 */
	void setSort(String value);

	/**
	 * @return function to test values
	 * @model required="false"
	 */
	public String getBy();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.CPNSubset#getBy <em>By</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>By</em>' attribute.
	 * @see #getBy()
	 * @generated
	 */
	void setBy(String value);

	/**
	 * @return list of allowed values
	 * @model required="false"
	 */
	public String getWith();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.CPNSubset#getWith <em>With</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>With</em>' attribute.
	 * @see #getWith()
	 * @generated
	 */
	void setWith(String value);
}
