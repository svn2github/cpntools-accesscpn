package org.cpntools.accesscpn.engine;

import java.io.IOException;

/**
 * @author mw
 */
public interface SimulatorImplementation {
	/**
	 * 
	 */
	void destroy();

	/**
	 * @return banner from simulator
	 */
	String getBanner();

	/**
	 * @return a packet from the simulator
	 * @throws IOException if an IO error occurred
	 */
	Packet receive() throws IOException;

	/**
	 * @param p packet to send
	 * @throws IOException if an IO error occurred
	 */
	void send(Packet p) throws IOException;

	/**
	 * Read data from eval stream.
	 * 
	 * @param data array to store in
	 * @param start first position to store in
	 * @param count max number of bytes to read
	 * @return the actual number of bytes read
	 * @throws IOException if an error occurs
	 * @see java.io.InputStream#read(byte[], int, int)
	 */
	int getEvalBytes(byte[] data, int start, int count) throws IOException;

}
