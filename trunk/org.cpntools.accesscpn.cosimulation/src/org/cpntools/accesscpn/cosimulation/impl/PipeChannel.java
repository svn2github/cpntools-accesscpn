package org.cpntools.accesscpn.cosimulation.impl;

import java.util.Collection;

import org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNValue;

public class PipeChannel extends AbstractChannel {
	@Override
	public void offer(final Collection<CPNValue> offers) {
		final CPNValue[] array = new CPNValue[offers.size()];
		offers.toArray(array);
		publishOffer(array);
	}
}
