/**
 * 
 */
package org.cpntools.accesscpn.engine.highlevel.utils;

import java.util.Arrays;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * @author mw
 */
public class SingleLineLogFormatter extends Formatter {
	/**
	 * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
	 */
	@Override
	public String format(final LogRecord record) {
		final StringBuilder sb = new StringBuilder();
		sb.append(record.getLoggerName());
		sb.append(": ");
		sb.append(record.getLevel());
		sb.append(": ");
		sb.append(record.getThreadID());
		sb.append('.');
		sb.append(record.getSourceClassName().replaceAll(".*[.]", ""));
		sb.append('.');
		sb.append(record.getSourceMethodName());
		if (record.getParameters() != null) {
			sb.append('(');
			sb.append(Arrays.toString(record.getParameters()));
			sb.append(')');
		}
		sb.append(": ");
		sb.append(record.getMessage());
		if (record.getThrown() != null) {
			sb.append('\n');
			sb.append(record.getThrown());
			for (final StackTraceElement traceElement : record.getThrown().getStackTrace()) {
				sb.append("\n\tat ");
				sb.append(traceElement);
			}
		}
		sb.append('\n');
		return sb.toString();
	}
}