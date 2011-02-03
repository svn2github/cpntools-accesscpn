package org.cpntools.accesscpn.model.cpntypes;

import java.util.List;

/**
 * @model
 * @author michael
 */
public interface CPNRecord extends CPNType {
	/**
	 * @return all elements of record
	 * @model required="true" type="NameTypePair" containment="true"
	 */
	public List<NameTypePair> getValues();

	/**
	 * @param id record id
	 * @param name name of type
	 */
	public void addValue(String id, String name);
}
