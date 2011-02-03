package org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues;

import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelData;
import org.cpntools.accesscpn.model.cpntypes.CPNType;


/**
 * @author mwesterg
 * 
 */
public class CPNString extends CPNValue {
	private final String value;

	/**
	 * @param value
	 */
	public CPNString(final String time, final String value) {
		super(time);
		this.value = value;

	}

	/**
	 * @param value
	 */
	public CPNString(final String value) {
		super("");
		this.value = value;

	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return '"' + value.toString().replaceAll("\"", "\\\"") + '"';
	}

	/**
	 * @see org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNValue#matches(org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelData,
	 *      org.cpntools.accesscpn.model.cpntypes.CPNType)
	 */
	@Override
	protected boolean matchesInternal(final ModelData modelData, final CPNType type) {
		return type instanceof org.cpntools.accesscpn.model.cpntypes.CPNString;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return value.hashCode();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o == null || !(o instanceof CPNString)) return false;
		final CPNString other = (CPNString) o;
		return value.equals(other.value);
	}
}
