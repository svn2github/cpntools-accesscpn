package org.cpntools.accesscpn.model.declaration;

/**
 * @model
 * @author michael
 */
public interface MLDeclaration extends DeclarationStructure {
	/**
	 * @model required="true"
	 * @return code
	 */
	public String getCode();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.declaration.MLDeclaration#getCode <em>Code</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Code</em>' attribute.
	 * @see #getCode()
	 * @generated
	 */
	void setCode(String value);
}
