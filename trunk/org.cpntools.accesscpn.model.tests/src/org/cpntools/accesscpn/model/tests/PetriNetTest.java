/************************************************************************/
/* Access/CPN                                                           */
/* Copyright 2010-2011 AIS Group, Eindhoven University of Technology    */
/*                                                                      */
/* This library is free software; you can redistribute it and/or        */
/* modify it under the terms of the GNU Lesser General Public           */
/* License as published by the Free Software Foundation; either         */
/* version 2.1 of the License, or (at your option) any later version.   */
/*                                                                      */
/* This library is distributed in the hope that it will be useful,      */
/* but WITHOUT ANY WARRANTY; without even the implied warranty of       */
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU    */
/* Lesser General Public License for more details.                      */
/*                                                                      */
/* You should have received a copy of the GNU Lesser General Public     */
/* License along with this library; if not, write to the Free Software  */
/* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,           */
/* MA  02110-1301  USA                                                  */
/************************************************************************/
/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.cpntools.accesscpn.model.tests;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.cpntools.accesscpn.model.Arc;
import org.cpntools.accesscpn.model.FusionGroup;
import org.cpntools.accesscpn.model.HLDeclaration;
import org.cpntools.accesscpn.model.ModelFactory;
import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.Place;
import org.cpntools.accesscpn.model.RefPlace;
import org.cpntools.accesscpn.model.Transition;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;


/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Petri Net</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class PetriNetTest extends TestCase {

	/**
	 * The fixture for this Petri Net test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PetriNet fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PetriNetTest.class);
	}

	/**
	 * Constructs a new Petri Net test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PetriNetTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Petri Net test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(PetriNet fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Petri Net test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PetriNet getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ModelFactory.eINSTANCE.createPetriNet());
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
	
	public void testPrint() {
		PetriNet net = getFixture();
		System.out.println("Decls");
		for (HLDeclaration decl : net.declaration()) {
			System.out.println(" - " + decl.asString());
		}
		
		List<Page> list = net.getPage();
		for(Page p : list) {
			System.out.println("Page: " + p.getName().getText());
			
			for (Place place : p.place()) {
				System.out.println("Place: " + place.getName().getText());
				System.out.println("   - type:     " + place.getType().getText());
				System.out.println("   - initmark: " + place.getInitialMarking().getText());
			}
			
			for (Transition trans : p.transition()) {
				System.out.println("Trans: " + trans.getName().getText());
				System.out.println("   - guard:  " + trans.getCondition().getText());
				System.out.println("   - time:   " + trans.getTime().getText());
				System.out.println("   - action: " + trans.getCode().getText());
			}
			
			List<Arc> arcList = p.getArc();
			for (Arc arc : arcList) {
				System.out.println("Arc");
				System.out.println("   - inscription: " + arc.getHlinscription().getText());
				System.out.println("   - From:        " + arc.getSource().getName().getText());
				System.out.println("   - To:          " + arc.getTarget().getName().getText());
			}

			for (RefPlace rp : p.portPlace()) {
				System.out.println("Port: " + rp.getName().getText() + ", id: " + rp.getId());
			}
		}
		
		for (FusionGroup fg : net.getFusionGroups()) {
			System.out.println("Fusion group name: " + fg.getName().getText() + ", id: " + fg.getId());
			for (RefPlace rp : fg.getReferences()) {
				System.out.println(" - refpalce name: " + rp.getName().getText() + ", id: " + rp.getId());
			}
		}
	}
	
	public void testLoadSave() {
		URI uri = URI.createFileURI("test.tmp.model");
		XMLResourceImpl xmlResource = new XMLResourceImpl(uri);
		xmlResource.getContents().add(getFixture());
		try {
			xmlResource.save(null);
		}
		catch (IOException ioe) {
			fail("Saving failed" + ioe);
		}
		
		xmlResource = new XMLResourceImpl(uri);
		try {
			xmlResource.load(null);
		}
		catch (IOException e) {
			fail("Loading failed" + e);
		}
		
		assertTrue("Loaded object is a Perti net", xmlResource.getContents().get(0) instanceof PetriNet);
	}


} //PetriNetTest
