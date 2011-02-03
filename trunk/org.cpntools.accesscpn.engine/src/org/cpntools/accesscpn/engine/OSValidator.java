package org.cpntools.accesscpn.engine;

/**
 * @author mwesterg
 *
 */
public class OSValidator {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		if (isWindows()) {
			System.out.println("This is Windows");
		} else if (isMac()) {
			System.out.println("This is Mac");
		} else if (isUnix()) {
			System.out.println("This is Unix or Linux");
		} else {
			System.out.println("Your OS is not support!!");
		}
	}

	/**
	 * @return
	 */
	public static boolean isWindows() {

		final String os = System.getProperty("os.name").toLowerCase();
		// windows
		return os.indexOf("win") >= 0;

	}

	/**
	 * @return
	 */
	public static boolean isMac() {

		final String os = System.getProperty("os.name").toLowerCase();
		// Mac
		return os.indexOf("mac") >= 0;

	}

	/**
	 * @return
	 */
	public static boolean isUnix() {

		final String os = System.getProperty("os.name").toLowerCase();
		// linux or unix
		return os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0;

	}
}