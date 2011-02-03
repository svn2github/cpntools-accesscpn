package org.cpntools.accesscpn.model;

import java.util.List;



/**
 * @model
 * @author michael
 */
public interface Place extends PlaceNode {
	/**
	 * @model containment="false" type="RefPlace"
	 * @return all references to this place
	 */
	List<RefPlace> getReferences();
}
