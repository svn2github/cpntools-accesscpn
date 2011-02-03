/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.cpntools.accesscpn.model.graphics.tests;

import org.cpntools.accesscpn.model.graphics.GraphicsFactory;
import org.cpntools.accesscpn.model.graphics.NodeGraphics;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Node Graphics</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class NodeGraphicsTest extends GraphicsTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(NodeGraphicsTest.class);
	}

	/**
	 * Constructs a new Node Graphics test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeGraphicsTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Node Graphics test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected NodeGraphics getFixture() {
		return (NodeGraphics)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(GraphicsFactory.eINSTANCE.createNodeGraphics());
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

} //NodeGraphicsTest
