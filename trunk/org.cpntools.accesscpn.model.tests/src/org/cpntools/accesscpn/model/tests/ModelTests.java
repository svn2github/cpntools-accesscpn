/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.cpntools.accesscpn.model.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>model</b></em>' package.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelTests extends TestSuite {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Test suite() {
		TestSuite suite = new ModelTests("model Tests");
		suite.addTestSuite(NameTest.class);
		suite.addTestSuite(HLMarkingTest.class);
		suite.addTestSuite(TypeTest.class);
		suite.addTestSuite(HLAnnotationTest.class);
		suite.addTestSuite(ConditionTest.class);
		suite.addTestSuite(CodeTest.class);
		suite.addTestSuite(TimeTest.class);
		suite.addTestSuite(HLDeclarationTest.class);
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelTests(String name) {
		super(name);
	}

} //ModelTests
