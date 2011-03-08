package org.cpntools.accesscpn.cosimulation;

import java.util.Collection;

public abstract class AbstractSubpage implements SubpagePlugin {
	protected boolean done = false;
	protected ExecutionContext context;

	@Override
	public void end() {
		done = true;
	}

	@Override
	public boolean isDone() {
		return done;
	}

	@Override
	public void run() {

	}

	@Override
	public boolean setInterface(final Collection<ChannelDescription<InputChannel>> inputs,
			final Collection<ChannelDescription<OutputChannel>> outputs,
			final Collection<ChannelDescription<DataStore>> data) {
		return false;
	}

	@Override
	public void start(final ExecutionContext context) throws Exception {
		this.context = context;
		run();
	}

	protected void done() {
		done = true;
	}
}
