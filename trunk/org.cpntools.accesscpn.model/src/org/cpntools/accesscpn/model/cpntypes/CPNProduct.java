package org.cpntools.accesscpn.model.cpntypes;

import java.util.List;

/**
 * @model
 * @author michael
 */
public interface CPNProduct extends CPNType {
	/**
	 * @return types we are product of
	 * @model type="String" unique="false"
	 */
	public List<String> getTypes();

	/**
	 * @param type new type to add to product
	 */
	public void addSort(String type);
}
