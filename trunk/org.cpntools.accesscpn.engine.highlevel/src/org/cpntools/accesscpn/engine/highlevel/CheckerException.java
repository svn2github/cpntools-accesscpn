package org.cpntools.accesscpn.engine.highlevel;

/**
 * @author mw
 */
public class CheckerException extends Exception {

	private static final long serialVersionUID = 1L;
	private final String id;

	/**
	 * @param id
	 *            id associated with error
	 * @param message
	 *            describing text
	 */
	public CheckerException(final String id, final String message) {
		super(message);
		this.id = id;
	}

	/**
	 * @return id associated with error
	 */
	public String getId() {
		return id;
	}

	/**
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return getId() + ": " + super.getMessage();
	}
}
