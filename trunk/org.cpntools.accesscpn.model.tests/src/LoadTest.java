import java.io.File;

import javax.swing.JFileChooser;

import org.cpntools.accesscpn.engine.highlevel.HighLevelSimulator;
import org.cpntools.accesscpn.engine.highlevel.InstancePrinter;
import org.cpntools.accesscpn.engine.highlevel.checker.Checker;
import org.cpntools.accesscpn.engine.highlevel.checker.ErrorInitializingSMLInterface;
import org.cpntools.accesscpn.model.ModelPrinter;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.importer.DOMParser;

public class LoadTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception {
		final JFileChooser chooser = new JFileChooser();
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			load(chooser.getSelectedFile());
		}

	}

	private static void load(final File selectedFile) throws Exception {
		final PetriNet petriNet = DOMParser.parse(selectedFile.toURI().toURL());
		System.out.println(ModelPrinter.printModel(petriNet));
		System.out.println("=======================================================");
		System.out.println(InstancePrinter.printModel(petriNet));
		System.out.println("=======================================================");
		System.out.println(InstancePrinter.printMonitors(petriNet));
		final HighLevelSimulator s = HighLevelSimulator.getHighLevelSimulator();
		s.setSimulationReportOptions(false, false, "");
		final Checker checker = new Checker(petriNet, selectedFile, s);
		try {
			checker.checkEntireModel(selectedFile.getParent(), selectedFile.getParent());
		} catch (final ErrorInitializingSMLInterface _) {
			// Ignore
		}
		System.out.println("Done");
		System.out.println(s.execute(20));
		System.out.println(s.getMarking());
	}

}
