package org.cpntools.accesscpn.model;


/**
 * @model abstract="true" interface="true"
 * @author michael
 */
public interface CPNToolsTransitionAddin {
	/**
	 * @model required="false"
	 * @return the code region
	 */
	public Code getCode();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.CPNToolsTransitionAddin#getCode <em>Code</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Code</em>' reference.
	 * @see #getCode()
	 * @generated
	 */
	void setCode(Code value);

	/**
	 * @model required="false"
	 * @return the time region
	 */
	public Time getTime();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.CPNToolsTransitionAddin#getTime <em>Time</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time</em>' reference.
	 * @see #getTime()
	 * @generated
	 */
	void setTime(Time value);
	
	/**
	 * @model required="false"
	 * @return
	 */
	public Priority getPriority();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.CPNToolsTransitionAddin#getPriority <em>Priority</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Priority</em>' reference.
	 * @see #getPriority()
	 * @generated
	 */
	void setPriority(Priority value);
}
