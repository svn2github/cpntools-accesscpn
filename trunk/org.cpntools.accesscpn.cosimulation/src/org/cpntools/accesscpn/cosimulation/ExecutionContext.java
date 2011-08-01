package org.cpntools.accesscpn.cosimulation;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author michael
 */
public class ExecutionContext extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	private final Lock lock = new ReentrantLock();

	/**
	 * Lock execution context (and prevent simulation). Should be used in subpage-plug-ins or superpage plug-ins to lock
	 * simulation which an atomic non-simulation action is taken.
	 */
	public void lock() {
		lock.lock();
	}

	/**
	 * Unlock execution context.
	 */
	public void unlock() {
		lock.unlock();
	}
}
