package org.cpntools.accesscpn.engine.proxy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.cpntools.accesscpn.engine.Packet;


public class ProxyDaemon extends Thread {
	private ServerSocket s;

	private static ProxyDaemon defaultInstance;

	public synchronized static ProxyDaemon getDefaultInstance() throws Exception {
		if (defaultInstance == null) {
			defaultInstance = new ProxyDaemon(2098);
			defaultInstance.start();
		}
		return defaultInstance;
	}

	public ProxyDaemon(final int port) throws Exception {
		super("Proxy Simulator (port = " + port + ")");
		setDaemon(true);
		simulators = new BlockingQueue<ProxySimulator>();
		try {
			s = new ServerSocket(port);
		} catch (final Exception e) {
			throw new Exception(
					"Could not start Proxy Daemon. Most likely CPN Tools is running. Try shutting down CPN Tools (including any cpnmld.* processes) and try again.");
		}
	}

	private final BlockingQueue<ProxySimulator> simulators;

	/**
	 * Get the next ProxySimulator connecting
	 * 
	 * @return
	 */
	public ProxySimulator getNext() {
		return simulators.get();
	}

	/**
	 * Clear the queue of ProxySimulators. Only ensures that at some point after calling the queue
	 * will be empty. Suggested use: clear the queue, display a message to the user to connect,
	 * getNext to (most likely) get the one the user connected.
	 */
	public void clear() {
		while (simulators.size() > 0) {
			simulators.get();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				final Packet p = new Packet();

				final Socket cpnmld = s.accept();
				final DataOutputStream cpnmldout = new DataOutputStream(new BufferedOutputStream(
						cpnmld.getOutputStream()));
				final DataInputStream cpnmldin = new DataInputStream(new BufferedInputStream(cpnmld
						.getInputStream()));
				p.receive(cpnmldin);
				p.send(cpnmldout);

				final Socket dmoeval = s.accept();
				final DataOutputStream dmoevalout = new DataOutputStream(new BufferedOutputStream(
						dmoeval.getOutputStream()));
				final DataInputStream dmoevalin = new DataInputStream(new BufferedInputStream(
						dmoeval.getInputStream()));
				dmoeval.setTcpNoDelay(true);
				dmoeval.setReceiveBufferSize(1);
				p.receive(dmoevalin);
				p.send(dmoevalout);

				final Socket dmo = s.accept();
				final DataOutputStream dmoout = new DataOutputStream(new BufferedOutputStream(dmo
						.getOutputStream()));
				final DataInputStream dmoin = new DataInputStream(new BufferedInputStream(dmo
						.getInputStream()));
				dmo.setTcpNoDelay(true);
				dmo.setReceiveBufferSize(1);
				p.receive(dmoin);
				p.send(dmoout);

				p.receive(cpnmldin);
				if (p.getOpcode() != 1 && p.getOpcode() != 2) {
					new Packet(3, "").send(cpnmldout); //$NON-NLS-1$
					throw new Exception("Wrong login"); //$NON-NLS-1$
				}
				new Packet(1, "").send(cpnmldout); //$NON-NLS-1$

				p.receive(cpnmldin);
				if (p.getOpcode() != 3) {
					new Packet(3, "").send(cpnmldout); //$NON-NLS-1$
					throw new Exception("No new session"); //$NON-NLS-1$
				}
				new Packet(1, "").send(cpnmldout); //$NON-NLS-1$
				final ProxySimulator proxySimulator = new ProxySimulator(cpnmld, dmo, dmoeval,
						dmoevalout, dmoin, dmoout);
				proxySimulator.start();
				simulators.put(proxySimulator);
			} catch (final Exception e) {
				// Ignore failure of a single connection
			}
		}
	}
}
