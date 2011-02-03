package org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues;

import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelData;
import org.cpntools.accesscpn.model.cpntypes.CPNInt;
import org.cpntools.accesscpn.model.cpntypes.CPNType;


/**
 * @author mwesterg
 * 
 */
public class CPNInteger extends CPNValue {
	private final int value;

	/**
	 * @param value
	 */
	public CPNInteger(final int value) {
		super("");
		this.value = value;

	}

	/**
	 * @param value
	 */
	public CPNInteger(final String time, final int value) {
		super(time);
		this.value = value;

	}

	/**
	 * @return
	 */
	public int getValue() {
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
		return type instanceof CPNInt;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return value;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o == null || !(o instanceof CPNInteger)) return false;
		final CPNInteger other = (CPNInteger) o;
		return value == other.value;
	}
}
