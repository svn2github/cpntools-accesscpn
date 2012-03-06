/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.cpntools.accesscpn.model.aux.util;

import org.cpntools.accesscpn.model.HasId;
import org.cpntools.accesscpn.model.HasLabel;
import org.cpntools.accesscpn.model.HasName;
import org.cpntools.accesscpn.model.HasToolInfo;

import org.cpntools.accesscpn.model.aux.*;

import org.cpntools.accesscpn.model.aux.impl.AuxPackageImpl;

import org.cpntools.accesscpn.model.graphics.HasGraphics;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.cpntools.accesscpn.model.aux.impl.AuxPackageImpl
 * @generated
 */
public class AuxAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AuxPackageImpl modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuxAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = AuxPackageImpl.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AuxSwitch<Adapter> modelSwitch =
		new AuxSwitch<Adapter>() {
			@Override
			public Adapter caseAux(Aux object) {
				return createAuxAdapter();
			}
			@Override
			public Adapter caseText(Text object) {
				return createTextAdapter();
			}
			@Override
			public Adapter caseHasId(HasId object) {
				return createHasIdAdapter();
			}
			@Override
			public Adapter caseHasToolInfo(HasToolInfo object) {
				return createHasToolInfoAdapter();
			}
			@Override
			public Adapter caseHasGraphics(HasGraphics object) {
				return createHasGraphicsAdapter();
			}
			@Override
			public Adapter caseHasLabel(HasLabel object) {
				return createHasLabelAdapter();
			}
			@Override
			public Adapter caseHasName(HasName object) {
				return createHasNameAdapter();
			}
			@Override
			public Adapter caseObject(org.cpntools.accesscpn.model.Object object) {
				return createObjectAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.cpntools.accesscpn.model.aux.Aux <em>Aux</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.cpntools.accesscpn.model.aux.Aux
	 * @generated
	 */
	public Adapter createAuxAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cpntools.accesscpn.model.aux.Text <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.cpntools.accesscpn.model.aux.Text
	 * @generated
	 */
	public Adapter createTextAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cpntools.accesscpn.model.HasId <em>Has Id</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.cpntools.accesscpn.model.HasId
	 * @generated
	 */
	public Adapter createHasIdAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cpntools.accesscpn.model.HasToolInfo <em>Has Tool Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.cpntools.accesscpn.model.HasToolInfo
	 * @generated
	 */
	public Adapter createHasToolInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cpntools.accesscpn.model.graphics.HasGraphics <em>Has Graphics</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.cpntools.accesscpn.model.graphics.HasGraphics
	 * @generated
	 */
	public Adapter createHasGraphicsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cpntools.accesscpn.model.HasLabel <em>Has Label</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.cpntools.accesscpn.model.HasLabel
	 * @generated
	 */
	public Adapter createHasLabelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cpntools.accesscpn.model.HasName <em>Has Name</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.cpntools.accesscpn.model.HasName
	 * @generated
	 */
	public Adapter createHasNameAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.cpntools.accesscpn.model.Object <em>Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.cpntools.accesscpn.model.Object
	 * @generated
	 */
	public Adapter createObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //AuxAdapterFactory
