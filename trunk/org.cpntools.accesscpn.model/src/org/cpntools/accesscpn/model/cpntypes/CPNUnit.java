package org.cpntools.accesscpn.model.cpntypes;

/**
 * @model
 * @author michael
 */
public interface CPNUnit extends CPNType {
	/**
	 * @model required="false"
	 * @return value of unit
	 */
	public String getId();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.CPNUnit#getId <em>Id</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);
}
