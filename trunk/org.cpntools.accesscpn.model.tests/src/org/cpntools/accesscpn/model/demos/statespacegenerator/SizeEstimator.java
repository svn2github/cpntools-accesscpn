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
package org.cpntools.accesscpn.model.demos.statespacegenerator;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author michael
 */
public class SizeEstimator {

	/**
	 * @param args
	 *            args
	 * @throws Exception
	 *             on error
	 * @throws InterruptedException
	 *             on error
	 */
	public static void main(final String[] args) throws InterruptedException, Exception {
		int ss = Integer.MAX_VALUE;
		if (args.length > 0) {
			ss = Integer.parseInt(args[0]);
		}
		boolean efficient = false;
		if (args.length > 1) {
			if ("-e".equals(args[1])) {
				System.out.println("Using efficient storage");
				efficient = true;
			}
		}
		System.out.println("Reading states...");
		final DataInputStream states = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(
		        "states.dmp"))));
		long bytes = 0;
		long aligned = 0;
		long alignedTotal = 0;
		final CompressedState c = new CompressedState();
		final int[] sizes = new int[1000];
		int max = 0, min = Integer.MAX_VALUE;
		int i = 0;
		try {
			for (i = 0; i < ss; i++) {
				if (i % 10000 == 0) {
					print(bytes, aligned, alignedTotal, i);
				}
				c.readObject(states);
				if (!c.isCheckpoint()) {
					final int arraySize = c.getArraySize();
					final int footprint = (arraySize + 7) / 8 * 8;
					if (!efficient) {
						alignedTotal += footprint + 16 + 16; // Array header + 4 pointers
					} else {
						alignedTotal += footprint + 16; // 4 integers
					}
					aligned += footprint;
					max = Math.max(max, arraySize);
					min = Math.min(min, arraySize);
					sizes[arraySize]++;
					bytes += arraySize;
					states.readUTF();
				} else {
					i--;
				}
			}
		} catch (final EOFException e) {
			// Ignore
		}
		print(bytes, aligned, alignedTotal, i);
		System.out.println("=========================");
		for (i = min; i <= max; i++) {
			if (sizes[i] > 0) {
				System.out.println(i + " (" + (i + 7) / 8 * 8 + "; " + ((i + 7) / 8 * 8 - i) + "): " + sizes[i]);
			}
		}
		states.close();

	}

	private static void print(final long bytes, final long aligned, final long aligned64, final int i) {
		System.out.println("States: " + i + ";\t bytes: " + bytes + " (" + format(bytes, i) + ");\t aligned = "
		        + aligned + " (" + format(aligned, i) + "); total = " + aligned64 + " (" + format(aligned64, i) + ")");
	}

	private static String format(final long bytes, final int i) {
		return "" + Math.round(10.0 * bytes / i) / 10.0;
	}

}
