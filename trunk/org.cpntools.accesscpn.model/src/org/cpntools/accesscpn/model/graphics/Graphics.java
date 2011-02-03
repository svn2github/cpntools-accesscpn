package org.cpntools.accesscpn.model.graphics;


/**
 * @model abstract="true"
 * @author michael
 */
public interface Graphics {
	/**
	 * @model required="true"
	 * @return the parent having this graphics
	 */
	public HasGraphics getParent();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.Graphics#getParent <em>Parent</em>}' container reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(HasGraphics value);
}
