package org.cpntools.accesscpn.cosimulation;

import java.util.HashMap;
import java.util.concurrent.Semaphore;

/**
 * @author michael
 */
public class ExecutionContext extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	private final Semaphore lock = new Semaphore(1);

	/**
	 * Lock execution context (and prevent simulation). Should be used in subpage-plug-ins or superpage plug-ins to lock
	 * simulation which an atomic non-simulation action is taken.
	 */
	public void lock() {
		boolean done = false;
		while (!done) {
			try {
				lock.acquire();
				done = true;
			} catch (final InterruptedException e) { // I would like to extend my death wishes to anybody who thought
// returning with an InterruptedException when acquiring a lock withou notifying that you didn't obtain the lock!
				done = false;
			}
		}
	}

	/**
	 * Unlock execution context.
	 */
	public void unlock() {
		lock.release();
	}
}
