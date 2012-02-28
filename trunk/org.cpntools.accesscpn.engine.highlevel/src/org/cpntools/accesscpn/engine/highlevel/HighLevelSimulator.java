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
package org.cpntools.accesscpn.engine.highlevel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cpntools.accesscpn.engine.Handler;
import org.cpntools.accesscpn.engine.Packet;
import org.cpntools.accesscpn.engine.Simulator;
import org.cpntools.accesscpn.engine.SimulatorService;
import org.cpntools.accesscpn.engine.highlevel.instance.Binding;
import org.cpntools.accesscpn.engine.highlevel.instance.Instance;
import org.cpntools.accesscpn.engine.highlevel.instance.InstanceFactory;
import org.cpntools.accesscpn.engine.highlevel.instance.Marking;
import org.cpntools.accesscpn.engine.highlevel.instance.State;
import org.cpntools.accesscpn.engine.highlevel.instance.ValueAssignment;
import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelData;
import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelDataAdapterFactory;
import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelInstance;
import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelInstanceAdapterFactory;
import org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNValue;
import org.cpntools.accesscpn.engine.highlevel.utils.SingleLineLogFormatter;
import org.cpntools.accesscpn.engine.highlevel.utils.StreamUtillities;
import org.cpntools.accesscpn.model.Arc;
import org.cpntools.accesscpn.model.HLArcType;
import org.cpntools.accesscpn.model.HLDeclaration;
import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.Place;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.Transition;
import org.cpntools.accesscpn.model.cpntypes.CPNAlias;
import org.cpntools.accesscpn.model.cpntypes.CPNBool;
import org.cpntools.accesscpn.model.cpntypes.CPNEnum;
import org.cpntools.accesscpn.model.cpntypes.CPNIndex;
import org.cpntools.accesscpn.model.cpntypes.CPNInt;
import org.cpntools.accesscpn.model.cpntypes.CPNList;
import org.cpntools.accesscpn.model.cpntypes.CPNProduct;
import org.cpntools.accesscpn.model.cpntypes.CPNRecord;
import org.cpntools.accesscpn.model.cpntypes.CPNString;
import org.cpntools.accesscpn.model.cpntypes.CPNSubset;
import org.cpntools.accesscpn.model.cpntypes.CPNType;
import org.cpntools.accesscpn.model.cpntypes.CPNUnion;
import org.cpntools.accesscpn.model.cpntypes.CPNUnit;
import org.cpntools.accesscpn.model.cpntypes.NameTypePair;
import org.cpntools.accesscpn.model.monitors.Monitor;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * @author mw
 */
public class HighLevelSimulator extends AdapterImpl {
	/**
	 * @author mw
	 */
	public static class GramHandler implements Handler {
		private static List<Object> handlers = Collections.synchronizedList(new ArrayList<Object>());
		private static final Logger logger = Logger.getLogger("GramHandler");

		static {
			if (DEBUG_SIMULATOR) {
				synchronized (logger) {
					logger.setLevel(Level.ALL);
					final java.util.logging.Handler handler = new ConsoleHandler();
					handler.setLevel(Level.ALL);
					handler.setFormatter(new SingleLineLogFormatter());
					logger.setUseParentHandlers(false);
					logger.addHandler(handler);
				}
			}
		}

		/**
		 * @param handler
		 *            new handler to add
		 */
		public static void addGlobalHandler(final Object handler) {
			GramHandler.handlers.add(handler);
		}

		private final LinkedList<Object> localHandlers;

		GramHandler() {
			localHandlers = new LinkedList<Object>();
		}

		/**
		 * @param newHandlers
		 *            handler local to this simulator instance
		 */
		public synchronized void addLocalHandler(final Object... newHandlers) {
			for (final Object handler : newHandlers) {
				localHandlers.add(handler);
			}
		}

		/**
		 * @param newHandlers
		 *            handler temporary to a single invocation
		 */
		public synchronized void addTemporaryHandler(final Object... newHandlers) {
			for (int i = newHandlers.length; i > 0; i--) {
				final Object handler = newHandlers[i - 1];
				localHandlers.addFirst(handler);
			}
		}

		/**
		 * @see org.cpntools.accesscpn.engine.Handler#handle(java.util.List)
		 */
		@Override
		public Object handle(final List<Object> parameters) {
			final LinkedList<Object> structured = new LinkedList<Object>();
			parse(structured, parameters, 0, parameters.size());
			if (structured.isEmpty()) { return null; }
			if (GramHandler.logger.isLoggable(Level.FINEST)) {
				GramHandler.logger.log(Level.FINEST, "Call: " + structured);
			}
			final Object o = structured.removeFirst();
			if (!(o instanceof String)) { return null; }
			final String name = (String) o;
			final Class<?>[] parameterTypes = new Class<?>[structured.size()];
			int i = 0;
			for (final Object p : structured) {
				parameterTypes[i++] = p.getClass();
			}
			if (GramHandler.logger.isLoggable(Level.FINE)) {
				GramHandler.logger.log(Level.FINE,
				        "Call to `" + name + "' " + structured + " : " + Arrays.toString(parameterTypes));
			}
			Method m = null;
			Object p = null;
			for (final Object handler : localHandlers) {
				try {
					m = handler.getClass().getMethod(name, parameterTypes);
					p = handler;
					break;
				} catch (final Exception e) {
					m = null;
				}
			}
			if (m == null) {
				for (final Object handler : GramHandler.handlers) {
					try {
						m = handler.getClass().getMethod(name, parameterTypes);
						p = handler;
						break;
					} catch (final Exception e) {
						m = null;
					}
				}
			}
			if (GramHandler.logger.isLoggable(Level.FINE)) {
				GramHandler.logger.log(Level.FINE, "Found `" + m + "' at " + p);
			}
			if (p != null && m != null) {
				try {
					return m.invoke(p, structured.toArray());
				} catch (final InvocationTargetException e) {
					GramHandler.logger.log(Level.WARNING, "Execution of `" + name + "' failed", e.getTargetException());
				} catch (final IllegalArgumentException e) {
					GramHandler.logger.log(Level.SEVERE, "Execution of `" + name + "' failed", e);
				} catch (final IllegalAccessException e) {
					GramHandler.logger.log(Level.SEVERE, "Execution of `" + name + "' failed", e);
				}
			}
			return null;
		}

		/**
		 * @param handler
		 *            handler to remove
		 */
		public void removeLocalHandler(final Object handler) {
			localHandlers.remove(handler);
		}

		/**
		 * @param handlersToRemove
		 *            handlers to remove after incocation
		 */
		public synchronized void removeTemporaryHandler(final Object... handlersToRemove) {
			for (final Object handler : handlersToRemove) {
				if (localHandlers.getFirst() == handler) {
					localHandlers.removeFirst();
				}
			}
		}

		private int parse(final List<Object> result, final List<Object> source, final int givenPos, final int max) {
			int pos = givenPos;
			while (pos < source.size() && result.size() < max) {
				Object o = source.get(pos++);
				if (o instanceof byte[]) {
					final byte[] arr = (byte[]) o;
					if (arr.length == 0) {
						o = source.get(pos++);
						if (o instanceof Integer) {
							final List<Object> tmp = new ArrayList<Object>();
							pos = parse(tmp, source, pos, (Integer) o);
							result.add(tmp);
						} else {
							result.add(arr);
							result.add(o);
						}
					} else {
						result.add(o);
					}
				} else {
					result.add(o);
				}
			}
			return pos;
		}
	}

	static class Bindings {
		private final List<List<String>> variables;
		private final List<List<List<String>>> values;
		private final Instance<? extends Transition> ti;
		private static final Random r = new Random();

		Bindings(final Instance<? extends Transition> ti, final List<List<String>> variables,
		        final List<List<List<String>>> values) {
			this.ti = ti;
			this.variables = variables;
			this.values = values;
			assert variables.size() == values.size();
			for (int i = 0; i < variables.size(); i++) {
				for (int j = 0; j < values.get(i).size(); j++) {
					assert variables.get(i).size() == values.get(i).get(j).size();
				}
			}
		}

		public List<Binding> getAllBindings() {
			final List<String> vars = new ArrayList<String>();
			List<List<String>> vals = new ArrayList<List<String>>();
			vals.add(new ArrayList<String>());
			for (int i = 0; i < variables.size(); i++) {
				vars.addAll(variables.get(i));
				if (values.get(i).size() == 1) {
					for (final List<String> val : vals) {
						val.addAll(values.get(i).get(0));
					}
				} else {
					final List<List<String>> oldVals = vals;
					vals = new ArrayList<List<String>>();
					for (final List<String> value : values.get(i)) {
						for (final List<String> val : oldVals) {
							final List<String> newVal = new ArrayList<String>(val);
							newVal.addAll(value);
							vals.add(newVal);
						}
					}
				}
			}
			final List<Binding> result = new ArrayList<Binding>();
			for (final List<String> val : vals) {
				assert val.size() == vars.size();
				final List<ValueAssignment> valueAssignments = new ArrayList<ValueAssignment>();
				for (int i = 0; i < val.size(); i++) {
					valueAssignments.add(InstanceFactory.INSTANCE.createValueAssignment(vars.get(i), val.get(i)));
				}
				result.add(InstanceFactory.INSTANCE.createBinding(ti, valueAssignments));
			}
			return result;
		}

		public Binding getAnyBinding() {
			final List<ValueAssignment> valueAssignments = new ArrayList<ValueAssignment>();
			for (int i = 0; i < variables.size(); i++) {
				final List<String> value = values.get(i).get(r.nextInt(values.get(i).size()));
				for (int j = 0; j < value.size(); j++) {
					valueAssignments.add(InstanceFactory.INSTANCE.createValueAssignment(variables.get(i).get(j),
					        value.get(j)));
				}
			}
			return InstanceFactory.INSTANCE.createBinding(ti, valueAssignments);
		}

		public List<List<List<String>>> getValues() {
			return values;
		}

		public List<List<String>> getVariables() {
			return variables;
		}
	}

	/**
	 * 
	 */
	public static boolean DEBUG_SIMULATOR = false;

	private static Map<Simulator, HighLevelSimulator> existing = new HashMap<Simulator, HighLevelSimulator>();

	/**
	 * @return a fresh HLS associated with a fresh simulator
	 * @throws Exception
	 *             if we could not instantiate the new simulator
	 */
	public static HighLevelSimulator getHighLevelSimulator() throws Exception {
		return new HighLevelSimulator(SimulatorService.getInstance().getNewSimulator());
	}

	/**
	 * @param simulator
	 *            the simulator we want a HLS for
	 * @return a canonical HLS for the given simulator
	 */
	public static HighLevelSimulator getHighLevelSimulator(final Simulator simulator) {
		if (HighLevelSimulator.existing.containsKey(simulator)) { return HighLevelSimulator.existing.get(simulator); }
		return new HighLevelSimulator(simulator);
	}

	State state = null;

	List<Binding> bindings = null;

	private final GramHandler gramHandler;

	private final Simulator simulator;

	private boolean pausebefore;

	private boolean pauseafter;

	private boolean pauseshow;

	private boolean reporttrans;

	private boolean reportbinds;

	private boolean showmarking;

	private boolean showenabling;

	private String untilstep;

	private String addstep;

	private String untiltime;

	private String addtime;

	private String pausecont;

	private String reportfunc;

	private PetriNet petriNet;

	ModelData modelData;

	private ModelInstance modelInstance;

	private boolean fairBE;

	private boolean globalFairness;

	private HighLevelSimulator(final Simulator simulator) {
		this.simulator = simulator;
		pausebefore = false;
		pauseafter = false;
		pauseshow = false;
		reporttrans = false;
		reportbinds = false;
		showmarking = true;
		showenabling = true;
		fairBE = false;
		globalFairness = false;
		untilstep = "";
		addstep = "";
		untiltime = "";
		addtime = "";
		pausecont = "";
		reportfunc = "";

		gramHandler = new GramHandler();
		simulator.setHandler(2, gramHandler);
		HighLevelSimulator.existing.put(simulator, this);
	}

	/**
	 * @param handlers
	 *            new local handlers to add
	 */
	public void addHandler(final Object... handlers) {
		gramHandler.addLocalHandler(handlers);
	}

	/**
	 * @param decl
	 *            declaration to check
	 * @throws IOException
	 *             if an IO error occured
	 * @throws DeclarationCheckerException
	 *             the declaration is not correct
	 */
	public void checkDeclaration(final HLDeclaration decl) throws IOException, DeclarationCheckerException {
		getSimulator().lock();
		Packet p = null;
		try {
			final Packet q = PacketGenerator.instance.constructCheckDeclaration(decl);
			p = send(q);
		} finally {
			getSimulator().release();
		}
		final String id = p.getString();
		final String errorMessage = p.getString();
		if (!"".equals(errorMessage)) { throw new DeclarationCheckerException(id, errorMessage); }
	}

	/**
	 * @param p
	 *            place
	 * @param marking
	 *            marking
	 * @return whether marking is legal for place
	 * @throws IOException
	 *             on error
	 */
	public boolean checkMarking(final PlaceNode p, final String marking) throws IOException {
		return send(PacketGenerator.instance.constructCheckMarking(p.getSort().getText(), marking)).getBoolean();
	}

	/**
	 * @param page
	 *            page to check
	 * @param prime
	 *            whether the page is prime
	 * @throws IOException
	 *             if an IO error occurred
	 * @throws CheckerException
	 *             if the page is not syntactically correct
	 */
	public void checkPage(final Page page, final boolean prime) throws IOException, CheckerException {
		getSimulator().lock();
		Packet p = null;
		try {
			final Packet q = PacketGenerator.instance.constructCheckPage(page, prime);
			p = send(q);
		} finally {
			getSimulator().release();
		}
		p.getInteger(); // TERMTAG=1
		final int numberOfErrors = p.getInteger();
		if (numberOfErrors > 0) {
			String errorMessage = "";
			for (int i = 0; i < numberOfErrors; i++) {
				errorMessage += p.getString() + ": " + p.getString() + "\n";
			}

			throw new SyntaxCheckerException(page.getId(), errorMessage);
		} else if (numberOfErrors == -1) { throw new SyntaxFatalErrorException("Fatal error occurred in syntax check"); }
	}

	/**
	 * @param monitor
	 * @throws SyntaxCheckerException
	 * @throws IOException
	 */
	public void checkMonitor(final Monitor monitor) throws SyntaxCheckerException, IOException {
		if (monitor.isDisabled()) { return; }
		switch (monitor.getKind()) {
		case BREAKPOINT:
		case USER_DEFINED:
		case DATA_COLLECTION:
		case WRITE_IN_FILE: {
			Packet p = PacketGenerator.instance.constructCheckMonitor(monitor);
			p = send(p);
			p.getInteger(); // TERMTAG = 1;
			p.getInteger();
			if (p.getInteger() != 0) {
				p.getString(); // monitor-id1
				p.getString(); // errid1
				throw new SyntaxCheckerException(monitor.getId(), "Could not generate monitor (" + p.getString() + ")");
			}
			return;
		}
		case MARKING_SIZE: {
			Packet p = PacketGenerator.instance.constructMarkingSize(monitor);
			p = send(p);
			return;
		}
		case LIST_LENGTH: {
			Packet p = PacketGenerator.instance.constructListLength(monitor);
			p = send(p);
			return;
		}
		case COUNT_TRANSITION: {
			Packet p = PacketGenerator.instance.constructCountTransition(monitor);
			p = send(p);
			return;
		}
		case PLACE_CONTENT: {
			Packet p = PacketGenerator.instance.constructPlaceEmpty(monitor);
			p = send(p);
			return;
		}
		case TRANSTION_ENABLED: {
			Packet p = PacketGenerator.instance.constructTransitionEnabled(monitor);
			p = send(p);
			return;
		}

		}
	}

	/**
	 * 
	 */
	public void destroy() {
		getSimulator().destroy();
	}

	/**
	 * @param arc
	 * @param binding
	 * @return
	 */
	public List<CPNValue> evaluate(final Arc arc, final Binding binding) {
		final StringBuilder expression = new StringBuilder();
		expression.append("((fn { ");
		boolean first = true;
		for (final ValueAssignment valueAssignment : binding.getAllAssignments()) {
			if (!first) {
				expression.append(", ");
			}
			first = false;
			expression.append(valueAssignment.getName());
		}
		expression.append(" } =>\n");
		expression.append(arc.getHlinscription().getText());
		expression.append(") { ");
		first = true;
		for (final ValueAssignment valueAssignment : binding.getAllAssignments()) {
			if (first) {
				first = false;
			} else {
				expression.append(", ");
			}
			expression.append(valueAssignment);
		}
		expression.append(" })");
		return evaluate(modelData.getType(arc.getPlaceNode()), arc.getPlaceNode().getSort().getText(),
		        expression.toString());
	}

	/**
	 * @param type
	 * @param expr
	 * @return
	 */
	public List<CPNValue> evaluate(final CPNType type, final String typeName, final String expr) {
		StringBuilder expression = new StringBuilder();
		expression.append("val CPN'result = JavaExecute.vLIST [CPN'SerializerJava.CPN'serialize'");
		expression.append(typeName);
		expression.append(" ");
		expression.append(expr);
		expression.append("]");
		final List<CPNValue> result = new ArrayList<CPNValue>();
		lock();
		try {
			try {
				try {
// System.out.println(expression.toString());
// System.out.println(evaluate(expression.toString()));
					evaluate(expression.toString());
				} catch (final Exception e) {
					expression = new StringBuilder();
					expression
					        .append("val CPN'result = CPN'SerializerJava.CPN'serializeplace CPN'SerializerJava.CPN'serialize'");
					expression.append(typeName);
					expression.append(" ");
					expression.append(expr);
// System.out.println(expression.toString());
// System.out.println(evaluate(expression.toString()));
					evaluate(expression.toString());
				}

				evaluate("execute \"sendMarking\" [CPN'result]", new Object() {

					@SuppressWarnings("unused")
					public void sendMarking(final ArrayList<?> elms) {
// System.out.println(elms);
						for (final Object elm : elms) {
							result.add(handle(type, false, elm));
						}
// System.out.println(result);
					}

					private boolean getBoolean(final Object unpacked) {
						assert unpacked instanceof Boolean;
						return (Boolean) unpacked;
					}

					private Integer getInteger(final Object unpacked) {
						assert unpacked instanceof Integer;
						return (Integer) unpacked;
					}

					private List<?> getList(final Object unpacked, final int length) {
						assert unpacked instanceof List<?>;
						final List<?> list = (List<?>) unpacked;
						if (length >= 0) {
							assert list.size() == length;
						}
						return list;
					}

					private String getString(final Object unpacked) {
						assert unpacked instanceof String;
						return (String) unpacked;
					}

					private void getUnit(final Object unpacked) {
						getBoolean(unpacked);
					}

					private CPNValue handle(@SuppressWarnings("hiding") final CPNType type, final boolean ignoreTime,
					        final Object elm) {
						if (type instanceof CPNUnit) {
							return handleUnit(type, ignoreTime, elm);
						} else if (type instanceof CPNBool) {
							return handleBool(type, ignoreTime, elm);
						} else if (type instanceof CPNInt) {
							return handleInt(type, ignoreTime, elm);
						} else if (type instanceof CPNString) {
							return handleString(type, ignoreTime, elm);
						} else if (type instanceof CPNEnum) {
							return handleEnum(type, ignoreTime, elm);
						} else if (type instanceof CPNIndex) {
							return handleIndex(type, ignoreTime, elm);
						} else if (type instanceof CPNList) {
							return handleList(type, ignoreTime, elm);
						} else if (type instanceof CPNProduct) {
							return handleProduct(type, ignoreTime, elm);
						} else if (type instanceof CPNRecord) {
							return handleRecord(type, ignoreTime, elm);
						} else if (type instanceof CPNUnion) {
							return handleUnion(type, ignoreTime, elm);
						} else if (type instanceof CPNSubset) {
							return handleSubset(type, ignoreTime, elm);
						} else if (type instanceof CPNAlias) { return handleAlias(type, ignoreTime, elm); }
						return null;
					}

					private CPNValue handleAlias(@SuppressWarnings("hiding") final CPNType type,
					        final boolean ignoreTime, final Object elm) {
						final CPNAlias aliasType = (CPNAlias) type;
						final CPNType baseType = modelData.getType(aliasType.getSort());
						return packTime(type, ignoreTime, elm,
						        handle(baseType, true, unpackTime(type, ignoreTime, elm)));
					}

					private CPNValue handleBool(@SuppressWarnings("hiding") final CPNType type,
					        final boolean ignoreTime, final Object elm) {
						return packTime(type, ignoreTime, elm,
						        new org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNBoolean(
						                getBoolean(unpackTime(type, ignoreTime, elm))));
					}

					private CPNValue handleEnum(@SuppressWarnings("hiding") final CPNType type,
					        final boolean ignoreTime, final Object elm) {
						final List<?> list = getList(unpackTime(type, ignoreTime, elm), 2);
						return packTime(
						        type,
						        ignoreTime,
						        elm,
						        new org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNEnum(getString(list
						                .get(0)), getInteger(list.get(1))));
					}

					private CPNValue handleIndex(@SuppressWarnings("hiding") final CPNType type,
					        final boolean ignoreTime, final Object elm) {
						final List<?> list = getList(unpackTime(type, ignoreTime, elm), 2);
						return packTime(type, ignoreTime, elm,
						        new org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNIndex(
						                ((CPNIndex) type).getName(), getInteger(list.get(1))));
					}

					private CPNValue handleInt(@SuppressWarnings("hiding") final CPNType type,
					        final boolean ignoreTime, final Object elm) {
						return packTime(type, ignoreTime, elm,
						        new org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNInteger(
						                getInteger(unpackTime(type, ignoreTime, elm))));
					}

					private CPNValue handleList(@SuppressWarnings("hiding") final CPNType type,
					        final boolean ignoreTime, final Object elm) {
						final CPNList listType = (CPNList) type;
						final CPNType baseType = modelData.getType(listType.getSort());
						final List<?> list = getList(unpackTime(type, ignoreTime, elm), -1);
						final List<CPNValue> listResult = new ArrayList<CPNValue>();
						for (final Object o : list) {
							listResult.add(handle(baseType, true, o));
						}
						return packTime(type, ignoreTime, elm,
						        new org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNList<CPNValue>(
						                listResult));
					}

					private CPNValue handleProduct(@SuppressWarnings("hiding") final CPNType type,
					        final boolean ignoreTime, final Object elm) {
						final CPNProduct listType = (CPNProduct) type;
						final List<?> list = getList(unpackTime(type, ignoreTime, elm), listType.getTypes().size());
						final List<CPNValue> listResult = new ArrayList<CPNValue>();
						int i = 0;
						for (final Object o : list) {
							final CPNType type2 = modelData.getType(listType.getTypes().get(i++));
							listResult.add(handle(type2, true, o));
						}
						return packTime(type, ignoreTime, elm,
						        new org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNProduct(listResult));
					}

					private CPNValue handleRecord(@SuppressWarnings("hiding") final CPNType type,
					        final boolean ignoreTime, final Object elm) {
						final CPNRecord recordType = (CPNRecord) type;
						final List<?> list = getList(elm, recordType.getValues().size());
						final Map<String, CPNValue> mapResult = new HashMap<String, CPNValue>();
						final Map<String, CPNType> types = new HashMap<String, CPNType>();
						for (final NameTypePair t : recordType.getValues()) {
							types.put(t.getName(), modelData.getType(t.getSort()));
						}
						for (final Object o : list) {
							final List<?> list2 = getList(o, 2);
							final String name = getString(list2.get(0));
							final CPNType type2 = types.get(name);
							mapResult.put(name, handle(type2, true, list2.get(1)));
						}
						return packTime(type, ignoreTime, elm,
						        new org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNRecord(mapResult));
					}

					private CPNValue handleString(@SuppressWarnings("hiding") final CPNType type,
					        final boolean ignoreTime, final Object elm) {
						return packTime(type, ignoreTime, elm,
						        new org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNString(
						                getString(unpackTime(type, ignoreTime, elm))));
					}

					private CPNValue handleSubset(@SuppressWarnings("hiding") final CPNType type,
					        final boolean ignoreTime, final Object elm) {
						final CPNSubset subsetType = (CPNSubset) type;
						final CPNType baseType = modelData.getType(subsetType.getSort());
						return packTime(type, ignoreTime, elm,
						        handle(baseType, true, unpackTime(type, ignoreTime, elm)));
					}

					private CPNValue handleUnion(@SuppressWarnings("hiding") final CPNType type,
					        final boolean ignoreTime, final Object elm) {
						final CPNUnion unionType = (CPNUnion) type;
						final List<?> list = getList(elm, 2);
						final Map<String, CPNType> types = new HashMap<String, CPNType>();
						for (final NameTypePair t : unionType.getValues()) {
							types.put(t.getName(), modelData.getType(t.getSort()));
						}
						final String name = getString(list.get(0));
						final CPNType type2 = types.get(name);
						@SuppressWarnings("hiding")
						CPNValue result = null;
						if (type2 != null) {
							result = handle(type2, true, list.get(1));
						}

						return packTime(type, ignoreTime, elm,
						        new org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNUnion(name, type,
						                result));

					}

					private CPNValue handleUnit(@SuppressWarnings("hiding") final CPNType type,
					        final boolean ignoreTime, final Object elm) {
						final Object unpacked = unpackTime(type, ignoreTime, elm);
						getUnit(unpacked);
						return packTime(type, ignoreTime, elm,
						        new org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNUnit());
					}

					private CPNValue packTime(@SuppressWarnings("hiding") final CPNType type, final boolean ignoreTime,
					        final Object elm, final CPNValue value) {
						if (!ignoreTime && type.getTimed()) {
							value.setTime(getString(getList(elm, 2).get(1)));
							return value;
						}
						return value;
					}

					private Object unpackTime(@SuppressWarnings("hiding") final CPNType type, final boolean ignoreTime,
					        final Object elm) {
						if (!ignoreTime && type.getTimed()) { return getList(elm, 2).get(0); }
						return elm;
					}
				});
				return result;
			} catch (final Exception e) {
				e.printStackTrace();
				assert false;
				return null;
			}
		} finally {
			release();
		}
	}

	/**
	 * @param expr
	 *            expression to evaluate
	 * @return the ML result
	 * @throws Exception
	 *             if ML raises an exception
	 */
	public String evaluate(final String expr) throws Exception {
		return getSimulator().evaluate(expr);
	}

	/**
	 * Evaluate with response handlers
	 * 
	 * @param expr
	 *            expression to evaluate
	 * @param wrap
	 *            whether to wrap expression in JavaExecute
	 * @param handlers
	 *            handlers to take care of responses
	 * @return the ML result
	 * @throws Exception
	 *             if ML raises an exception
	 */
	public String evaluate(final String expr, final boolean wrap, final Object... handlers) throws Exception {
		try {
			gramHandler.addTemporaryHandler(handlers);
			if (wrap) {
				return getSimulator().evaluate("let open JavaExecute in\n" + expr + "\nend");
			} else {
				return getSimulator().evaluate(expr);
			}
		} finally {
			gramHandler.removeTemporaryHandler(handlers);
		}
	}

	/**
	 * Evaluate expression, wrapping in JavaExecute
	 * 
	 * @param expr
	 *            expression to evaluate
	 * @param handlers
	 *            handlers to take care of responses
	 * @return the ML result
	 * @throws Exception
	 *             if ML raises an exception
	 */
	public String evaluate(final String expr, final Object... handlers) throws Exception {
		return evaluate(expr, true, handlers);
	}

	/**
	 * @return whether it was possible to execute any transition
	 * @throws Exception
	 */
	public Instance<? extends Transition> execute() throws Exception {
		final Instance<? extends Transition> ti = execute(getAllTransitionInstances());
		if (ti == null) {
			if (increaseTime() == null) { return execute(); }
		}
		return ti;
	}

	/**
	 * @param binding
	 * @return
	 * @throws IOException
	 */
	public boolean execute(final Binding binding) throws IOException {
		final Instance<Transition> ti = binding.getTransitionInstance();
		if (isEnabled(ti)) {
			@SuppressWarnings("hiding")
			final Bindings bindings = getBindings(ti,
			        send(PacketGenerator.instance.constructGetBindings(ti.getNode().getId(), ti.getInstanceNumber())));
			final Packet packet = PacketGenerator.instance.constructManualBinding(bindings, binding);
			if (packet != null) {
				send(packet);
				return true;
			} else {
				send(PacketGenerator.instance.constructCancelManualBinding());
			}
		}
		return false;
	}

	/**
	 * @param ti
	 *            the transition instance to execute
	 * @return if the transition was successfully executed
	 * @throws Exception
	 *             on error; if IOException communication failed, if plain Exception an exception was rasied on the ML
	 *             side
	 */
	public boolean execute(final Instance<? extends Transition> ti) throws Exception {
		lock();
		try {
			if (isEnabled(ti)) {
				final Packet p = send(PacketGenerator.instance.constructExecute(ti.getNode().getId(),
				        ti.getInstanceNumber()));
				p.getString();
				p.getString();
				final String whyStopped = p.getString();
				if (whyStopped.startsWith("Error")) { throw new Exception(whyStopped); }
				return true;
			} else {
				return false;
			}
		} finally {
			release();
		}
	}

	/**
	 * @param amount
	 *            number of additional steps to execute
	 * @return why simulation was stopped
	 * @throws Exception
	 *             on error; if IOException communication failed, if plain Exception an exception was rasied on the ML
	 *             side
	 */
	public String execute(final int amount) throws Exception {
		lock();
		try {
			setSimulationOptions(pausebefore, pauseafter, pauseshow, reporttrans, reportbinds, showmarking,
			        showenabling, untilstep, Integer.valueOf(amount).toString(), untiltime, addtime, pausecont,
			        reportfunc, fairBE, globalFairness);
			final Packet p = send(PacketGenerator.instance.constructExecuteSteps());
			p.getString();
			p.getString();
			final String whyStopped = p.getString();
			if (whyStopped.startsWith("Error")) { throw new Exception(whyStopped); }
			return whyStopped;
		} finally {
			release();
		}
	}

	/**
	 * @param tis
	 *            the list of transition instance to execute
	 * @return if any transition was successfully executed
	 * @throws Exception
	 *             on error; if IOException communication failed, if plain Exception an exception was rasied on the ML
	 *             side
	 */
	public Instance<? extends Transition> execute(final List<Instance<Transition>> tis) throws Exception {
		final List<Instance<? extends Transition>> enabled = isEnabled(tis);
		Collections.shuffle(enabled);
		lock();
		try {
			for (final Instance<? extends Transition> ti : enabled) {
				if (execute(ti)) { return ti; }
			}
		} finally {
			release();
		}
		return null;
	}

	/**
	 * @return the binding of the executed TI or null if none enabled
	 * @throws Exception
	 */
	public Binding executeAndGet() throws Exception {
		final Binding binding = executeAndGet(getAllTransitionInstances());
		if (binding == null) {
			if (increaseTime() == null) { return executeAndGet(); }
		}
		return binding;
	}

	/**
	 * @param ti
	 * @return the binding of executing ti or null if none available
	 * @throws IOException
	 */
	public Binding executeAndGet(final Instance<Transition> ti) throws IOException {
		@SuppressWarnings("hiding")
		final List<Binding> bindings = getBindings(ti);
		Collections.shuffle(bindings);
		lock();
		try {
			for (final Binding binding : bindings) {
				if (execute(binding)) { return binding; }
			}
		} finally {
			release();
		}
		return null;
	}

	/**
	 * @param tis
	 *            the list of transition instance to execute
	 * @return if any transition was successfully executed
	 * @throws Exception
	 *             on error; if IOException communication failed, if plain Exception an exception was rasied on the ML
	 *             side
	 */
	public Binding executeAndGet(final List<Instance<Transition>> tis) throws Exception {
		Collections.shuffle(tis);
		lock();
		try {
			for (final Instance<Transition> ti : tis) {
				if (isEnabled(ti)) {
					@SuppressWarnings("hiding")
					final Bindings bindings = getBindings(
					        ti,
					        send(PacketGenerator.instance.constructGetBindings(ti.getNode().getId(),
					                ti.getInstanceNumber())));
					final Binding binding = bindings.getAnyBinding();
					final Packet packet = PacketGenerator.instance.constructManualBinding(bindings, binding);
					assert packet != null;
					send(packet);
					return binding;
				}
			}
		} finally {
			release();
		}
		return null;
	}

	/**
	 * @param bindings
	 *            the list of bindings to execute
	 * @return if any binding was successfully executed
	 * @throws Exception
	 *             on error; if IOException communication failed, if plain Exception an exception was rasied on the ML
	 *             side
	 */
	public Binding executeAnyGetWhich(@SuppressWarnings("hiding") final List<Binding> bindings) throws Exception {
		Collections.shuffle(bindings);
		lock();
		try {
			for (final Binding binding : bindings) {
				if (execute(binding)) { return binding; }
			}
		} finally {
			release();
		}
		return null;
	}

	/**
	 * @param id
	 *            id of fusion group
	 * @throws IOException
	 *             if an IO error occurred
	 */
	public void generateInstanceForFusionGroup(final String id) throws IOException {
		getSimulator().lock();
		try {
			send(PacketGenerator.instance.constructGenerateInstanceForFusionGroup(id));
		} finally {
			getSimulator().release();
		}
	}

	/**
	 * @param id
	 *            id of the place
	 * @throws IOException
	 *             if an IO error occurred
	 */
	public void generateInstanceForPlace(final String id) throws IOException {
		getSimulator().lock();
		try {
			send(PacketGenerator.instance.constructGenerateInstanceForPlace(id));
		} finally {
			getSimulator().release();
		}
	}

	/**
	 * @param id
	 *            id of the transition
	 * @throws IOException
	 *             if an IO error occurred
	 */
	public void generateInstanceForTransition(final String id) throws IOException {
		getSimulator().lock();
		try {
			send(PacketGenerator.instance.constructGenerateInstanceForTransition(id));
		} finally {
			getSimulator().release();
		}
	}

	/**
	 * @return all place isntances of the PetriNet of this simulator
	 * @throws Exception
	 *             if no Petrinet is associated with this simulator
	 */
	public List<Instance<PlaceNode>> getAllPlaceInstances() throws Exception {
		if (modelData != null) {
			final List<Instance<PlaceNode>> allPlaceInstances = modelData.getAllPlaceNodeInstances();
			if (allPlaceInstances != null) { return allPlaceInstances; }
		}
		throw new Exception("You did not give a PetriNet on creation, so you cannot execute arbitrary bindings.");

	}

	/*
	 * public void generateInstances() throws IOException { // TODO: generete instancer for hver fusions gruppe, plads
	 * og trans send(PacketGenerator.instance.constructGenerateInstances()); }
	 */

	/**
	 * @return all place isntances of the PetriNet of this simulator
	 * @throws Exception
	 *             if no Petrinet is associated with this simulator
	 */
	public List<Instance<Place>> getAllRealPlaceInstances() throws Exception {
		if (modelData != null) {
			final List<Instance<Place>> allPlaceInstances = modelData.getAllPlaceInstances();
			if (allPlaceInstances != null) { return allPlaceInstances; }
		}
		throw new Exception("You did not give a PetriNet on creation, so you cannot execute arbitrary bindings.");

	}

	/**
	 * @return all transition isntances of the PetriNet of this simulator
	 * @throws Exception
	 *             if no Petrinet is associated with this simulator
	 */
	public List<Instance<Transition>> getAllTransitionInstances() throws Exception {
		if (modelData != null) {
			final List<Instance<Transition>> allTransitionInstances = modelData.getAllTransitionInstances();
			if (allTransitionInstances != null) { return allTransitionInstances; }
		}
		throw new Exception("You did not give a PetriNet on creation, so you cannot execute arbitrary bindings.");

	}

	/**
	 * @return the simulator banner
	 */
	public String getBanner() {
		return getSimulator().getBanner();
	}

	/**
	 * @param ti
	 * @return
	 */
	public List<Binding> getBindings(final Instance<? extends Transition> ti) {
		lock();
		Packet result;
		try {
			result = send(PacketGenerator.instance.constructGetBindings(ti.getNode().getId(), ti.getInstanceNumber()));
			send(PacketGenerator.instance.constructCancelManualBinding());
		} catch (final Exception e) {
			return Collections.emptyList();
		} finally {
			release();
		}
		@SuppressWarnings("hiding")
		final Bindings bindings = getBindings(ti, result);
		return bindings.getAllBindings();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public State getMarking() throws Exception {
		return getMarking(getAllPlaceInstances());
	}

	/**
	 * @param copies
	 * @return
	 * @throws Exception
	 */
	public State getMarking(final boolean copies) throws Exception {
		if (copies) { return getMarking(); }
		return getMarking(getAllRealPlaceInstances());
	}

	/**
	 * @param pis
	 * @return
	 * @throws Exception
	 */
	public State getMarking(final Collection<? extends Instance<? extends PlaceNode>> pis) throws Exception {
		final Packet p = send(PacketGenerator.instance.constructGetMarking(pis));
		if (p.getInteger() == 1) {
			final int noMarkings = p.getInteger();
			assert noMarkings == pis.size();
			final List<Marking> markings = new ArrayList<Marking>();
			for (final Instance<? extends PlaceNode> pi : pis) {
				final int tokens = p.getInteger();
				final String marking = p.getString();
				if (tokens < 0) { throw new Exception(marking); }
				final Marking m = InstanceFactory.INSTANCE.createMarking(tokens, marking);
				m.setPlaceInstance(pi);
				markings.add(m);
			}
			return InstanceFactory.INSTANCE.createState(markings);
		}
		return null;
	}

	/**
	 * @param pi
	 * @return string representation of marking of pi if available
	 * @throws Exception
	 *             if ML returns an error
	 */
	public String getMarking(final Instance<PlaceNode> pi) throws Exception {
		final Packet p = send(PacketGenerator.instance.constructGetMarking(Collections.singletonList(pi)));
		if (p.getInteger() == 1) {
			if (p.getInteger() == 1) {
				if (p.getInteger() >= 0) {
					return p.getString();
				} else {
					throw new Exception(p.getString());
				}
			} else {
				assert false;
			}
		}
		return null;
	}

	/**
	 * @return
	 */
	public Simulator getSimulator() {
		return simulator;
	}

	/**
	 * @return the current simulator step (as a string)
	 * @throws IOException
	 *             on error
	 */
	public BigInteger getStep() throws IOException {
		final Packet p = send(PacketGenerator.instance.constructGetTimeAndStep());
		p.getString();
		return new BigInteger(p.getString());
	}

	/**
	 * @param pi
	 * @return
	 * @throws Exception
	 */
	public List<CPNValue> getStructuredMarking(final Instance<PlaceNode> pi) throws Exception {
		final String marking = getMarking(pi);
		final Marking m = InstanceFactory.INSTANCE.createMarking(marking);
		m.setPlaceInstance(pi);
		return getStructuredMarking(m);
	}

	/**
	 * @return structured representation of marking of pi if available
	 * @throws Exception
	 *             if ML returns an error
	 */
	public List<CPNValue> getStructuredMarking(final Marking marking) throws Exception {
		if ("empty".equals(marking.getMarking())) { return Collections.emptyList(); }
		final List<CPNValue> m = evaluate(modelData.getType(marking.getPlaceInstance()), marking.getPlaceInstance()
		        .getNode().getSort().getText(), '(' + marking.getMarking() + ')');
		return m;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Instance<? extends PlaceNode>, List<CPNValue>> getStructuredMarking(
	        @SuppressWarnings("hiding") final State state, final Map<String, List<CPNValue>> mscache) throws Exception {
		final Map<Instance<? extends PlaceNode>, List<CPNValue>> markings = new HashMap<Instance<? extends PlaceNode>, List<CPNValue>>();
		for (final Marking marking : state.getAllMarkings()) {
			List<CPNValue> m = mscache.get(marking.getMarking());
			if (m == null) {
				m = getStructuredMarking(marking);
				mscache.put(marking.getMarking(), m);
			}
			markings.put(marking.getPlaceInstance(), m);
		}
		return markings;
	}

	/**
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public Map<Instance<? extends PlaceNode>, List<CPNValue>> getStructuredMarking(
	        @SuppressWarnings("hiding") final State state) throws Exception {
		return getStructuredMarking(state, new HashMap<String, List<CPNValue>>());
	}

	/**
	 * @return the current simulator time (as a string)
	 * @throws IOException
	 *             on error
	 */
	public String getTime() throws IOException {
		final Packet p = send(PacketGenerator.instance.constructGetTimeAndStep());
		return p.getString();
	}

	/**
	 * @return
	 * @throws IOException
	 */
	public String increaseTime() throws IOException {
		final Packet p = send(PacketGenerator.instance.createIncreaseTime());
		if (p.getBoolean()) { return null; }
		return p.getString();
	}

	/**
	 * @throws IOException
	 *             if an IO error occurred
	 */
	public void initialiseSimulationScheduler() throws IOException {
		send(PacketGenerator.instance.constructInitialiseSimulationScheduler());
	}

	/**
	 * @throws IOException
	 *             if an IO error occurred
	 */
	public void initialize() throws IOException {
		send(PacketGenerator.instance.constructInitialize());
	}

	/**
	 * @throws IOException
	 *             if an IO error occurred
	 */
	public void initializeSyntaxCheck() throws IOException {
		send(PacketGenerator.instance.constructInitializeSyntaxCheck());
	}

	/**
	 * @throws IOException
	 *             on error
	 */
	public void initialState() throws IOException {
		send(PacketGenerator.instance.constructInitialState());
	}

	/**
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#isAdapterForType(java.lang.Object)
	 */
	@Override
	public boolean isAdapterForType(final Object type) {
		return HighLevelSimulator.class.equals(type);
	}

	/**
	 * @param tis
	 *            list of transition instances to check enabledness for
	 * @return list of enabled transition instances from the list
	 * @throws IOException
	 *             on error
	 */
	public List<Instance<? extends Transition>> isEnabled(final Collection<? extends Instance<? extends Transition>> tis)
	        throws IOException {
		final Packet p = send(PacketGenerator.instance.constructIsEnabled(tis));
		final List<Instance<? extends Transition>> result = new ArrayList<Instance<? extends Transition>>();
		for (final Instance<? extends Transition> ti : tis) {
			if (p.getBoolean()) {
				result.add(ti);
			}
		}
		return result;
	}

	/**
	 * @param ti
	 *            the transition instance to check enabledness for
	 * @return whether the TI is enabled
	 * @throws IOException
	 *             on error
	 */
	public boolean isEnabled(final Instance<? extends Transition> ti) throws IOException {
		return send(PacketGenerator.instance.constructIsEnabled(ti.getNode().getId(), ti.getInstanceNumber()))
		        .getBoolean();
	}

	/**
	 * 
	 */
	public void lock() {
		getSimulator().lock();
	}

	/**
	 * Rebind a transition in another binding. Only output places are considered, i.e., it's as if the transition was
	 * executed in oldBinding and, during execution, after consuming all tokens (treating double arcs as test arcs), the
	 * binding was changed and tokens are produced in the new binding. This assumes the transition was just executed in
	 * oldBinding.
	 * 
	 * @param oldBinding
	 * @param newBinding
	 * @throws Exception
	 */
	public void rebind(final Binding oldBinding, final Binding newBinding) throws Exception {
		final Instance<Transition> instance = oldBinding.getTransitionInstance();
		final Transition transition = instance.getNode();
		assert transition.equals(newBinding.getTransitionInstance().getNode());
		lock();
		try {
			rebind(oldBinding, transition.getSourceArc(), true);
			rebind(newBinding, transition.getSourceArc(), false);
		} finally {
			release();
		}
	}

	/**
	 * @throws Exception
	 * @throws IOException
	 */
	public void refreshViews() throws IOException, Exception {
		lock();
		try {
			refreshViews(getTime(), getStep());
		} finally {
			release();
		}
	}

	/**
	 * @throws Exception
	 */
	public void refreshViews(final String time, final BigInteger step) throws Exception {
		@SuppressWarnings("hiding")
		State state;
		final List<Instance<Transition>> allTransitions = new ArrayList<Instance<Transition>>();
		Set<? extends Instance<? extends Transition>> enabledTransitions;
		@SuppressWarnings("hiding")
		final PetriNet petriNet = this.petriNet;
		if (modelInstance == null) { return; }
		for (final Page page : petriNet.getPage()) {
			for (final Transition t : page.transition()) {
				allTransitions.addAll(modelInstance.getAllInstances(t));
			}
		}

		lock();
		try {
			state = getMarking();
			final List<Instance<? extends Transition>> enabled = isEnabled(allTransitions);
			enabledTransitions = new HashSet<Instance<? extends Transition>>(enabled);
		} finally {
			release();
		}

		final Map<Simulator.SimpleInstance, Boolean> enablings = new HashMap<Simulator.SimpleInstance, Boolean>();
		final Map<Simulator.SimpleInstance, Simulator.SimpleMarking> markings = new HashMap<Simulator.SimpleInstance, Simulator.SimpleMarking>();

		for (final Instance<Transition> instance : allTransitions) {
			final Simulator.SimpleInstance simpleInstance = new Simulator.SimpleInstance(instance.getInstanceNumber(),
			        instance.getNode().getId());
			enablings.put(simpleInstance, enabledTransitions.contains(instance));
		}
		for (final Marking m : state.getAllMarkings()) {
			final Simulator.SimpleInstance simpleInstance = new Simulator.SimpleInstance(m.getPlaceInstance()
			        .getInstanceNumber() == 0 ? 1 : m.getPlaceInstance().getInstanceNumber(), m.getPlaceInstance()
			        .getNode().getId());
			final Simulator.SimpleMarking simpleMarking = new Simulator.SimpleMarking(m.getTokenCount(), m.getMarking());
			markings.put(simpleInstance, simpleMarking);
		}
		simulator.refreshViews(markings, enablings, time, "" + step);
	}

	/**
	 * 
	 */
	public void release() {
		getSimulator().release();
	}

	/**
	 * @param handlers
	 *            handler to remove
	 */
	public void removeHandler(final Object... handlers) {
		gramHandler.removeLocalHandler(handlers);
	}

	/**
	 * Roll back execution of binding. This is not fast!
	 * 
	 * @param binding
	 * @throws Exception
	 */
	public void rollBack(final Binding binding) throws Exception {
		final Instance<Transition> instance = binding.getTransitionInstance();
		final Transition transition = instance.getNode();
		lock();
		try {
			rebind(binding, transition.getSourceArc(), true);
			rebind(binding, transition.getTargetArc(), false);
		} finally {
			release();
		}
	}

	/**
	 * Save the current report in the given file.
	 * 
	 * @param file
	 *            file to save report to.
	 * @throws IOException
	 *             on error
	 */
	public void saveSimulationReport(final File file) throws IOException {
		saveSimulationReport(file.getAbsolutePath());
	}

	/**
	 * @param file
	 * @throws IOException
	 */
	public void saveSimulationReport(final String file) throws IOException {
		send(PacketGenerator.instance.constructSaveSimulationReport(file));
	}

	/**
	 * @param p
	 *            packet to send
	 * @return the resulting packet from the simulator
	 * @throws IOException
	 *             if an IO error occurred
	 */
	public Packet send(final Packet p) throws IOException {
		return getSimulator().send(p);
	}

	/**
	 * @param resetRan
	 *            reset random number generator
	 * @param resetRef
	 *            reset ref
	 * @param randomSeed
	 *            seed for random number generator
	 * @throws IOException
	 *             if an IO error occurred
	 */
	public void setInitializationSimulationOptions(final boolean resetRan, final boolean resetRef, final int randomSeed)
	        throws IOException {
		send(PacketGenerator.instance.constructSetInitializationSimulationOptions(resetRan, resetRef, randomSeed));
	}

	/**
	 * @param pi
	 * @param marking
	 * @return
	 * @throws IOException
	 */
	public boolean setMarking(final Instance<? extends PlaceNode> pi, final Marking marking) throws IOException {
		return setMarking(pi, marking.getMarking());
	}

	/**
	 * Set marking of place instance if it is legal. There is no need to call checkMArking (as the manual says) as this
	 * takes care of that.
	 * 
	 * @param pi
	 *            place instance
	 * @param marking
	 *            marking
	 * @throws IOException
	 *             on error
	 */
	public boolean setMarking(final Instance<? extends PlaceNode> pi, final String marking) throws IOException {
		lock();
		try {
			if (checkMarking(pi.getNode(), marking)) {
				send(PacketGenerator.instance.constructSetMarking(pi.getNode().getId(), pi.getInstanceNumber(), false));
				return true;
			}
		} finally {
			release();
		}
		return false;
	}

	/**
	 * @param state
	 * @throws IOException
	 */
	public void setMarking(final State state) throws IOException {
		lock();
		try {
			for (final Marking m : state.getAllMarkings()) {
				setMarking(m.getPlaceInstance(), m);
			}
		} finally {
			release();
		}
	}

	/**
	 * @param state
	 * @throws Exception
	 */
	public void setMarkingFast(final State state, final State oldState, final boolean resetScheduler) throws Exception {
		final StringBuilder sb = new StringBuilder();
		boolean changed = false;
		for (final Marking m : state.getAllMarkings()) {
			if (m.getPlaceInstance().getNode() instanceof Place) {
				if (oldState == null || !m.getMarking().equals(oldState.getMarking(m.getPlaceInstance()).getMarking())) {
					changed = true;
					sb.append("CPN'place");
					sb.append(m.getPlaceInstance().getNode().getId());
					sb.append(".set ");
					sb.append(m.getPlaceInstance().getInstanceNumber());
					m.getPlaceInstance().getInstanceNumber();
					sb.append(" (");
					sb.append(m.getMarking());
					sb.append(");");
				}
			}
		}
		if (changed) {
			lock();
			try {
				if (resetScheduler) {
					sb.append("CPN'Sim.reset_scheduler();");
				}
				evaluate(sb.toString());
			} finally {
				release();
			}
		}
	}

	/**
	 * @param state
	 * @throws Exception
	 */
	public void setMarkingFast(final State state) throws Exception {
		setMarkingFast(state, null, true);
	}

	/**
	 * @param modelName
	 *            name of model
	 * @param modelDir
	 *            directory of model (should be absolute path)
	 * @param outputDir
	 *            output dir (if "" set to modeldir/output)
	 * @throws Exception
	 *             on error; if Exception something went wrong setting paths (probably because paths do not exist or are
	 *             not writable; if IOException comm error
	 */
	public void setModelNameModelDirOutputDir(final String modelName, final String modelDir, final String outputDir)
	        throws Exception {
		final Packet p = send(PacketGenerator.instance.constructModelNameModelDirOutputDir(modelName, modelDir,
		        outputDir));
		if (!p.getBoolean()) { throw new Exception(p.getString()); }
	}

	/**
	 * @param pausebefore
	 *            pause before executing transition
	 * @param pauseafter
	 *            pause after executing transition
	 * @param pauseshow
	 *            pause before showing
	 * @param reporttrans
	 *            report transitions
	 * @param reportbinds
	 *            report binding elements
	 * @param showmarking
	 *            show marking
	 * @param showenabling
	 *            show enabling
	 * @param untilstep
	 *            run until step
	 * @param addstep
	 *            run for additional steps
	 * @param untiltime
	 *            run until time
	 * @param addtime
	 *            run additional time units
	 * @param pausecont
	 *            pause cont
	 * @param reportfunc
	 *            reporting function
	 * @throws IOException
	 *             if an IO error occurred
	 */
	public void setSimulationOptions(final boolean pausebefore, final boolean pauseafter, final boolean pauseshow,
	        final boolean reporttrans, final boolean reportbinds, final boolean showmarking,
	        final boolean showenabling, final String untilstep, final String addstep, final String untiltime,
	        final String addtime, final String pausecont, final String reportfunc, final boolean fairBE,
	        final boolean globalFairness) throws IOException {
		this.pausebefore = pausebefore;
		this.pauseafter = pauseafter;
		this.pauseshow = pauseshow;
		this.fairBE = fairBE;
		this.globalFairness = globalFairness;
		this.reporttrans = reporttrans || reportbinds;
		this.reportbinds = reportbinds;
		this.showmarking = showmarking;
		this.showenabling = showenabling;
		this.untilstep = untilstep;
		this.addstep = addstep;
		this.untiltime = untiltime;
		this.addtime = addtime;
		this.pausecont = pausecont;
		this.reportfunc = reportfunc;
		send(PacketGenerator.instance.constructSetSimulationOptions(pausebefore, pauseafter, pauseshow,
		        this.reporttrans, reportbinds, showmarking, showenabling, untilstep, addstep, untiltime, addtime,
		        pausecont, reportfunc, fairBE, globalFairness));
	}

	/**
	 * Start reporting; clears data gathered until now and sets options for future reporting.
	 * 
	 * @param reporttrans
	 *            report transition executions
	 * @param reportbinds
	 *            report bindings
	 * @param reportfunc
	 *            function for reporting
	 * @throws IOException
	 *             on error
	 */
	public void setSimulationReportOptions(final boolean reporttrans, final boolean reportbinds, final String reportfunc)
	        throws IOException {
		lock();
		try {
			this.reporttrans = reporttrans;
			this.reportbinds = reportbinds;
			this.reportfunc = reportfunc;
			setSimulationOptions(pausebefore, pauseafter, pauseshow, reporttrans, reportbinds, showmarking,
			        showenabling, untilstep, addstep, untiltime, addtime, pausecont, reportfunc, fairBE, globalFairness);
			send(PacketGenerator.instance.constructResetSimulationReport());
		} finally {
			release();
		}
	}

	/**
	 * Start reporting; clears data gathered until now and sets options for future reporting.
	 * 
	 * @throws IOException
	 *             on error
	 */
	public void setFairnessOptions(final boolean fairBE, final boolean globalFairness) throws IOException {
		lock();
		try {
			this.fairBE = fairBE;
			this.globalFairness = globalFairness;
			setSimulationOptions(pausebefore, pauseafter, pauseshow, reporttrans, reportbinds, showmarking,
			        showenabling, untilstep, addstep, untiltime, addtime, pausecont, reportfunc, fairBE, globalFairness);
			send(PacketGenerator.instance.constructResetSimulationReport());
		} finally {
			release();
		}
	}

	/**
	 * @param timeStamp
	 * @throws IOException
	 */
	public void setSimulatorTime(final BigInteger timeStamp) throws IOException {
		send(PacketGenerator.instance.constructSetSimulatorTime(timeStamp.toString()));
	}

	/**
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#setTarget(org.eclipse.emf.common.notify.Notifier)
	 */
	@Override
	public void setTarget(final Notifier notifier) {
		super.setTarget(notifier);
		if (notifier != null && notifier instanceof PetriNet) {
			setPetriNet((PetriNet) notifier);
		}
	}

	/**
	 * @param file
	 *            url of file to load
	 * @return the result
	 * @throws Exception
	 *             on error; if Exception an exception was raised in ML, if IOException, comm error
	 */
	public String use(final URL file) throws Exception {
		final ByteArrayOutputStream o = new ByteArrayOutputStream();
		StreamUtillities.copy(file.openStream(), o);
		return evaluate(o.toString());
	}

	/**
	 * @param petriNet
	 */
	protected void setPetriNet(final PetriNet petriNet) {
		this.petriNet = petriNet;
		if (petriNet != null) {
			modelData = (ModelData) ModelDataAdapterFactory.getInstance().adapt(petriNet, ModelData.class);
			modelInstance = (ModelInstance) ModelInstanceAdapterFactory.getInstance().adapt(petriNet,
			        ModelInstance.class);
		} else {
			modelData = null;
			modelInstance = null;
		}
	}

	private Bindings getBindings(final Instance<? extends Transition> ti, final Packet result) {
		final List<List<String>> variables = new ArrayList<List<String>>();
		final List<List<List<String>>> values = new ArrayList<List<List<String>>>();
		result.getInteger(); // RESPTAG = 2
		result.getInteger(); // RESPKIND = 60
		final int groups = result.getInteger();
		for (int i = 0; i < groups; i++) {
			final int varCount = result.getInteger();
			final int bindingsCount = result.getInteger();
			final List<String> vars = new ArrayList<String>();
			final List<List<String>> vals = new ArrayList<List<String>>();
			for (int j = 0; j < varCount; j++) {
				vars.add(result.getString());
			}
			for (int j = 0; j < bindingsCount; j++) {
				final List<String> value = new ArrayList<String>();
				for (int k = 0; k < varCount; k++) {
					value.add(result.getString());
				}
				vals.add(value);
			}
			variables.add(vars);
			values.add(vals);
		}
		return new Bindings(ti, variables, values);
	}

	private void rebind(final Binding binding, final Collection<Arc> arcs, final boolean remove) throws Exception,
	        IOException {
		final List<Instance<PlaceNode>> places = new ArrayList<Instance<PlaceNode>>();
		final Map<Instance<PlaceNode>, List<CPNValue>> tokens = new HashMap<Instance<PlaceNode>, List<CPNValue>>();
		for (final Arc arc : arcs) {
			if (arc.getKind() != HLArcType.TEST) {
				final Instance<PlaceNode> placeInstance = InstanceFactory.INSTANCE.createInstance(arc.getPlaceNode(),
				        binding.getTransitionInstance().getTransitionPath());
				places.add(placeInstance);
				tokens.put(placeInstance, evaluate(arc, binding));
			}
		}
		lock();
		try {
			@SuppressWarnings("hiding")
			final State state = getMarking(places);
			final List<Marking> markings = new ArrayList<Marking>();
			for (final Instance<PlaceNode> place : places) {
				Marking marking;
				if (modelData.isTimed(place)) {
					if (remove) {
						marking = InstanceFactory.INSTANCE.createMarking("CPN'TMS.tsub(("
						        + state.getMarking(place).getMarking() + "), (" + tokens.get(place) + "))");
					} else {
						marking = InstanceFactory.INSTANCE.createMarking("(" + state.getMarking(place).getMarking()
						        + ") +++ (" + tokens.get(place) + ")");
					}
				} else {
					if (remove) {
						marking = InstanceFactory.INSTANCE.createMarking("(" + state.getMarking(place).getMarking()
						        + ") -- (" + tokens.get(place) + ")");
					} else {
						marking = InstanceFactory.INSTANCE.createMarking("(" + state.getMarking(place).getMarking()
						        + ") ++ (" + tokens.get(place) + ")");
					}
				}
				marking.setPlaceInstance(place);
				markings.add(marking);
			}
			setMarking(InstanceFactory.INSTANCE.createState(markings));
		} finally {
			release();
		}
	}
}
