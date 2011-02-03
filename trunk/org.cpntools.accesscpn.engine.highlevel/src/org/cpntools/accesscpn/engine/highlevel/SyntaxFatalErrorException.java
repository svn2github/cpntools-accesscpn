package org.cpntools.accesscpn.engine.highlevel;

/**
 * @author mw
 */
public class SyntaxFatalErrorException extends CheckerException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 *            describing text
	 */
	public SyntaxFatalErrorException(final String message) {
		super("-1", message);
	}
}
