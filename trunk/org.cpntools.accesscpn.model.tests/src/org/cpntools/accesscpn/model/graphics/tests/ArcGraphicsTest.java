/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.cpntools.accesscpn.model.graphics.tests;

import org.cpntools.accesscpn.model.graphics.ArcGraphics;
import org.cpntools.accesscpn.model.graphics.GraphicsFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Arc Graphics</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ArcGraphicsTest extends GraphicsTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ArcGraphicsTest.class);
	}

	/**
	 * Constructs a new Arc Graphics test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArcGraphicsTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Arc Graphics test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ArcGraphics getFixture() {
		return (ArcGraphics)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(GraphicsFactory.eINSTANCE.createArcGraphics());
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

} //ArcGraphicsTest
