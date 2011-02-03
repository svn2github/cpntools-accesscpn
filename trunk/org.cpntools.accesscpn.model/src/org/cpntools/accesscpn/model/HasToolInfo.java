package org.cpntools.accesscpn.model;

import java.util.List;



/**
 * @model abstract="true" interface="true"
 * @author michael Addition compared to HLPN standard. Added to make reverse references for toolInfo.
 */
public interface HasToolInfo {
	/**
	 * @model containment="true" opposite="parent" type="ToolInfo"
	 * @return the toolinfo of the object
	 */
	public List<ToolInfo> getToolinfo();
}
