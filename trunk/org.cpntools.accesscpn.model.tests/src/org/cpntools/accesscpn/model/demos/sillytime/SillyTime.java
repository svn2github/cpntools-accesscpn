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
package org.cpntools.accesscpn.model.demos.sillytime;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.cpntools.accesscpn.engine.highlevel.HighLevelSimulator;
import org.cpntools.accesscpn.engine.proxy.ProxyDaemon;
import org.cpntools.accesscpn.engine.proxy.ProxySimulator;


public class SillyTime {
	private static final String[] approx = new String[] { "1", "2", "Threeish", "Four, I think",
			"Five or six", "Not too many", "Not too many", "Think it's about ten",
			"Think it's about ten", "Think it's about ten", "Lost count several steps ago",
			"Lost count several steps ago", "Lost count several steps ago", "Lets say it's 37",
			"Lets say it's 37", "Fifty?", "Fifty?", "Over nine thousaaand!", };

	public static void main(final String[] args) throws Exception {
		System.out.println("Starting proxy");
		final ProxyDaemon pd = ProxyDaemon.getDefaultInstance();
		while (true) {
			System.out.println("Waiting for CPN Tools");
			final ProxySimulator ps = pd.getNext();
			System.out.println("Waiting for syntax check");
			while (ps.getPetriNet() == null)
				Thread.sleep(500);
			ps.getPetriNet();
			new Thread() {
				@Override
				public void run() {
					final HighLevelSimulator simulator = ps.getSimulator();
					try {
						simulator.evaluate("val SILLY'step = ref \"nothing\";"
								+ "fun setWallClockTime() = (SILLY'step := \"wall\");"
								+ "fun setInternationalTime() = (SILLY'step := \"star\");"
								+ "fun setApproximateTime() = (SILLY'step := \"approx\")");
						int last = -1;
						int count = 0;
						final Random r = new Random();
						while (true) {
							final String result = simulator.evaluate("!SILLY'step");
							int level = last;
							if (result.indexOf("wall") >= 0) level = 1;
							if (result.indexOf("star") >= 0) level = 2;
							if (result.indexOf("approx") >= 0) level = 3;
							count++;
							if (level != last) count = 0;
							last = level;
							if (level == 1) {
								simulator.execute();
								final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
								final String s = formatter.format(new Date());
								simulator.refreshViews(s + " CEDT", simulator.getStep());
							}
							if (level == 2) {
								simulator.execute();
								final int main = r.nextInt(10000) + 40000;
								final int minor = r.nextInt(10);
								simulator.refreshViews("Stardate " + main + "." + minor, simulator
										.getStep());
							}
							if (level == 3) {
								simulator.execute();
								simulator.refreshViews(SillyTime.approx[Math.min(count,
										SillyTime.approx.length - 1)], simulator.getStep());
							}
							Thread.sleep(1000);
						}
					} catch (final Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
}
