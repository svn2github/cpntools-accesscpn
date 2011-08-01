package org.cpntools.accesscpn.cosimulation.impl;

import java.util.Collections;

import org.cpntools.accesscpn.cosimulation.ExecutionContext;

/**
 * @author michael
 */
public class EmptyCosimulation extends CPNToolsCosimulation {

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public EmptyCosimulation() {
		super(null, null, new ExecutionContext(), Collections.EMPTY_MAP, Collections.EMPTY_MAP, Collections.EMPTY_MAP);
	}

}
