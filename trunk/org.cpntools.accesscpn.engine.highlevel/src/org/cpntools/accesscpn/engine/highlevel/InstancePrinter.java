package org.cpntools.accesscpn.engine.highlevel;

import org.cpntools.accesscpn.engine.highlevel.instance.Instance;
import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelInstance;
import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelInstanceAdapterFactory;
import org.cpntools.accesscpn.model.Page;
import org.cpntools.accesscpn.model.PetriNet;


/**
 * @author mwesterg
 * 
 */
public class InstancePrinter {
	private final StringBuilder sb;

	/**
	 * 
	 */
	public InstancePrinter() {
		sb = new StringBuilder();
	}

	/**
	 * @param petriNet
	 * @return
	 */
	public static String printModel(final PetriNet petriNet) {
		return new InstancePrinter().print(petriNet);
	}

	private String print(final PetriNet petriNet) {
		for (final Page p : petriNet.getPage()) {
			print(p);
		}
		return sb.toString();
	}

	private void print(final Page p) {
		sb.append("Page ");
		sb.append(p.getName().getText());
		sb.append("\n");

		final ModelInstance modelInstance = (ModelInstance) ModelInstanceAdapterFactory
				.getInstance().adapt(p.getPetriNet(), ModelInstance.class);
		for (final Instance<Page> instance : modelInstance.getAllInstances(p)) {
			sb.append("  - ");
			if (instance != null) {
				sb.append(instance);
				sb.append(" - ");
				sb.append(instance.getInstanceNumber());
			} else {
				sb.append("Top - 1");
			}
			sb.append("\n");
		}
	}

}
