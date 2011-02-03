package org.cpntools.accesscpn.model.graphics;


/**
 * @model
 * @author michael
 */
public interface Line {
	/**
	 * @model required="true"
	 * @return the shape
	 */
	public Shape getShape();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.Line#getShape <em>Shape</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shape</em>' attribute.
	 * @see org.cpntools.accesscpn.model.graphics.Shape
	 * @see #getShape()
	 * @generated
	 */
	void setShape(Shape value);

	/**
	 * @model dataType="CSS2Color" required="true"
	 * @return the color
	 */
	public String getColor();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.Line#getColor <em>Color</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Color</em>' attribute.
	 * @see #getColor()
	 * @generated
	 */
	void setColor(String value);

	/**
	 * @model dataType="NonNegativeDecimal" required="true"
	 * @return the width
	 */
	public double getWidth();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.Line#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(double value);

	/**
	 * @model required="true"
	 * @return the style
	 */
	public Style getStyle();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.Line#getStyle <em>Style</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Style</em>' attribute.
	 * @see org.cpntools.accesscpn.model.graphics.Style
	 * @see #getStyle()
	 * @generated
	 */
	void setStyle(Style value);
}
