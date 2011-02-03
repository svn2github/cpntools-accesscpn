package org.cpntools.accesscpn.model.graphics;

/**
 * @model
 * @author michael
 */
public interface AnnotationGraphics extends Graphics {
	/**
	 * @model containment="true"
	 * @return the fill
	 */
	public Fill getFill();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.AnnotationGraphics#getFill <em>Fill</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fill</em>' containment reference.
	 * @see #getFill()
	 * @generated
	 */
	void setFill(Fill value);

	/**
	 * @model containment="true"
	 * @return the offset
	 */
	public Coordinate getOffset();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.AnnotationGraphics#getOffset <em>Offset</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Offset</em>' containment reference.
	 * @see #getOffset()
	 * @generated
	 */
	void setOffset(Coordinate value);

	/**
	 * @model containment="true"
	 * @return the line
	 */
	public Line getLine();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.AnnotationGraphics#getLine <em>Line</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Line</em>' containment reference.
	 * @see #getLine()
	 * @generated
	 */
	void setLine(Line value);

	/**
	 * @model containment="true"
	 * @return the font
	 */
	public Font getFont();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.AnnotationGraphics#getFont <em>Font</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Font</em>' containment reference.
	 * @see #getFont()
	 * @generated
	 */
	void setFont(Font value);
}
