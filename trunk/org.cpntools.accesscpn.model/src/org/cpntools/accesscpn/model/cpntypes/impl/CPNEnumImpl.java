/**
 * <copyright> </copyright> $Id$
 */
package org.cpntools.accesscpn.model.cpntypes.impl;

import java.util.Collection;
import java.util.List;

import org.cpntools.accesscpn.model.cpntypes.CPNEnum;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;


/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>CPN Enum</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.cpntools.accesscpn.model.cpntypes.impl.CPNEnumImpl#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CPNEnumImpl extends CPNTypeImpl implements CPNEnum {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The cached value of the '{@link #getValues() <em>Values</em>}' attribute list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getValues()
	 * @generated
	 * @ordered
	 */
	protected EList<String> values;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected CPNEnumImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpntypesPackageImpl.Literals.CPN_ENUM;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public List<String> getValues() {
		if (values == null) {
			values = new EDataTypeUniqueEList<String>(String.class, this, CpntypesPackageImpl.CPN_ENUM__VALUES);
		}
		return values;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CpntypesPackageImpl.CPN_ENUM__VALUES:
				return getValues();
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
			case CpntypesPackageImpl.CPN_ENUM__VALUES:
				getValues().clear();
				getValues().addAll((Collection<? extends String>)newValue);
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
			case CpntypesPackageImpl.CPN_ENUM__VALUES:
				getValues().clear();
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
			case CpntypesPackageImpl.CPN_ENUM__VALUES:
				return values != null && !values.isEmpty();
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
		result.append(" (values: ");
		result.append(values);
		result.append(')');
		return result.toString();
	}

	/**
	 * @see org.cpntools.accesscpn.model.cpntypes.CPNType#asString()
	 */
	public String asString() {
		final StringBuilder sb = new StringBuilder();
		boolean kludge = false;
		sb.append("with ");
		for (final String value : getValues()) {
			if (kludge) {
				sb.append(" | ");
			}
			kludge = true;
			sb.append(value);
		}
		return sb.toString() + postFixAsString();
	}

	/**
	 * @see org.cpntools.accesscpn.model.cpntypes.CPNEnum#addValue(java.lang.String)
	 */
	public void addValue(final String value) {
		getValues().add(value);
	}

} // CPNEnumImpl
