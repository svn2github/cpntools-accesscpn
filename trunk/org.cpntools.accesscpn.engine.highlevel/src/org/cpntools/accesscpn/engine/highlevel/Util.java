package org.cpntools.accesscpn.engine.highlevel;

/**
 * @author mw
 */
public class Util {

	/**
	 * @param s
	 *            string
	 * @return whether the string is empty or null
	 */
	public static String emptyOrNull(final String s) {
		if (s == null)
			return "";
		else
			return s;
	}

	/**
	 * @param s
	 *            string to escape
	 * @return escaped version of s
	 */
	public static String mlEscape(final String s) {
		// TODO: Tjek om der er flere tegn som skal escapes
		return s.trim().replaceAll("[\\p{Space}\\-.]", "_");
	}
}
