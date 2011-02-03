package org.cpntools.accesscpn.model;

import java.util.List;



/**
 * @model abstract="true"
 * @author michael
 */
public interface Node extends org.cpntools.accesscpn.model.Object {
	/**
	 * @model containment="false" type="Arc"
	 * @return get all arcs where this node is source
	 */
	public List<Arc> getSourceArc();

	/**
	 * @model containment="false" type="Arc"
	 * @return get all arcs where this node is target
	 */
	public List<Arc> getTargetArc();

	/**
	 * @return whether this node is ready to be transmitted to the simulator
	 */
	public boolean isReady();
}
