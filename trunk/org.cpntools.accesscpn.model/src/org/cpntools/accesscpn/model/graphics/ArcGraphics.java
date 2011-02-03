package org.cpntools.accesscpn.model.graphics;

import java.util.List;

/**
 * @model
 * @author michael
 */
public interface ArcGraphics extends Graphics {
	/**
	 * @model containment="true"
	 * @return the line
	 */
	public Line getLine();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.ArcGraphics#getLine <em>Line</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Line</em>' containment reference.
	 * @see #getLine()
	 * @generated
	 */
	void setLine(Line value);

	/**
	 * @model type="Coordinate" containment="true"
	 * @return the bendpoints
	 */
	public List<Coordinate> getPosition();
}
