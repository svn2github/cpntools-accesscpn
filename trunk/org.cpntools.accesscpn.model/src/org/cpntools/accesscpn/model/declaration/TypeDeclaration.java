package org.cpntools.accesscpn.model.declaration;

import org.cpntools.accesscpn.model.cpntypes.CPNType;

/**
 * @model
 * @author michael
 */
public interface TypeDeclaration extends DeclarationStructure {
	/**
	 * @model required="true"
	 * @return name of declared type
	 */
	public String getTypeName();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.declaration.TypeDeclaration#getTypeName <em>Type Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Name</em>' attribute.
	 * @see #getTypeName()
	 * @generated
	 */
	void setTypeName(String value);


	/**
	 * @model containment="true"
	 * @return type of declared type
	 */
	public CPNType getSort();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.declaration.TypeDeclaration#getSort <em>Sort</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sort</em>' containment reference.
	 * @see #getSort()
	 * @generated
	 */
	void setSort(CPNType value);
}
