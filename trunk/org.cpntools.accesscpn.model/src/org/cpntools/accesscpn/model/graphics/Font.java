package org.cpntools.accesscpn.model.graphics;


/**
 * @model
 * @author michael
 */
public interface Font {
	/**
	 * @model required="true" dataType="CSS2FontFamily"
	 * @return the font family
	 */
	public String getFamily();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.Font#getFamily <em>Family</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Family</em>' attribute.
	 * @see #getFamily()
	 * @generated
	 */
	void setFamily(String value);

	/**
	 * @model required="true" dataType="CSS2FontStyle"
	 * @return the style
	 */
	public String getStyle();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.Font#getStyle <em>Style</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Style</em>' attribute.
	 * @see #getStyle()
	 * @generated
	 */
	void setStyle(String value);

	/**
	 * @model required="true" dataType="CSS2FontWeight"
	 * @return the weight
	 */
	public String getWeight();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.Font#getWeight <em>Weight</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Weight</em>' attribute.
	 * @see #getWeight()
	 * @generated
	 */
	void setWeight(String value);

	/**
	 * @model required="true" dataType="CSS2FontSize"
	 * @return the size
	 */
	public String getSize();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.Font#getSize <em>Size</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size</em>' attribute.
	 * @see #getSize()
	 * @generated
	 */
	void setSize(String value);

	/**
	 * @model
	 * @return the decoration
	 */
	public Decoration getDecoration();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.Font#getDecoration <em>Decoration</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Decoration</em>' attribute.
	 * @see #getDecoration()
	 * @generated
	 */
	void setDecoration(Decoration value);

	/**
	 * @model required="true"
	 * @return the alignment
	 */
	public Align getAlign();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.Font#getAlign <em>Align</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Align</em>' attribute.
	 * @see org.cpntools.accesscpn.model.graphics.Align
	 * @see #getAlign()
	 * @generated
	 */
	void setAlign(Align value);

	/**
	 * @model required="true"
	 * @return the roration
	 */
	public double getRotation();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.Font#getRotation <em>Rotation</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rotation</em>' attribute.
	 * @see #getRotation()
	 * @generated
	 */
	void setRotation(double value);
}
