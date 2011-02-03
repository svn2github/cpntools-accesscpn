package org.cpntools.accesscpn.model.cpntypes;

import java.util.List;

/**
 * @model
 * @author michael
 */
public interface CPNEnum extends CPNType {
	/**
	 * @return all allowed values
	 * @model type="String"
	 */
	public List<String> getValues();

	/**
	 * @param value add an allowed value
	 */
	public void addValue(String value);
}
