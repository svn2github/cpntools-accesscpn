package org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues;

import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelData;
import org.cpntools.accesscpn.model.cpntypes.CPNBool;
import org.cpntools.accesscpn.model.cpntypes.CPNType;


/**
 * @author mwesterg
 * 
 */
public class CPNBoolean extends CPNValue {
	private final boolean value;

	/**
	 * @param value
	 */
	public CPNBoolean(final boolean value) {
		this("", value);
	}

	/**
	 * @param value
	 */
	public CPNBoolean(final String time, final boolean value) {
		super(time);
		this.value = value;

	}

	/**
	 * @return
	 */
	public boolean isValue() {
		return value;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "" + value;
	}

	/**
	 * @see org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNValue#matches(org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelData,
	 *      org.cpntools.accesscpn.model.cpntypes.CPNType)
	 */
	@Override
	protected boolean matchesInternal(final ModelData modelData, final CPNType type) {
		return type instanceof CPNBool;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (value) return 13;
		return 17;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o == null || !(o instanceof CPNBoolean)) return false;
		final CPNBoolean other = (CPNBoolean) o;
		return value == other.value;
	}
}
