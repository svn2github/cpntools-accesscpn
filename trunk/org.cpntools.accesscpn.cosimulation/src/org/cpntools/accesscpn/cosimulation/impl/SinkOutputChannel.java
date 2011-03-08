package org.cpntools.accesscpn.cosimulation.impl;

import java.util.Collection;

import org.cpntools.accesscpn.cosimulation.OutputChannel;
import org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNValue;

public class SinkOutputChannel implements OutputChannel {
	private boolean closed;

	@Override
	public void close() {
		closed = true;
	}

	@Override
	public boolean isClosed() {
		return closed;
	}

	@Override
	public void offer(final Collection<CPNValue> offers) {
		// Discard
	}
}
