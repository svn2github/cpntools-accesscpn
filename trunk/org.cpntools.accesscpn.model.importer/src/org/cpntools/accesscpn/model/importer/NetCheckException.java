package org.cpntools.accesscpn.model.importer;

import org.w3c.dom.Node;

/**
 * @author mw
 */
public class NetCheckException extends Exception {

	private static final long serialVersionUID = 1L;
	protected Node node;

	/**
	 * @param message describing message
	 * @param node node associated with error
	 */
	public NetCheckException(final String message, final Node node) {
		super(message);
		this.node = node;
	}

	/**
	 * @return node
	 */
	public Node getNode() {
		return node;
	}

}
