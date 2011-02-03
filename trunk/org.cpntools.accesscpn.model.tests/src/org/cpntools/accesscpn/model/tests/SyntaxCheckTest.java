package org.cpntools.accesscpn.model.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

import org.cpntools.accesscpn.engine.DaemonSimulator;
import org.cpntools.accesscpn.engine.Simulator;
import org.cpntools.accesscpn.engine.highlevel.HighLevelSimulator;
import org.cpntools.accesscpn.engine.highlevel.checker.Checker;
import org.cpntools.accesscpn.model.PetriNet;
import org.cpntools.accesscpn.model.importer.DOMParser;

import junit.framework.TestCase;
import junit.textui.TestRunner;

public class SyntaxCheckTest extends TestCase {

	public static void main(final String[] args) {
		TestRunner.run(SyntaxCheckTest.class);
	}

	@Override
	protected void setUp() throws Exception {
		// setFixture(ModelFactory.eINSTANCE.createPetriNet());
	}

	@Override
	protected void tearDown() throws Exception {
		// setFixture(null);
	}

	public void testSyntaxCheckDatabase() throws Exception {
		checkStatesAndArcs("database.cpn", 110, 225);
	}

	public void testSyntaxCheckDrawing() throws Exception {
		checkStatesAndArcs("drawing.cpn", 58, 154);
	}

	private void checkStatesAndArcs(final String model, final int states, final int arcs) throws Exception {
		final String result = doSyntaxCheckofModel(model);
		// System.out.println("Model " + model + "\nResult = " + result);
		assertTrue("Correct number of states", result.contains("states = " + states));
		assertTrue("Correct number of arcs", result.contains("arcs = " + arcs));
	}

	public void testSyntaxCheckErdp() throws Exception {
		checkStatesAndArcs("erdp.cpn", 4173, 18962);
	}

	public void testSyntaxCheckExampleProtocol() throws Exception {
		checkStatesAndArcs("ExampleProtocol.cpn", 385, 798);
	}

	public void testSyntaxCheckPhilosophers() throws Exception {
		checkStatesAndArcs("philosophers.cpn", 15128, 167241);
	}

	public void testSyntaxCheckProtocol() throws Exception {
		checkStatesAndArcs("protocol.cpn", 8967, 25473);
	}

	public void testSyntaxCheckTelephones() throws Exception {
		checkStatesAndArcs("telephones.cpn", 6507, 47020);
	}

	private String doSyntaxCheckofModel(final String model) throws Exception {
		final String path = getResourcesFolder() + "models/" + model;
		System.out.println("Parsing " + path);
		final PetriNet petriNet = DOMParser.parse(new URL(path));
		System.out.println("Parse result: " + petriNet);

		System.out.println("getHighLevelSimulator 1");
		final HighLevelSimulator s = HighLevelSimulator.getHighLevelSimulator(new Simulator(new DaemonSimulator(
		        InetAddress.getByName("localhost"), 12345, new File("cpn.ML"))), petriNet);
		System.out.println("getHighLevelSimulator 2");
		final Checker checker = new Checker(petriNet, null, s);
		System.out.println("Checker");
		checker.checkInitializing();
		System.out.println("checkInitializing");
		checker.checkDeclarations();
		System.out.println("checkDeclarations");
		checker.checkPages();
		System.out.println("checkPages");
		checker.generateInstances();
		System.out.println("generateInstances");

		final String switchContent = getFileContentAsString(new URL(getResourcesFolder() + "switch.sml"));
		s.evaluate(switchContent);
		System.out.println("switchContent");

		final String quickContent = getFileContentAsString(new URL(getResourcesFolder() + "quick.sml"));
		final String result = s.evaluate(quickContent);
		System.out.println("quickContent");

		return result;
	}

	private String getResourcesFolder() {
		final String resPath = getClass().getResource("/").toString();
		return resPath.replaceAll("bin/", "") + "resources/";
	}

	private String getFileContentAsString(final URL path) throws IOException {
		final StringBuffer contents = new StringBuffer();
		final BufferedReader input = new BufferedReader(new InputStreamReader(path.openStream()));
		String line = null;

		while ((line = input.readLine()) != null) {
			contents.append(line);
			contents.append(System.getProperty("line.separator"));
		}
		return contents.toString();
	}
}
