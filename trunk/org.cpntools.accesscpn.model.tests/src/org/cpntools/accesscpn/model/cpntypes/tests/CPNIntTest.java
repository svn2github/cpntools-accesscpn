/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.cpntools.accesscpn.model.cpntypes.tests;

import org.cpntools.accesscpn.model.cpntypes.CPNInt;
import org.cpntools.accesscpn.model.cpntypes.CpntypesFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>CPN Int</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class CPNIntTest extends CPNTypeTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(CPNIntTest.class);
	}

	/**
	 * Constructs a new CPN Int test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CPNIntTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this CPN Int test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected CPNInt getFixture() {
		return (CPNInt)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(CpntypesFactory.eINSTANCE.createCPNInt());
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

} //CPNIntTest
