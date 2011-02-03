package org.cpntools.accesscpn.engine.highlevel.instance;

import java.util.List;

import org.cpntools.accesscpn.model.PlaceNode;
import org.eclipse.emf.ecore.EObject;


/**
 * @model
 * @author mw
 */
public interface Marking {
	/**
	 * @model containment="false"
	 * @return
	 */
	Instance<? extends PlaceNode> getPlaceInstance();

	/**
	 * Sets the value of the '
	 * {@link org.cpntools.accesscpn.engine.highlevel.instance.Marking#getPlaceInstance
	 * <em>Place Instance</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Place Instance</em>' containment reference.
	 * @see #getPlaceInstance()
	 * @generated
	 */
	void setPlaceInstance(Instance<? extends PlaceNode> value);

	/**
	 * @model
	 * @return
	 */
	int getTokenCount();

	/**
	 * Sets the value of the '{@link org.cpntools.accesscpn.engine.highlevel.instance.Marking#getTokenCount <em>Token Count</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Token Count</em>' attribute.
	 * @see #getTokenCount()
	 * @generated
	 */
	void setTokenCount(int value);

	/**
	 * @model changeable="false" transient="true"
	 * @return
	 */
	String getMarking();

	/**
	 * @model containment="true"
	 * @return
	 */
	List<MultisetEntry> getStructuredMarking();
}
