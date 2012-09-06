package org.cpntools.accesscpn.engine.protocol;

/**
 * @author michael
 */
public class DMOSimulatorException extends Exception {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private final Packet p;

	/**
	 * @param p
	 */
	public DMOSimulatorException(final Packet p) {
		this.p = p;
	}

}
