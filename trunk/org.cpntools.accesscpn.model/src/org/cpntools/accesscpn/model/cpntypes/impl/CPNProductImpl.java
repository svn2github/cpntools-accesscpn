/**
 * <copyright> </copyright> $Id$
 */
package org.cpntools.accesscpn.model.cpntypes.impl;

import java.util.Collection;
import java.util.List;

import org.cpntools.accesscpn.model.cpntypes.CPNProduct;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EDataTypeEList;


/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>CPN Product</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.cpntools.accesscpn.model.cpntypes.impl.CPNProductImpl#getTypes <em>Types</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CPNProductImpl extends CPNTypeImpl implements CPNProduct {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The cached value of the '{@link #getTypes() <em>Types</em>}' attribute list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<String> types;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected CPNProductImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpntypesPackageImpl.Literals.CPN_PRODUCT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public List<String> getTypes() {
		if (types == null) {
			types = new EDataTypeEList<String>(String.class, this, CpntypesPackageImpl.CPN_PRODUCT__TYPES);
		}
		return types;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CpntypesPackageImpl.CPN_PRODUCT__TYPES:
				return getTypes();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CpntypesPackageImpl.CPN_PRODUCT__TYPES:
				getTypes().clear();
				getTypes().addAll((Collection<? extends String>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CpntypesPackageImpl.CPN_PRODUCT__TYPES:
				getTypes().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CpntypesPackageImpl.CPN_PRODUCT__TYPES:
				return types != null && !types.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (types: ");
		result.append(types);
		result.append(')');
		return result.toString();
	}

	/**
	 * @see org.cpntools.accesscpn.model.cpntypes.CPNType#asString()
	 */
	public String asString() {
		final StringBuilder sb = new StringBuilder();
		boolean kludge = false;
		for (final String type : getTypes()) {
			if (kludge) {
				sb.append(" * ");
			}
			kludge = true;
			sb.append(type);
		}
		return sb.toString() + postFixAsString();
	}

	/**
	 * @see org.cpntools.accesscpn.model.cpntypes.CPNProduct#addSort(java.lang.String)
	 */
	public void addSort(final String type) {
		getTypes().add(type);
	}

} // CPNProductImpl
