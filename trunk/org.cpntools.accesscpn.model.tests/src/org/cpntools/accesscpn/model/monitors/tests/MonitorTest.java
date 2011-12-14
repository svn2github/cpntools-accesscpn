/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.cpntools.accesscpn.model.monitors.tests;

import junit.textui.TestRunner;

import org.cpntools.accesscpn.model.monitors.Monitor;

import org.cpntools.accesscpn.model.monitors.impl.MonitorsFactoryImpl;

import org.cpntools.accesscpn.model.tests.HasIdTest;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Monitor</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class MonitorTest extends HasIdTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(MonitorTest.class);
	}

	/**
	 * Constructs a new Monitor test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MonitorTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Monitor test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Monitor getFixture() {
		return (Monitor)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(MonitorsFactoryImpl.eINSTANCE.createMonitor());
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

} //MonitorTest
