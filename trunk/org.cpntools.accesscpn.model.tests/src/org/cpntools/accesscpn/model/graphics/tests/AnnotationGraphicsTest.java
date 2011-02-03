/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.cpntools.accesscpn.model.graphics.tests;

import org.cpntools.accesscpn.model.graphics.AnnotationGraphics;
import org.cpntools.accesscpn.model.graphics.GraphicsFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Annotation Graphics</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class AnnotationGraphicsTest extends GraphicsTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(AnnotationGraphicsTest.class);
	}

	/**
	 * Constructs a new Annotation Graphics test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnnotationGraphicsTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Annotation Graphics test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected AnnotationGraphics getFixture() {
		return (AnnotationGraphics)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(GraphicsFactory.eINSTANCE.createAnnotationGraphics());
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

} //AnnotationGraphicsTest
