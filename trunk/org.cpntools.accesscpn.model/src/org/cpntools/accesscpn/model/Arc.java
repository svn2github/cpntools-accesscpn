package org.cpntools.accesscpn.model;

/**
 * @model
 * @author michael
 */
public interface Arc extends HasId, HLArcAddin {
	/**
	 * @model containment="false" opposite="sourceArc" required="true"
	 * @return source of arc
	 */
	public Node getSource();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.Arc#getSource <em>Source</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(Node value);

	/**
	 * @model containment="false" opposite="targetArc" required="true"
	 * @return target of arc
	 */
	public Node getTarget();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.Arc#getTarget <em>Target</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Node value);

	/**
	 * @model containment="false" opposite="arc" required="true"
	 * @return page containing the arc
	 */
	public Page getPage();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.Arc#getPage <em>Page</em>}' container reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Page</em>' container reference.
	 * @see #getPage()
	 * @generated
	 */
	void setPage(Page value);

	// Utility methods not part of the model

	/**
	 * @param n node
	 * @return the other end of the arc
	 */
	public Node getOtherEnd(Node n);

	/**
	 * @return the end of the arc that is a place
	 */
	public PlaceNode getPlaceNode();

	/**
	 * @return the end of the arc that is a transition
	 */
	public Transition getTransition();

	/**
	 * @return whether this arc is ready to be sent to the simulator
	 */
	boolean isReady();

}
