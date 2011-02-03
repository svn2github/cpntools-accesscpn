/**
 * <copyright> </copyright> $Id$
 */
package org.cpntools.accesscpn.model.cpntypes.impl;

import org.cpntools.accesscpn.model.cpntypes.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;


/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * @generated
 */
public class CpntypesFactoryImpl extends EFactoryImpl implements CpntypesFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final CpntypesFactoryImpl eINSTANCE = init();

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static CpntypesFactoryImpl init() {
		try {
			CpntypesFactoryImpl theCpntypesFactory = (CpntypesFactoryImpl)EPackage.Registry.INSTANCE.getEFactory("http:///dk/au/daimi/ascoveco/cpn/model/cpntypes.ecore"); 
			if (theCpntypesFactory != null) {
				return theCpntypesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CpntypesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CpntypesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case CpntypesPackageImpl.CPN_ALIAS: return (EObject)createCPNAlias();
			case CpntypesPackageImpl.CPN_BOOL: return (EObject)createCPNBool();
			case CpntypesPackageImpl.CPN_ENUM: return (EObject)createCPNEnum();
			case CpntypesPackageImpl.CPN_INDEX: return (EObject)createCPNIndex();
			case CpntypesPackageImpl.CPN_INT: return (EObject)createCPNInt();
			case CpntypesPackageImpl.CPN_LIST: return (EObject)createCPNList();
			case CpntypesPackageImpl.CPN_PRODUCT: return (EObject)createCPNProduct();
			case CpntypesPackageImpl.CPN_REAL: return (EObject)createCPNReal();
			case CpntypesPackageImpl.CPN_RECORD: return (EObject)createCPNRecord();
			case CpntypesPackageImpl.CPN_STRING: return (EObject)createCPNString();
			case CpntypesPackageImpl.CPN_SUBSET: return (EObject)createCPNSubset();
			case CpntypesPackageImpl.CPN_UNION: return (EObject)createCPNUnion();
			case CpntypesPackageImpl.CPN_UNIT: return (EObject)createCPNUnit();
			case CpntypesPackageImpl.NAME_TYPE_PAIR: return (EObject)createNameTypePair();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CPNUnit createCPNUnit() {
		CPNUnitImpl cpnUnit = new CPNUnitImpl();
		return cpnUnit;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CPNEnum createCPNEnum() {
		CPNEnumImpl cpnEnum = new CPNEnumImpl();
		return cpnEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CPNProduct createCPNProduct() {
		CPNProductImpl cpnProduct = new CPNProductImpl();
		return cpnProduct;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CPNReal createCPNReal() {
		CPNRealImpl cpnReal = new CPNRealImpl();
		return cpnReal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CPNIndex createCPNIndex() {
		CPNIndexImpl cpnIndex = new CPNIndexImpl();
		return cpnIndex;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CPNString createCPNString() {
		CPNStringImpl cpnString = new CPNStringImpl();
		return cpnString;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CPNAlias createCPNAlias() {
		CPNAliasImpl cpnAlias = new CPNAliasImpl();
		return cpnAlias;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CPNInt createCPNInt() {
		CPNIntImpl cpnInt = new CPNIntImpl();
		return cpnInt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * 
	 * @return a new CPNBool <!-- end-user-doc -->
	 * @generated
	 */
	public CPNBool createCPNBool() {
		CPNBoolImpl cpnBool = new CPNBoolImpl();
		return cpnBool;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CPNList createCPNList() {
		CPNListImpl cpnList = new CPNListImpl();
		return cpnList;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CPNRecord createCPNRecord() {
		CPNRecordImpl cpnRecord = new CPNRecordImpl();
		return cpnRecord;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CPNUnion createCPNUnion() {
		CPNUnionImpl cpnUnion = new CPNUnionImpl();
		return cpnUnion;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CPNSubset createCPNSubset() {
		CPNSubsetImpl cpnSubset = new CPNSubsetImpl();
		return cpnSubset;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NameTypePair createNameTypePair() {
		NameTypePairImpl nameTypePair = new NameTypePairImpl();
		return nameTypePair;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CpntypesPackageImpl getCpntypesPackage() {
		return (CpntypesPackageImpl)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CpntypesPackageImpl getPackage() {
		return CpntypesPackageImpl.eINSTANCE;
	}

} // CpntypesFactoryImpl
