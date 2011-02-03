package org.cpntools.accesscpn.engine.highlevel.checker;

import java.util.HashMap;
import java.util.Map;

import org.cpntools.accesscpn.engine.highlevel.LocalCheckFailed;
import org.cpntools.accesscpn.engine.highlevel.Util;
import org.cpntools.accesscpn.model.HasName;
import org.cpntools.accesscpn.model.Instance;
import org.cpntools.accesscpn.model.Node;
import org.cpntools.accesscpn.model.Object;
import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.PlaceNode;
import org.cpntools.accesscpn.model.TransitionNode;


/**
 * @author michael
 */
public class LocalChecker {
	private static LocalChecker instance = new LocalChecker();

	private LocalChecker() {
		// Make constructor private
	}

	/**
	 * @return
	 */
	public static LocalChecker getInstance() {
		return instance;
	}

	/**
	 * @param petriNet
	 * @throws LocalCheckFailed
	 */
	public void check(final PetriNet petriNet) throws LocalCheckFailed {
		for (final Page page : petriNet.getPage()) {
			checkPage(page);
		}
		checkUniquePageNames(petriNet);
	}

	private void checkUniquePageNames(final PetriNet petriNet) throws LocalCheckFailed {
		final Map<String, Page> names = new HashMap<String, Page>();
		for (final Page page : petriNet.getPage()) {
			final String name = getName(page);
			if (names.containsKey(name))
				throw new LocalCheckFailed(page.getId(), "Page name `" + page.getName().getText()
						+ "' is not unique (it is translated to `" + name + "', which clashes)");
			names.put(name, page);
		}
	}

	private void checkPage(final Page page) throws LocalCheckFailed {
		checkAllNames(page);
		checkUniqueNames(page);
	}

	private void checkUniqueNames(final Page page) throws LocalCheckFailed {
		final Map<String, Node> names = new HashMap<String, Node>();
		for (final Object object : page.getObject()) {
			if (object instanceof PlaceNode || object instanceof Instance
					|| object instanceof TransitionNode) {
				final Node node = (Node) object;
				final String name = getName(node);
				if (names.containsKey(name))
					throw new LocalCheckFailed(node.getId(), getNodeName(node)
							+ "' is not unique  page `" + getName(page)
							+ "' (it is translated to `" + name + "', which clashes with "
							+ getNodeName(names.get(name)) + ")");
				names.put(name, node);
			}
		}
	}

	private String getNodeName(final Node n) {
		final StringBuilder name = new StringBuilder();
		if (n instanceof PlaceNode) {
			name.append("Place");
		} else if (n instanceof Instance) {
			name.append("Substitution transition");
		} else if (n instanceof TransitionNode) {
			name.append("Transition");
		} else {
			name.append("Unknown node type (");
			name.append(n.getClass().getSimpleName());
			name.append(')');
		}
		name.append(" `");
		name.append(n.getName().getText());
		name.append("' (id = ");
		name.append(n.getId());
		name.append(')');
		return name.toString();
	}

	/**
	 * @param object
	 * @return
	 */
	public static String getName(final HasName object) {
		String name = object.getName().getText();
		if (name == null) return "";
		name = Util.mlEscape(name);
		name = name.replaceFirst("[^\\p{Alnum}'_].*", "");
		if (!name.matches("^[\\p{Alpha}].*")) return "";
		return name;
	}

	private void checkAllNames(final Page page) throws LocalCheckFailed {
		if ("".equals(getName(page)))
			throw new LocalCheckFailed(page.getId(), "Page has no or illegal name (name is `"
					+ page.getName().getText() + "')");
		for (final Object object : page.getObject()) {
			// System.out.println(object);
			if ("".equals(getName(object)))
				throw new LocalCheckFailed(object.getId(), "Object on page `" + getName(page)
						+ "' has no or illegal name (name is `" + object.getName().getText() + "')");
		}
	}
}
