package org.cpntools.accesscpn.engine.proxy;

import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;

import org.cpntools.accesscpn.engine.Packet;
import org.cpntools.accesscpn.engine.Simulator;
import org.cpntools.accesscpn.engine.Simulator.PacketSent;
import org.cpntools.accesscpn.engine.highlevel.HighLevelSimulator;

/**
 * @author michael
 */
public abstract class PacketInspector implements Observer {
	protected final HighLevelSimulator simulator;

	/**
	 * @param simulator
	 */
	public PacketInspector(final HighLevelSimulator simulator) {
		this.simulator = simulator;
	}

	/**
	 * 
	 */
	public void attach() {
		simulator.getSimulator().addObserver(this);
	}

	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(final Observable arg0, final java.lang.Object arg1) {
		if (arg1 != null && arg1 instanceof Simulator.PacketSent) {
			try {
				handlePacket(((PacketSent) arg1).getPacket());
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void handlePacket(final Packet packet) throws Exception {
		if (packet.getOpcode() == 9) {
			packet.reset();
			try {
				final int command = packet.getInteger();
				if (command == 200) {
					handleMiscPacket(packet);
				} else if (command == 300) {
					handleDeclPacket(packet);
				} else if (command == 400) {
					handleSyntaxPacket(packet);
				} else if (command == 500) {
					handleSimulatePacket(packet);
				}
			} catch (final NoSuchElementException e) {

			}
		}
	}

	protected abstract void handleSimulatePacket(final Packet packet) throws Exception;

	protected abstract void handleSyntaxPacket(final Packet packet) throws Exception;

	protected abstract void handleDeclPacket(final Packet packet) throws Exception;

	protected abstract void handleMiscPacket(final Packet packet) throws Exception;
}
