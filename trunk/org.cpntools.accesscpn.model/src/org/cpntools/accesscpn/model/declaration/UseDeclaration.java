package org.cpntools.accesscpn.model.declaration;

/**
 * @model
 * @author michael
 */
public interface UseDeclaration extends DeclarationStructure {
	/**
	 * @model required="true"
	 * @return name of file to load
	 */
	public String getFileName();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.declaration.UseDeclaration#getFileName <em>File Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Name</em>' attribute.
	 * @see #getFileName()
	 * @generated
	 */
	void setFileName(String value);
}
