/************************************************************************/
/* Access/CPN */
/* Copyright 2010-2011 AIS Group, Eindhoven University of Technology */
/*                                                                      */
/* This library is free software; you can redistribute it and/or */
/* modify it under the terms of the GNU Lesser General Public */
/* License as published by the Free Software Foundation; either */
/* version 2.1 of the License, or (at your option) any later version. */
/*                                                                      */
/* This library is distributed in the hope that it will be useful, */
/* but WITHOUT ANY WARRANTY; without even the implied warranty of */
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU */
/* Lesser General Public License for more details. */
/*                                                                      */
/* You should have received a copy of the GNU Lesser General Public */
/* License along with this library; if not, write to the Free Software */
/* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, */
/* MA 02110-1301 USA */
/************************************************************************/
package org.cpntools.accesscpn.model.demos.statespacegenerator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.cpntools.accesscpn.engine.highlevel.HighLevelSimulator;
import org.cpntools.accesscpn.engine.highlevel.MultiSetUtils;
import org.cpntools.accesscpn.engine.highlevel.instance.Binding;
import org.cpntools.accesscpn.engine.highlevel.instance.Instance;
import org.cpntools.accesscpn.engine.highlevel.instance.State;
import org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNValue;
import org.cpntools.accesscpn.engine.proxy.ProxyDaemon;
import org.cpntools.accesscpn.engine.proxy.ProxySimulator;
import org.cpntools.accesscpn.model.Place;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.Transition;

public class StateSpaceGenerator {
	private static final class ComputeThread extends Thread {
		private final BlockingDeque<CompressedState> waiting;
		private final CompressedStateSet storage;
		private final HighLevelSimulator simulator;
		private final Date start;
		private final List<Instance<Place>> allRealPlaceInstances;
		private final List<org.cpntools.accesscpn.engine.highlevel.instance.State> dead;

		public ComputeThread(final BlockingDeque<CompressedState> waiting, final CompressedStateSet storage,
		        final List<Instance<Place>> allRealPlaceInstances, final HighLevelSimulator simulator,
		        final List<org.cpntools.accesscpn.engine.highlevel.instance.State> dead, final Date start) {
			this.waiting = waiting;
			this.storage = storage;
			this.allRealPlaceInstances = allRealPlaceInstances;
			this.simulator = simulator;
			this.dead = dead;
			this.start = start;
		}

		@Override
		public void run() {
			try {
				StateSpaceGenerator.generateStateSpace(simulator, storage, allRealPlaceInstances, waiting, dead, start);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static int THREADS = 2;
	private static Map<String, Long> profile = new HashMap<String, Long>();
	private static ProxySimulator ps;

	private static AtomicInteger threadCount;

	private static int arcs = 0;

	static final BlockingQueue<CompressedState> toSerialize = new LinkedBlockingQueue<CompressedState>();
	static final BlockingQueue<String> toSerializeBindings = new LinkedBlockingQueue<String>();
	static final AtomicBoolean done = new AtomicBoolean(false);
	private static final boolean CHECKPOINTING = false;

	static private AtomicBoolean checkpointing = new AtomicBoolean(false);

	// private static List<State> calculateDeadStates(HighLevelSimulator simulator,
	// final Set<CompressedState> storage, final List<Instance<Place>> allRealPlaceInstances)
	// throws Exception {
	// final List<State> dead = new ArrayList<State>();
	// System.out.print("Finding dead states\n0 (0%):\t");
	// final Date start = new Date();
	// int i = 0;
	// State last = null;
	// for (final CompressedState compressed : storage) {
	// final State s = compressed.decompress(allRealPlaceInstances);
	// boolean failed;
	// do {
	// failed = false;
	//
	// try {
	// simulator.setMarkingFast(s, last, true);
	// last = s;
	// if (simulator.isEnabled(simulator.getAllTransitionInstances()).isEmpty())
	// dead.add(s);
	// } catch (final Exception e) {
	// failed = true;
	// last = null;
	// simulator = StateSpaceGenerator.ps.getSimulatorClone();
	// }
	// } while (failed);
	// StateSpaceGenerator.handleEOL(start, ++i, dead.size(), storage.size());
	// }
	// StateSpaceGenerator.handleEOL(start, 0);
	// System.out.println("Found " + dead.size() + " dead state" + (dead.size() == 1 ? "" : "s"));
	// return dead;
	// }

	static private int last = -1;

	static int stateCount = 1;

	@SuppressWarnings({ "unchecked", "unused" })
	public static void main(final String[] args) {
		if (args.length > 0) {
			StateSpaceGenerator.THREADS = Integer.parseInt(args[0]);
		}
		boolean efficient = false;
		if (args.length > 1) {
			if ("-e".equals(args[1])) {
				System.out.println("Using efficient storage");
				efficient = true;
			}
		}
		StateSpaceGenerator.threadCount = new AtomicInteger(StateSpaceGenerator.THREADS);
		System.out.println("Using " + StateSpaceGenerator.THREADS + " thread"
		        + (StateSpaceGenerator.THREADS == 1 ? "" : "s"));
		try {
			final HighLevelSimulator simulator = StateSpaceGenerator.getSimulator();
			final List<Instance<Place>> allRealPlaceInstances = simulator.getAllRealPlaceInstances();
			final CompressedStateSet storage;
			if (efficient) {
				storage = new EfficientStateSet(allRealPlaceInstances.size());
			} else {
				storage = new TroveSetCompressedStateSet();
			}
// final CompressedStateSet storage = new BDDCompressedStateSet(allRealPlaceInstances);
// final CompressedStateSet storage = new CollectionsCompressedStateSet();
			final BlockingDeque<CompressedState> waiting = new LinkedBlockingDeque<CompressedState>();
			final State init = simulator.getMarking(false);
			try {
				if (!StateSpaceGenerator.CHECKPOINTING) { throw new Exception("Skip check point"); }
				System.out.println("Trying to reestablish from checkpoint");
				new File("checkpoint.dmp").renameTo(new File("checkpoint.old"));
				final ObjectInputStream checkpoint = new ObjectInputStream(new BufferedInputStream(new FileInputStream(
				        new File("checkpoint.old"))));
				final ArrayList<String> values = (ArrayList<String>) checkpoint.readObject();
				if (values != null) {
					CompressedState.setValues(values);
					for (int i = checkpoint.readInt(); i > 0; i--) {
						waiting.add((CompressedState) checkpoint.readObject());
					}
					new File("states.dmp").renameTo(new File("states.old"));
					final DataInputStream states = new DataInputStream(new BufferedInputStream(new FileInputStream(
					        new File("states.old"))));
					final List<CompressedState> unchecked = new LinkedList<CompressedState>();
					try {
						while (true) {
							final CompressedState c = new CompressedState();
							c.readObject(states);
							if (c.isCheckpoint()) {
								storage.addAll(unchecked);
								unchecked.clear();
							} else {
								final String binding = states.readUTF();
								StateSpaceGenerator.serialize(null, binding, allRealPlaceInstances, c);
								unchecked.add(c);
							}
						}
					} catch (final EOFException e) {
						// We're done
					}
					states.close();
					StateSpaceGenerator.serialize(null, null, null, new CompressedState());
				}
				checkpoint.close();
			} catch (final Throwable t) {
				System.out.println("Starting from scratch");
				storage.clear();
				waiting.clear();
				final CompressedState c = new CompressedState(allRealPlaceInstances, init);
				storage.add(c);
				waiting.add(c);
				StateSpaceGenerator.serialize(init, "<init>", allRealPlaceInstances, c);
			}

			int threads = 1;
			final List<Thread> threadPool = new ArrayList<Thread>();
			final List<State> dead = Collections.synchronizedList(new LinkedList<State>());
			final Date start = new Date();
			threadPool.add(new ComputeThread(waiting, storage, allRealPlaceInstances, simulator, dead, start));
			while (threads < StateSpaceGenerator.THREADS) {
				System.out.println("Started thread " + threads + " of " + StateSpaceGenerator.THREADS);
				threadPool.add(new ComputeThread(waiting, storage, allRealPlaceInstances, StateSpaceGenerator.ps
				        .getSimulatorClone(), dead, start));
				threads++;
			}
			System.out.println("Started thread " + threads + " of " + StateSpaceGenerator.THREADS);

			final DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(
			        new File("states.dmp"))));
			// final EDataGraph dataGraph = SDOFactory.eINSTANCE.createEDataGraph();
			// dataGraph.setERootObject((EObject) simulator.getTarget());
			// outputStream.writeObject(simulator.getTarget());
			// outputStream.flush();
			new Thread() {
				@Override
				public void run() {
					try {
						while (true) {
							CompressedState s;
							try {
								s = StateSpaceGenerator.toSerialize.poll(1, TimeUnit.SECONDS);
								if (s == null) {
									if (StateSpaceGenerator.done.get()) {
										break;
									}
								} else {
									s.writeObject(outputStream);
									if (s.isCheckpoint()) {
										outputStream.flush();
									} else {
										saveBindings(outputStream, StateSpaceGenerator.toSerializeBindings.take());
									}
								}
							} catch (final InterruptedException e) {
								// Ignore
							}
						}
						outputStream.flush();
						outputStream.close();
					} catch (final IOException e2) {
						e2.printStackTrace();
					}
				}

				private void saveBindings(final DataOutputStream o, final String bindings) throws IOException {
					{
						o.writeUTF(bindings);
					}
				}
			}.start();
			System.out.print("Generating\n0:  0  0  0  .");
			start.setTime(new Date().getTime());
			for (final Thread t : threadPool) {
				t.start();
			}
			for (final Thread t : threadPool) {
				t.join();
			}
			StateSpaceGenerator.handleEOL(start, 0);
			StateSpaceGenerator.serialize(null, null, null, new CompressedState());
			final ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(
			        new File("checkpoint.dmp"))));
			oos.writeObject(CompressedState.getValues());
			oos.writeInt(0);
			oos.close();
			System.out.println("Generated " + storage.size() + " states, " + StateSpaceGenerator.arcs + " arcs");
			StateSpaceGenerator.done.set(true);
			storage.printStats();

			// final List<State> dead = StateSpaceGenerator.calculateDeadStates(simulator, storage,
			// allRealPlaceInstances);
			System.out.println("Found " + dead.size() + " dead state" + (dead.size() == 1 ? "" : "s"));
			for (final State s : dead) {
				System.out.println(s);
			}

			final Map<Instance<? extends PlaceNode>, Integer> max = new HashMap<Instance<? extends PlaceNode>, Integer>(), min = new HashMap<Instance<? extends PlaceNode>, Integer>();
			final Map<Instance<? extends PlaceNode>, List<CPNValue>> msmax = new HashMap<Instance<? extends PlaceNode>, List<CPNValue>>(), msmin = new HashMap<Instance<? extends PlaceNode>, List<CPNValue>>();
			StateSpaceGenerator.computeBounds(simulator, storage, allRealPlaceInstances, max, min, msmax, msmin);
			for (final Entry<Instance<? extends PlaceNode>, Integer> entry : max.entrySet()) {
				System.out.println(entry.getKey() + ":\t" + min.get(entry.getKey()) + " - " + entry.getValue());
				System.out.println("\t" + MultiSetUtils.toString(msmin.get(entry.getKey()), false));
				System.out.println("\t" + MultiSetUtils.toString(msmax.get(entry.getKey()), false));
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}

		System.out.println(StateSpaceGenerator.profile);

	}

	private static synchronized boolean add(final CompressedStateSet storage, final CompressedState c,
	        final CompressedState compressed, final Binding b, final List<Instance<Place>> allRealPlaceInstances) {
		if (storage.add(compressed)) {
			compressed.setStateNumber(StateSpaceGenerator.stateCount++);
			compressed.setPredecessor(c.getStateNumber());
			StateSpaceGenerator.serialize(null, "" + b, allRealPlaceInstances, compressed);
			return true;
		}
		return false;
	}

	private static void computeBounds(final HighLevelSimulator simulator, final CompressedStateSet storage,
	        final List<Instance<Place>> allRealPlaceInstances, final Map<Instance<? extends PlaceNode>, Integer> max,
	        final Map<Instance<? extends PlaceNode>, Integer> min,
	        final Map<Instance<? extends PlaceNode>, List<CPNValue>> msmax,
	        final Map<Instance<? extends PlaceNode>, List<CPNValue>> msmin) throws Exception {
		int i;
		HighLevelSimulator ss = simulator;
		System.out.print("Calculating bounds\n0:  ");
		final Date start = new Date();
		i = 0;
		final Map<String, List<CPNValue>> mscache = new HashMap<String, List<CPNValue>>();
		for (final CompressedState compressed : storage) {
			final State s = compressed.decompress(allRealPlaceInstances);
			Map<Instance<? extends PlaceNode>, List<CPNValue>> structuredMarking = null;
			while (structuredMarking == null) {
				try {
					structuredMarking = ss.getStructuredMarking(s, mscache);
				} catch (final Exception e) {
					ss = StateSpaceGenerator.ps.getSimulatorClone();
				}
			}
			for (final Entry<Instance<? extends PlaceNode>, List<CPNValue>> entry : structuredMarking.entrySet()) {
				max.put(entry.getKey(), Math.max(entry.getValue().size(),
				        max.get(entry.getKey()) == null ? 0 : max.get(entry.getKey())));
				min.put(entry.getKey(),
				        Math.min(entry.getValue().size(),
				                min.get(entry.getKey()) == null ? Integer.MAX_VALUE : min.get(entry.getKey())));
				msmin.put(entry.getKey(), MultiSetUtils.min(entry.getValue(), msmin.get(entry.getKey())));
				msmax.put(entry.getKey(), MultiSetUtils.max(entry.getValue(), msmax.get(entry.getKey())));
			}
			StateSpaceGenerator.handleEOL(start, ++i, storage.size());
		}
		StateSpaceGenerator.handleEOL(start, 0);
	}

	@SuppressWarnings("unused")
	static void generateStateSpace(HighLevelSimulator simulator, final CompressedStateSet storage,
	        final List<Instance<Place>> allRealPlaceInstances, final BlockingDeque<CompressedState> waiting,
	        final List<State> dead, final Date start) throws Exception, IOException {

		final List<Instance<Transition>> allTransitionInstances = simulator.getAllTransitionInstances();
		State previous = null, s = null;
		outer: while (true) {
			try {
				CompressedState c = null;
				StateSpaceGenerator.threadCount.decrementAndGet();
				while (c == null) {
					synchronized (StateSpaceGenerator.threadCount) {
						c = waiting.pollLast(1, TimeUnit.SECONDS);
						if (c == null) {
							System.out.print("P" + StateSpaceGenerator.threadCount.get());
							if (StateSpaceGenerator.threadCount.get() == 0) {
								break outer;
							}
						} else {
							StateSpaceGenerator.threadCount.incrementAndGet();
						}
					}
					Thread.yield();
				}
				s = c.decompress(allRealPlaceInstances);
				StateSpaceGenerator.profileStart();
				simulator.setMarkingFast(s, previous, true);
				StateSpaceGenerator.profileEnd("setMarking");
				previous = s;
				final List<Instance<? extends Transition>> enabled = simulator.isEnabled(allTransitionInstances);
				final List<Binding> bindings = new ArrayList<Binding>();
				for (final Instance<? extends Transition> ti : enabled) {
					StateSpaceGenerator.profileStart();
					bindings.addAll(simulator.getBindings(ti));
					StateSpaceGenerator.profileEnd("getBindings");
				}
				if (bindings.isEmpty()) {
					dead.add(s);
					System.out.println("\nDead state (" + c.getStateNumber() + "):\n" + s);
				}
				StateSpaceGenerator.arcs += bindings.size();

				for (final Binding b : bindings) {
					StateSpaceGenerator.profileStart();
					if (!simulator.execute(b)) { throw new Exception("Binding not found: " + b); }
					StateSpaceGenerator.profileEnd("execute");
					StateSpaceGenerator.profileStart();
					final State ss = simulator.getMarking(false);
					StateSpaceGenerator.profileEnd("getMarking");
					final CompressedState compressed = new CompressedState(allRealPlaceInstances, ss);
					if (StateSpaceGenerator.add(storage, c, compressed, b, allRealPlaceInstances)) {
						waiting.add(compressed);
						final int size = storage.size();
						StateSpaceGenerator.handleEOL(start, size);
						if (size % 50 == 0) {
							System.out.print(StateSpaceGenerator.arcs + "  " + waiting.size() + "  "
							        + CompressedState.getValues().size() + "  ");
							// if (size % 200 == 0)
							// storage.printStats();
						}
					} else {
						// System.out.print("!");
					}
					StateSpaceGenerator.profileStart();
					simulator.setMarkingFast(s, ss, false);
					StateSpaceGenerator.profileEnd("setMarking");
				}

				if (storage.size() - StateSpaceGenerator.last > 10000 && StateSpaceGenerator.CHECKPOINTING) {
					if (!StateSpaceGenerator.checkpointing.getAndSet(true)) {
						System.out.print('W');
						final LinkedList<CompressedState> states = new LinkedList<CompressedState>();
						inner: while (true) {
							synchronized (StateSpaceGenerator.threadCount) {
								c = waiting.poll(50, TimeUnit.MILLISECONDS);
								if (c == null) {
									System.out.print(StateSpaceGenerator.threadCount.get());
									if (StateSpaceGenerator.threadCount.get() == 1) {
										StateSpaceGenerator.serialize(null, null, null, new CompressedState());
										break inner;
									}
								} else {
									states.add(c);
								}
							}
						}
						System.out.print('C');
						final ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(
						        new FileOutputStream(new File("checkpoint.dmp"))));
						outputStream.writeObject(CompressedState.getValues());
						outputStream.writeInt(states.size());
						for (final CompressedState cs : states) {
							outputStream.writeObject(cs);
						}
						outputStream.close();
						StateSpaceGenerator.last = storage.size();
						for (final CompressedState cs : states) {
							waiting.add(cs);
						}
						System.out.print('D');
						StateSpaceGenerator.checkpointing.set(false);
					}
				}
			} catch (final Exception e) {
				e.printStackTrace();
				simulator.destroy();
				if (s != null) {
					waiting.add(new CompressedState(allRealPlaceInstances, s));
				}
				previous = null;
				simulator = StateSpaceGenerator.ps.getSimulatorClone();
			}
		}
	}

	private static HighLevelSimulator getSimulator() throws Exception, InterruptedException {
		final ProxyDaemon pd = ProxyDaemon.getDefaultInstance();
		System.out.println("Waiting for CPN Tools");
		StateSpaceGenerator.ps = pd.getNext();

		System.out.println("Waiting for syntax check");
		while (StateSpaceGenerator.ps.getPetriNet() == null) {
			Thread.sleep(500);
		}

		final HighLevelSimulator simulator = StateSpaceGenerator.ps.getSimulator();
		return simulator;
	}

	private static void handleEOL(final Date start, final int i) {
		StateSpaceGenerator.handleEOL(start, i, i, 0);
	}

	private static void handleEOL(final Date start, final int i, final int j) {
		StateSpaceGenerator.handleEOL(start, i, i, j);
	}

	private static void handleEOL(final Date start, final int i, final int j, final int k) {
		if (i % 10 == 0) {
			System.out.print('o');
		} else {
			System.out.print('.');
		}
		if (i % 50 == 0) {
			final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			final Date now = new Date();
			final long spent = now.getTime() - start.getTime();
			System.out.print(" - " + formatter.format(now) + " (" + spent / 86400000L + "d, "
			        + formatter.format(new Date(spent + 82800000L)) + ")\n");
			if (i > 0) {
				System.out.print(j + (k > 0 ? " (" + 100 * i / k + "%)" : "") + ":  ");
			}
		}
	}

	private synchronized static void serialize(final State s, final String binding,
	        final List<Instance<Place>> allRealPlaceInstances, final CompressedState c) {
		StateSpaceGenerator.toSerialize.add(c);
		if (!c.isCheckpoint()) {
			StateSpaceGenerator.toSerializeBindings.add(binding);
		}
	}

	static void profileEnd(final String name) {
		// final Date end = new Date();
		// Long value = StateSpaceGenerator.profile.get(name);
		// if (value == null) value = 0L;
		// value += end.getTime() - StateSpaceGenerator.start.getTime();
		// StateSpaceGenerator.profile.put(name, value);
	}

	static void profileStart() {
		// StateSpaceGenerator.start = new Date();
	}

}
