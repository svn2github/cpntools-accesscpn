package org.cpntools.accesscpn.model;



/**
 * @model
 * @author michael
 */
public interface ToolInfo {
	/**
	 * @model required="true"
	 * @return the tool adding this toolspecific information
	 */
	public String getTool();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.ToolInfo#getTool <em>Tool</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Tool</em>' attribute.
	 * @see #getTool()
	 * @generated
	 */
	void setTool(String value);

	/**
	 * @model required="true"
	 * @return the version of the tool adding this toolspecific information
	 */
	public String getVersion();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.ToolInfo#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * @model required="true"
	 * @return the node this toolspecific information is associated with
	 */
	public HasToolInfo getParent();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.ToolInfo#getParent <em>Parent</em>}' container reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(HasToolInfo value);
}
