package org.cpntools.accesscpn.model;

import java.util.List;



/**
 * @model
 * @author michael
 */
public interface PetriNet extends HasId, HasToolInfo, HasLabel, HasName {
/**
	 * @model required="true"
	 * @return the type of petri net
	 */
	public String getKind();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.PetriNet#getKind <em>Type</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getKind()
	 * @generated
	 */
	void setKind(String value);

	/**
	 * @model containment="true" lower="1" opposite="petriNet" type="Page"
	 * @return all pages of this petri net
	 */
	public List<Page> getPage();

	// This definition differs from the standard to avoid double representation
	/**
	 * @return all declarations of this petri net
	 */
	public Iterable<HLDeclaration> declaration();

	// This is added to better support fusion groups
	/**
	 * @return all fusion groups in this petri net
	 * @model containment="true" opposite="petriNet" type="FusionGroup"
	 */
	List<FusionGroup> getFusionGroups();
}
