package org.cpntools.accesscpn.model;

import org.cpntools.accesscpn.model.graphics.HasGraphics;

/**
 * @model abstract="true"
 * @author michael
 */
public interface Object extends HasId, HasToolInfo, HasGraphics, HasLabel, HasName {
	/**
	 * @model required="true" opposite="object"
	 * @return page this object resides on
	 */
	public Page getPage();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.Object#getPage <em>Page</em>}' container reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Page</em>' container reference.
	 * @see #getPage()
	 * @generated
	 */
	void setPage(Page value);
}
