/************************************************************************/
/* Access/CPN                                                           */
/* Copyright 2010-2011 AIS Group, Eindhoven University of Technology    */
/*                                                                      */
/* This library is free software; you can redistribute it and/or        */
/* modify it under the terms of the GNU Lesser General Public           */
/* License as published by the Free Software Foundation; either         */
/* version 2.1 of the License, or (at your option) any later version.   */
/*                                                                      */
/* This library is distributed in the hope that it will be useful,      */
/* but WITHOUT ANY WARRANTY; without even the implied warranty of       */
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU    */
/* Lesser General Public License for more details.                      */
/*                                                                      */
/* You should have received a copy of the GNU Lesser General Public     */
/* License along with this library; if not, write to the Free Software  */
/* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,           */
/* MA  02110-1301  USA                                                  */
/************************************************************************/
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
