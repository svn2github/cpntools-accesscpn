package org.cpntools.accesscpn.engine.highlevel;

/**
 * @author mw
 */
public class SyntaxCheckerException extends CheckerException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 *            id of element associated with error
	 * @param message
	 *            describing text of error
	 */
	public SyntaxCheckerException(final String id, final String message) {
		super(id, message);
	}
}
