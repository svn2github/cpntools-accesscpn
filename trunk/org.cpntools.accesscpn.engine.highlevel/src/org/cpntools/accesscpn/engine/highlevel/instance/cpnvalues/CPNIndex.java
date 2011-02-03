package org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues;

import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelData;
import org.cpntools.accesscpn.model.cpntypes.CPNType;


/**
 * @author mwesterg
 * 
 */
public class CPNIndex extends CPNValue {

	private final int index;
	private final String name;

	/**
	 * @param index
	 */
	public CPNIndex(final String name, final int index) {
		this("", name, index);
	}

	/**
	 * @param time
	 * @param index
	 */
	public CPNIndex(final String time, final String name, final int index) {
		super(time);
		this.name = name;
		this.index = index;

	}

	/**
	 * @see org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNValue#matches(org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelData,
	 *      org.cpntools.accesscpn.model.cpntypes.CPNType)
	 */
	@Override
	protected boolean matchesInternal(final ModelData modelData, final CPNType type) {
		if (!(type instanceof org.cpntools.accesscpn.model.cpntypes.CPNIndex)) return false;
		final org.cpntools.accesscpn.model.cpntypes.CPNIndex indexType = (org.cpntools.accesscpn.model.cpntypes.CPNIndex) type;
		return indexType.getName().equals(name);
	}

	/**
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name + '(' + index + ')';
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return name.hashCode() + 37 * index;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o == null || !(o instanceof CPNIndex)) return false;
		final CPNIndex other = (CPNIndex) o;
		return index == other.index && name.equals(other.name);
	}
}
