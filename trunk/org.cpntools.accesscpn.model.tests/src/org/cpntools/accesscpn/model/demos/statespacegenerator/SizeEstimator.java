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
	 * @throws Exception
	 * @throws InterruptedException
	 */
	public static void main(final String[] args) throws InterruptedException, Exception {
		int ss = Integer.MAX_VALUE;
		if (args.length > 0) {
			ss = Integer.parseInt(args[0]);
		}
		System.out.println("Reading states...");
		final DataInputStream states = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(
		        "states.dmp"))));
		long bytes = 0;
		long aligned = 0;
		long aligned64 = 0;
		final CompressedState c = new CompressedState();
		final int[] sizes = new int[1000];
		int max = 0, min = Integer.MAX_VALUE;
		int i = 0;
		try {
			for (i = 0; i < ss; i++) {
				if (i % 10000 == 0) {
					print(bytes, aligned, aligned64, i);
				}
				c.readObject(states);
				if (!c.isCheckpoint()) {
					final int arraySize = c.getArraySize();
					max = Math.max(max, arraySize);
					min = Math.min(min, arraySize);
					sizes[arraySize]++;
					bytes += arraySize;
					aligned += (arraySize + 16 + 7) / 8 * 8;
					aligned64 += (arraySize + 3 * 8 + 7) / 8 * 8;
					states.readUTF();
				} else {
					i--;
				}
			}
		} catch (final EOFException e) {
			// Ignore
		}
		print(bytes, aligned, aligned64, i);
		System.out.println("=========================");
		for (i = min; i <= max; i++) {
			if (sizes[i] > 0) {
				System.out.println(i + " (" + (i + 16 + 7) / 8 * 8 + "; " + ((i + 7) / 8 * 8 - i) + "): " + sizes[i]);
			}
		}
		states.close();

	}

	private static void print(final long bytes, final long aligned, final long aligned64, final int i) {
		System.out.println("States: " + i + ";\t bytes: " + bytes + " (" + format(bytes, i) + ");\t aligned = "
		        + aligned + " (" + format(aligned, i) + "); aligned64 = " + aligned64 + " (" + format(aligned64, i)
		        + ")");
	}

	private static String format(final long bytes, final int i) {
		return "" + Math.round(10.0 * bytes / i) / 10.0;
	}

}
