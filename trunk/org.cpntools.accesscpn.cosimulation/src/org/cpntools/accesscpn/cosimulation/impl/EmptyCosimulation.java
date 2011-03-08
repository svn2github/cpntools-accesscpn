package org.cpntools.accesscpn.cosimulation.impl;

import java.util.Collections;

public class EmptyCosimulation extends CPNToolsCosimulation {

	@SuppressWarnings("unchecked")
	public EmptyCosimulation() {
		super(null, null, Collections.EMPTY_MAP, Collections.EMPTY_MAP, Collections.EMPTY_MAP);
	}

}
