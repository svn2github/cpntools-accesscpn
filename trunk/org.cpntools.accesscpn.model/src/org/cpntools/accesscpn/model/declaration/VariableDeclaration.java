package org.cpntools.accesscpn.model.declaration;

import java.util.List;

/**
 * @model
 * @author michael
 */
public interface VariableDeclaration extends DeclarationStructure {
	/**
	 * @model required="true"
	 * @return type of variables
	 */
	public String getTypeName();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.declaration.VariableDeclaration#getTypeName <em>Type Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Name</em>' attribute.
	 * @see #getTypeName()
	 * @generated
	 */
	void setTypeName(String value);

	/**
	 * @model required="true" type="String"
	 * @return names of variables
	 */
	public List<String> getVariables();

	/**
	 * @param var new variable to add to decl
	 */
	public void addVariable(String var);
}
