/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.cpntools.accesscpn.model.declaration.tests;

import org.cpntools.accesscpn.model.declaration.DeclarationFactory;
import org.cpntools.accesscpn.model.declaration.VariableDeclaration;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Variable Declaration</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class VariableDeclarationTest extends DeclarationStructureTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(VariableDeclarationTest.class);
	}

	/**
	 * Constructs a new Variable Declaration test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VariableDeclarationTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Variable Declaration test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected VariableDeclaration getFixture() {
		return (VariableDeclaration)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(DeclarationFactory.eINSTANCE.createVariableDeclaration());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //VariableDeclarationTest
