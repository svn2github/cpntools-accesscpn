package org.cpntools.accesscpn.cosimulation;

/**
 * @author michael
 * 
 */
public interface CPNToolsPlugin {
	/**
	 * Called after each run has ended but before endSimulation is called.
	 */
	void end();

	/**
	 * Called before each run is started but after startSimulation has been called.
	 * 
	 * @throws Exception
	 */
	void start() throws Exception;
}
