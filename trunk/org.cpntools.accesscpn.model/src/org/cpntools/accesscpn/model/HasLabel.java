package org.cpntools.accesscpn.model;

import java.util.List;



/**
 * @model abstract="true" interface="true"
 * @author michael Addition compared to HLPN standard. Makes it possible to have reverse references for labels.
 */
public interface HasLabel {
	/**
	 * @model containment="true" opposite="parent" type="Label"
	 * @return the labels of the object
	 */
	public List<Label> getLabel();
}
