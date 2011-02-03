package org.cpntools.accesscpn.model;

/**
 * @model
 * @author michael
 */
public interface RefPlace extends PlaceNode {
	/**
	 * @model required="true" containment="false" opposite="references"
	 * @return the place this reference points to
	 */
	public Place getRef();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.RefPlace#getRef <em>Ref</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Ref</em>' reference.
	 * @see #getRef()
	 * @generated
	 */
	void setRef(Place value);

	/**
	 * @return whether this is a CPN Tools fusion place
	 */
	public boolean isFusionGroup();

	/**
	 * @return whether this is a CPN Tools port place
	 */
	public boolean isPort();
}
