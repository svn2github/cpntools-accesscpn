package org.cpntools.accesscpn.model.cpntypes;

import java.util.List;

/**
 * @model
 * @author keblov
 */
public interface CPNUnion extends CPNType {
	/**
	 * @return all elements of unit
	 * @model required="true" type="NameTypePair" containment="true"
	 */
	public List<NameTypePair> getValues();

	/**
	 * @param id constructor
	 * @param name name of type
	 */
	public void addValue(String id, String name);

}
