package org.cpntools.accesscpn.model.graphics;

import java.net.URL;

/**
 * @model
 * @author michael
 */
public interface Fill {
	/**
	 * @model dataType="CSS2Color"
	 * @return the color
	 */
	public String getColor();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.Fill#getColor <em>Color</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Color</em>' attribute.
	 * @see #getColor()
	 * @generated
	 */
	void setColor(String value);

	/**
	 * @model
	 * @return the image
	 */
	public URL getImage();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.Fill#getImage <em>Image</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Image</em>' attribute.
	 * @see #getImage()
	 * @generated
	 */
	void setImage(URL value);

	/**
	 * @model dataType="CSS2Color"
	 * @return the gradient color
	 */
	public String getGradientColor();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.Fill#getGradientColor <em>Gradient Color</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gradient Color</em>' attribute.
	 * @see #getGradientColor()
	 * @generated
	 */
	void setGradientColor(String value);

	/**
	 * @model
	 * @return the gradient rotation
	 */
	public Rotation getGradientRotation();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.graphics.Fill#getGradientRotation <em>Gradient Rotation</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gradient Rotation</em>' attribute.
	 * @see #getGradientRotation()
	 * @generated
	 */
	void setGradientRotation(Rotation value);
}
