import java.io.File;
import java.net.InetAddress;
import java.net.URL;

import org.cpntools.accesscpn.engine.DaemonSimulator;
import org.cpntools.accesscpn.engine.Simulator;
import org.cpntools.accesscpn.engine.highlevel.HighLevelSimulator;
import org.cpntools.accesscpn.engine.highlevel.checker.Checker;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.importer.DOMParser;


public class StateSpaceTool {
	public static void main(final String[] args) throws Exception {
		final String fileName = "/Users/michael/Dropbox/Documents/Work/Projects/CPNunf/models/erdp.cpn";// args[0];
		final PetriNet petriNet = DOMParser.parse(new URL("file://" + fileName));
		final HighLevelSimulator s = HighLevelSimulator.getHighLevelSimulator();
		try {
			final Checker checker = new Checker(petriNet, null, s);
			checker.checkEntireModel();
			s.evaluate("use \"/Users/michael/simple-dfs.sml\"");
			System.out
			        .println(s
			                .evaluate("let val (state, storage) = dfs dead (CPNToolsModel.getInitialStates()) in (state, HashTable.numItems storage) end"));
		} finally {
			s.destroy();
		}
	}
}
