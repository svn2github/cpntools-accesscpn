package org.cpntools.accesscpn.model.aux;

import org.cpntools.accesscpn.model.graphics.NodeGraphics;

/**
 * @model abstract="true"
 * @author michael
 */
public interface Aux extends org.cpntools.accesscpn.model.Object {

	/**
	 * @see org.cpntools.accesscpn.model.graphics.HasGraphics#getGraphics()
	 */
	public NodeGraphics getNodeGraphics();

}
