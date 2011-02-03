package org.cpntools.accesscpn.model;



/**
 * @model abstract="true" interface="true"
 * @author michael
 */
public interface HLArcAddin {

	/**
	 * @return the inscription of this arc
	 * @model required="false" containment="true"
	 */
	public HLAnnotation getHlinscription();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.HLArcAddin#getHlinscription <em>Hlinscription</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hlinscription</em>' containment reference.
	 * @see #getHlinscription()
	 * @generated
	 */
	void setHlinscription(HLAnnotation value);

	// This has been added -- seems like the standard has forgotten ablut this?
	/**
	 * @model required="true"
	 * @return the type of the arc
	 */
	public HLArcType getKind();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.HLArcAddin#getKind <em>Type</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.cpntools.accesscpn.model.HLArcType
	 * @see #getKind()
	 * @generated
	 */
	void setKind(HLArcType value);
}
