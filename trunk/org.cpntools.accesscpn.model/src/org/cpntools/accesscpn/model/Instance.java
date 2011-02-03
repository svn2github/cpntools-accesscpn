package org.cpntools.accesscpn.model;

import java.util.List;


/**
 * @model
 * @author michael
 */
public interface Instance extends Node {
	/**
	 * @model type="ParameterAssignment" opposite="instance" containment="true"
	 * @return the parameter assignement of the instance/subst. transition
	 */
	List<ParameterAssignment> getParameterAssignment();

	/**
	 * @model
	 * @return the id of the sub-page
	 */
	String getSubPageID();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.Instance#getSubPageID <em>Sub Page ID</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sub Page ID</em>' attribute.
	 * @see #getSubPageID()
	 * @generated
	 */
	void setSubPageID(String value);
}
