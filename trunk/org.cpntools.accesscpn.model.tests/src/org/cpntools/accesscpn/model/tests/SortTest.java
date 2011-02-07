/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.cpntools.accesscpn.model.tests;

import junit.textui.TestRunner;

import org.cpntools.accesscpn.model.Sort;

import org.cpntools.accesscpn.model.impl.ModelFactoryImpl;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Sort</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class SortTest extends AnnotationTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(SortTest.class);
	}

	/**
	 * Constructs a new Sort test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SortTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Sort test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Sort getFixture() {
		return (Sort)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ModelFactoryImpl.eINSTANCE.createSort());
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

} //SortTest
