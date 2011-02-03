

import org.cpntools.accesscpn.engine.highlevel.HighLevelSimulator;
import org.cpntools.accesscpn.engine.highlevel.InstancePrinter;
import org.cpntools.accesscpn.engine.highlevel.instance.Binding;
import org.cpntools.accesscpn.engine.proxy.ProxyDaemon;
import org.cpntools.accesscpn.engine.proxy.ProxySimulator;
import org.cpntools.accesscpn.model.ModelPrinter;
import org.cpntools.accesscpn.model.PetriNet;


public class SimulationTest {
	public static void main(final String[] args) throws Exception {
		System.out.println("Starting proxy");
		final ProxyDaemon pd = ProxyDaemon.getDefaultInstance();
		System.out.println("Waiting for CPN Tools");
		final ProxySimulator ps = pd.getNext();

		System.out.println("Waiting for syntax check");
		while (ps.getPetriNet() == null)
			Thread.sleep(500);
		final PetriNet petriNet = ps.getPetriNet();

		System.out.println(ModelPrinter.printModel(petriNet));
		System.out.println(InstancePrinter.printModel(petriNet));

		final HighLevelSimulator simulator = ps.getSimulator();

		System.out.println("===================================");
		simulator.execute(10);
		System.out.println("Skipped " + simulator.getStep() + " steps");
		for (int i = 0; i < 100; i++) {
			final Binding binding = simulator.executeAndGet();
			if (binding != null)
				System.out.println(simulator.getStep() + ": " + binding);
			else
				break;
		}
		System.out.println("===================================");
		simulator.destroy();
	}
}
