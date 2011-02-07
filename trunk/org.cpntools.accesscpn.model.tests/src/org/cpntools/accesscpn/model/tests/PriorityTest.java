/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.cpntools.accesscpn.model.tests;

import junit.textui.TestRunner;

import org.cpntools.accesscpn.model.Priority;

import org.cpntools.accesscpn.model.impl.ModelFactoryImpl;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Priority</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class PriorityTest extends AnnotationTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PriorityTest.class);
	}

	/**
	 * Constructs a new Priority test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PriorityTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Priority test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Priority getFixture() {
		return (Priority)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ModelFactoryImpl.eINSTANCE.createPriority());
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

} //PriorityTest
