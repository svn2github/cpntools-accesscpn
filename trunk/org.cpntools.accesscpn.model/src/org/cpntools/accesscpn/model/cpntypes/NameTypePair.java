package org.cpntools.accesscpn.model.cpntypes;


/**
 * @model
 * @author keblov
 */
public interface NameTypePair {

	/**
	 * @return type name
	 * @model
	 */
	public String getName();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.NameTypePair#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * @return id of type
	 * @model
	 */
	public String getSort();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.NameTypePair#getSort <em>Sort</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sort</em>' attribute.
	 * @see #getSort()
	 * @generated
	 */
	void setSort(String value);

}
