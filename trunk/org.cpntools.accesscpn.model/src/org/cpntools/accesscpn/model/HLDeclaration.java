package org.cpntools.accesscpn.model;

import org.cpntools.accesscpn.model.declaration.DeclarationStructure;

/**
 * @model
 * @author michael Renamed from the standard to not collide
 */
public interface HLDeclaration extends Annotation, HasId {
	/**
	 * @model containment="true" required="false"
	 * @return a structured representation of the decl
	 */
	public DeclarationStructure getStructure();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.model.HLDeclaration#getStructure <em>Structure</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Structure</em>' containment reference.
	 * @see #getStructure()
	 * @generated
	 */
	void setStructure(DeclarationStructure value);
}
