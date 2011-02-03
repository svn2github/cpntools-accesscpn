package org.cpntools.accesscpn.model.cpntypes;

/**
 * @model
 * @author michael
 */
public interface CPNAlias extends CPNType {
	/**
	 * @return type type we are alias for
	 * @model required="true"
	 */
	public String getSort();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.CPNAlias#getSort <em>Sort</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sort</em>' attribute.
	 * @see #getSort()
	 * @generated
	 */
	void setSort(String value);
}
