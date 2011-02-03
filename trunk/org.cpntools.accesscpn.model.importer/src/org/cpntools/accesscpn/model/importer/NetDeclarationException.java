package org.cpntools.accesscpn.model.importer;

import org.w3c.dom.Node;

/**
 * @author mw
 */
public class NetDeclarationException extends NetCheckException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message describing message
	 * @param node node associated with error
	 */
	public NetDeclarationException(final String message, final Node node) {
		super(message, node);
	}
}
