/**
 * 
 */
package org.cpntools.accesscpn.cosimulation;

import org.cpntools.accesscpn.model.cpntypes.CPNType;

public class ChannelDescription<T> {
	private final T channel;
	private final CPNType type;
	private final String name;

	public ChannelDescription(final String name, final CPNType type, final T channel) {
		this.name = name;
		this.type = type;
		this.channel = channel;
	}

	public T getChannel() {
		return channel;
	}

	public String getName() {
		return name;
	}

	public CPNType getType() {
		return type;
	}
}