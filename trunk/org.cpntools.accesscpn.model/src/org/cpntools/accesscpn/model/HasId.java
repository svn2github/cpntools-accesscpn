package org.cpntools.accesscpn.model;



/**
 * @model abstract="true"
 * @author michael Addition compared to HLPN standard. Makes all id'able classes have a common super-class.
 */
public interface HasId {
	/**
	 * @model id="true" required="true"
	 * @return the id of the object
	 */
	public String getId();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.HasId#getId <em>Id</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);
}
