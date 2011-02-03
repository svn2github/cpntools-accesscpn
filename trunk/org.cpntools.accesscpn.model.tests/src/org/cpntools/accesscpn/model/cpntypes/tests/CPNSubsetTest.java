/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.cpntools.accesscpn.model.cpntypes.tests;

import org.cpntools.accesscpn.model.cpntypes.CPNSubset;
import org.cpntools.accesscpn.model.cpntypes.CpntypesFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>CPN Subset</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class CPNSubsetTest extends CPNTypeTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(CPNSubsetTest.class);
	}

	/**
	 * Constructs a new CPN Subset test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CPNSubsetTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this CPN Subset test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected CPNSubset getFixture() {
		return (CPNSubset)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(CpntypesFactory.eINSTANCE.createCPNSubset());
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

} //CPNSubsetTest
