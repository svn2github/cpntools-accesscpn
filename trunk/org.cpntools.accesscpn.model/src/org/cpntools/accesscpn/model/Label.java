package org.cpntools.accesscpn.model;

/**
 * @model abstract="true"
 * @author michael
 */
public interface Label extends HasToolInfo {
	/**
	 * @model required="true"
	 * @return the parent of this label
	 */
	public HasLabel getParent();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.Label#getParent <em>Parent</em>}' container reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(HasLabel value);

	// Additional utility methods

	/**
	 * @model transient="true"
	 * @return a string representation of this label
	 */
	public String asString();
}
