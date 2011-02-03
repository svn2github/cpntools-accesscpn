package org.cpntools.accesscpn.model.declaration;

/**
 * @model
 * @author michael
 */
public interface GlobalReferenceDeclaration extends DeclarationStructure {
	/**
	 * @model required="true"
	 * @return name of the refernce
	 */
	public String getName();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.declaration.GlobalReferenceDeclaration#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * @model required="true"
	 * @return initial value of reference
	 */
	public String getValue();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.declaration.GlobalReferenceDeclaration#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);
}
