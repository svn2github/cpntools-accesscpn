package org.cpntools.accesscpn.engine.highlevel;

/**
 * @author michael
 */
public class LocalCheckFailed extends CheckerException {

	/**
     * 
     */
	private static final long serialVersionUID = 7264778633543323410L;

	/**
	 * @param id
	 * @param message
	 */
	public LocalCheckFailed(final String id, final String message) {
		super(id, message);
	}

}
