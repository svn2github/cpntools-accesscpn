package org.cpntools.accesscpn.engine.highlevel.checker;

/**
 * @author mw
 */
public class ErrorInitializingSMLInterface extends Exception {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private final Exception e;

	/**
	 * @param e
	 *            exception causing faiult
	 */
	public ErrorInitializingSMLInterface(final Exception e) {
		this.e = e;
	}

	/**
	 * @return exception causing fault
	 */
	public Exception getException() {
		return e;
	}

}
