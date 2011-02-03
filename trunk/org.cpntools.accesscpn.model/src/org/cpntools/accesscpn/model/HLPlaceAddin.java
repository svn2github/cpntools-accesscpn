package org.cpntools.accesscpn.model;



/**
 * @model abstract="true" interface="true"
 * @author michael
 */
public interface HLPlaceAddin {

	/**
	 * @model required="false"
	 * @return the type region of the place
	 */
	public Sort getSort();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.HLPlaceAddin#getSort <em>Type</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getSort()
	 * @generated
	 */
	void setSort(Sort value);

	/**
	 * @model required="false"
	 * @return the initial marking of the place
	 */
	public HLMarking getInitialMarking();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.HLPlaceAddin#getInitialMarking <em>Initial Marking</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial Marking</em>' reference.
	 * @see #getInitialMarking()
	 * @generated
	 */
	void setInitialMarking(HLMarking value);
}
