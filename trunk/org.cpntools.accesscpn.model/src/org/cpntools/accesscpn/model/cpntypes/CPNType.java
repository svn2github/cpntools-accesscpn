package org.cpntools.accesscpn.model.cpntypes;

import java.util.List;

/**
 * @model abstract="true"
 * @author michael
 */
public interface CPNType {
	/**
	 * @model is the type timed
	 * @return whether the type id timed
	 */
	public Boolean getTimed();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.cpntypes.CPNType#getTimed <em>Timed</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timed</em>' attribute.
	 * @see #getTimed()
	 * @generated
	 */
	void setTimed(Boolean value);

	/**
	 * @return get all declares
	 * @model type="String"
	 */
	public List<String> getDeclares();

	/**
	 * @param declare declare to add
	 */
	public void addDeclare(String declare);

	/**
	 * @return string rep of type
	 */
	public String asString();
}
