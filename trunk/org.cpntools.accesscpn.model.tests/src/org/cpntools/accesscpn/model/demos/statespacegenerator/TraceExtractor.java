package org.cpntools.accesscpn.model.demos.statespacegenerator;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.cpntools.accesscpn.engine.highlevel.HighLevelSimulator;
import org.cpntools.accesscpn.engine.highlevel.instance.Instance;
import org.cpntools.accesscpn.engine.highlevel.instance.State;
import org.cpntools.accesscpn.engine.proxy.ProxyDaemon;
import org.cpntools.accesscpn.engine.proxy.ProxySimulator;
import org.cpntools.accesscpn.model.Place;


public class TraceExtractor {

	/**
	 * @param args
	 * @throws Exception
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	public static void main(final String[] args) throws InterruptedException, Exception {
		int state = 0;
		if (args.length > 0) state = Integer.parseInt(args[0]);
		System.out.println("Searching for state " + state);
		final ProxyDaemon pd = new ProxyDaemon(2099);
		pd.start();
		System.out.println("Waiting for CPN Tools");
		final ProxySimulator ps = pd.getNext();
		System.out.println("Waiting for syntax check");
		while (ps.getPetriNet() == null)
			Thread.sleep(500);
		final HighLevelSimulator simulator = ps.getSimulator();

		final List<Instance<Place>> allRealPlaceInstances = simulator.getAllRealPlaceInstances();

		System.out.println("Reading strings");
		final ObjectInputStream checkpoint = new ObjectInputStream(new BufferedInputStream(
				new FileInputStream(new File("checkpoint.dmp"))));
		final ArrayList<String> values = (ArrayList<String>) checkpoint.readObject();
		if (values != null) {
			CompressedState.setValues(values);

			System.out.print("Reading states");
			final ObjectInputStream states = new ObjectInputStream(new BufferedInputStream(
					new FileInputStream(new File("states.dmp"))));
			final List<CompressedState> stateArray = new ArrayList<CompressedState>();
			final List<String> bindings = new ArrayList<String>();
			for (int i = 0; i <= state; i++) {
				final CompressedState c = (CompressedState) states.readObject();
				if (!c.isCheckpoint()) {
					c.setStateNumber(i);
					stateArray.add(c);
					final String b = states.readUTF();
					bindings.add(b);
				} else
					i--;
				if (i % 10000 == 0) System.out.print('.');
			}
			System.out.println();
			states.close();

			final Deque<State> trace = new LinkedList<State>();
			final Deque<String> trans = new LinkedList<String>();

			while (state != 0) {
				final CompressedState compressedState = stateArray.get(state);
				System.out.println("===== " + state + " =========================");
				final State s = compressedState.decompress(allRealPlaceInstances);
				trace.addFirst(s);
				System.out.println(s);
				final String t = bindings.get(state);
				trans.addFirst(t);
				System.out.println(" - " + t);
				state = compressedState.getPredecessor();
			}
			System.out.println("===== " + state + " =========================");
			System.out.println(stateArray.get(0).decompress(simulator.getAllRealPlaceInstances()));

			final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			for (final String t : trans) {
				System.out.print(t);
				in.readLine();
				simulator.setMarking(trace.removeFirst());
				simulator.refreshViews();
			}
		}
	}

}
