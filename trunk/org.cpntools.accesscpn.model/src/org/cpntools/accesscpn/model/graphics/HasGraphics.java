package org.cpntools.accesscpn.model.graphics;


/**
 * @model interface="true" abstract="true"
 * @author michael
 */
public interface HasGraphics {
	/**
	 * @model containment="true" required="false" opposite="parent"
	 * @return the graphics of this objectr
	 */
	public Graphics getGraphics();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.HasGraphics#getGraphics <em>Graphics</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Graphics</em>' containment reference.
	 * @see #getGraphics()
	 * @generated
	 */
	void setGraphics(Graphics value);
}
