package org.cpntools.accesscpn.cosimulation;

import java.util.Collection;

/**
 * @author michael
 * 
 */
public interface SubpagePlugin extends CPNToolsPlugin, Runnable {
	/**
	 * This method defines all channels this plugin may use to communication with the surroundings.
	 * This is called before startSimulation.
	 * 
	 * @param inputs
	 * @param outputs
	 * @param data
	 * @return true if the plugin accepts the interface, false otherwise
	 */
	boolean setInterface(Collection<ChannelDescription<InputChannel>> inputs,
			Collection<ChannelDescription<OutputChannel>> outputs,
			Collection<ChannelDescription<DataStore>> data);
}
