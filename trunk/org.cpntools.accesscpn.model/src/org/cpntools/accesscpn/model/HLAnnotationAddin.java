package org.cpntools.accesscpn.model;



/**
 * @model abstract="true" interface="true"
 * @author michael
 */
public interface HLAnnotationAddin {
	/**
	 * @model required="true"
	 * @return the text of the annotation
	 */
	public String getText();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.HLAnnotationAddin#getText <em>Text</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
	void setText(String value);

	/**
	 * @return whether this label is ready to be transmitted to the simulator
	 */
	public boolean isReady(boolean mayBeEmpty);
}
