/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.cpntools.accesscpn.model.cpntypes.tests;

import org.cpntools.accesscpn.model.cpntypes.CPNEnum;
import org.cpntools.accesscpn.model.cpntypes.CpntypesFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>CPN Enum</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class CPNEnumTest extends CPNTypeTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(CPNEnumTest.class);
	}

	/**
	 * Constructs a new CPN Enum test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CPNEnumTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this CPN Enum test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected CPNEnum getFixture() {
		return (CPNEnum)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(CpntypesFactory.eINSTANCE.createCPNEnum());
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

} //CPNEnumTest
