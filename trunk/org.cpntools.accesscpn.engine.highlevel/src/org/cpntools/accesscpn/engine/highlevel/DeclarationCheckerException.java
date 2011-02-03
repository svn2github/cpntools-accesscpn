package org.cpntools.accesscpn.engine.highlevel;

/**
 * @author mw
 */
public class DeclarationCheckerException extends CheckerException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 *            id associated with error
	 * @param message
	 *            describing message
	 */
	public DeclarationCheckerException(final String id, final String message) {
		super(id, message);
	}
}
