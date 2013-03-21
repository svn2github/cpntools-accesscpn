package org.cpntools.accesscpn.engine.protocol.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cpntools.accesscpn.engine.utils.SingleLineLogFormatter;

/**
 * @author mw
 */
public class GramHandler implements Handler {
	private static List<Object> handlers = Collections.synchronizedList(new ArrayList<Object>());
	private static final Logger logger = Logger.getLogger("GramHandler");
	/**
	 * 
	 */
	public static final boolean DEBUG_GRAM = false;

	static {
		if (DEBUG_GRAM) {
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

	/**
	 * 
	 */
	public GramHandler() {
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
	 * @see org.cpntools.accesscpn.engine.protocol.handler.Handler#handle(java.util.List)
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