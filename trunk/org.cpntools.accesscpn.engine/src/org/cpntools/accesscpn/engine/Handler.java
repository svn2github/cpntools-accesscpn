package org.cpntools.accesscpn.engine;

import java.util.List;

/**
 * @author mw
 */
public interface Handler {
	/**
	 * @param values parameters
	 * @return result
	 */
	Object handle(List<Object> values);
}
