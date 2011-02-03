/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.cpntools.accesscpn.model.cpntypes.tests;

import org.cpntools.accesscpn.model.cpntypes.CPNProduct;
import org.cpntools.accesscpn.model.cpntypes.CpntypesFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>CPN Product</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class CPNProductTest extends CPNTypeTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(CPNProductTest.class);
	}

	/**
	 * Constructs a new CPN Product test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CPNProductTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this CPN Product test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected CPNProduct getFixture() {
		return (CPNProduct)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(CpntypesFactory.eINSTANCE.createCPNProduct());
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

} //CPNProductTest
