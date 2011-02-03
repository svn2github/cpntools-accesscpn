/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.cpntools.accesscpn.model.declaration.tests;

import org.cpntools.accesscpn.model.declaration.DeclarationFactory;
import org.cpntools.accesscpn.model.declaration.UseDeclaration;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Use Declaration</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class UseDeclarationTest extends DeclarationStructureTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(UseDeclarationTest.class);
	}

	/**
	 * Constructs a new Use Declaration test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UseDeclarationTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Use Declaration test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected UseDeclaration getFixture() {
		return (UseDeclaration)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(DeclarationFactory.eINSTANCE.createUseDeclaration());
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

} //UseDeclarationTest
