package org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues;

import java.util.Collections;
import java.util.List;

import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelData;
import org.cpntools.accesscpn.model.cpntypes.CPNType;


/**
 * @author mwesterg
 * 
 * @param <T>
 */
public class CPNList<T extends CPNValue> extends CPNValue {
	private final List<T> values;

	/**
	 * @param values
	 */
	public CPNList(final List<T> values) {
		super("");
		this.values = Collections.unmodifiableList(values);
	}

	/**
	 * @param values
	 */
	public CPNList(final String time, final List<T> values) {
		super(time);
		this.values = Collections.unmodifiableList(values);
	}

	/**
	 * @return
	 */
	public List<T> getValues() {
		return values;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return values.toString();
	}

	/**
	 * @see org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNValue#matches(org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelData,
	 *      org.cpntools.accesscpn.model.cpntypes.CPNType)
	 */
	@Override
	protected boolean matchesInternal(final ModelData modelData, final CPNType type) {
		if (!(type instanceof org.cpntools.accesscpn.model.cpntypes.CPNList)) return false;
		final org.cpntools.accesscpn.model.cpntypes.CPNList lst = (org.cpntools.accesscpn.model.cpntypes.CPNList) type;
		final CPNType lsttype = modelData.getType(lst.getSort());
		for (final T value : values)
			if (!value.matches(modelData, lsttype)) return false;
		return true;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return values.hashCode();
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof CPNList<?>)) return false;
		CPNList<?> other = (CPNList<?>) o; 
		return values.equals(other.values);
	}
}
