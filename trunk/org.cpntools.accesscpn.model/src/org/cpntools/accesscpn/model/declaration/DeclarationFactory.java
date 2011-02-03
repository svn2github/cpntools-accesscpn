/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.cpntools.accesscpn.model.declaration;


/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @generated
 */
public interface DeclarationFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DeclarationFactory INSTANCE = org.cpntools.accesscpn.model.declaration.impl.DeclarationFactoryImpl.eINSTANCE;
	/**
	 * Returns a new object of class '<em>Variable Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Variable Declaration</em>'.
	 * @generated
	 */
	VariableDeclaration createVariableDeclaration();

	/**
	 * Returns a new object of class '<em>ML Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ML Declaration</em>'.
	 * @generated
	 */
	MLDeclaration createMLDeclaration();

	/**
	 * Returns a new object of class '<em>Use Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Use Declaration</em>'.
	 * @generated
	 */
	UseDeclaration createUseDeclaration();

	/**
	 * Returns a new object of class '<em>Global Reference Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Global Reference Declaration</em>'.
	 * @generated
	 */
	GlobalReferenceDeclaration createGlobalReferenceDeclaration();

	/**
	 * Returns a new object of class '<em>Type Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Declaration</em>'.
	 * @generated
	 */
	TypeDeclaration createTypeDeclaration();

} //DeclarationFactory
