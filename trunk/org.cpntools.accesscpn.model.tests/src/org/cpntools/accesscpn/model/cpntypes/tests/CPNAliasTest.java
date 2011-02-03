/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.cpntools.accesscpn.model.cpntypes.tests;

import org.cpntools.accesscpn.model.cpntypes.CPNAlias;
import org.cpntools.accesscpn.model.cpntypes.CpntypesFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>CPN Alias</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class CPNAliasTest extends CPNTypeTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(CPNAliasTest.class);
	}

	/**
	 * Constructs a new CPN Alias test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CPNAliasTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this CPN Alias test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected CPNAlias getFixture() {
		return (CPNAlias)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(CpntypesFactory.eINSTANCE.createCPNAlias());
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

} //CPNAliasTest
