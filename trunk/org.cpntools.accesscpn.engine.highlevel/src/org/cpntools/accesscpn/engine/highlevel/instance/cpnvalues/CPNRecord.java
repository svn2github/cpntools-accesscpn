package org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelData;
import org.cpntools.accesscpn.model.cpntypes.CPNType;
import org.cpntools.accesscpn.model.cpntypes.NameTypePair;


/**
 * @author mwesterg
 * 
 */
public class CPNRecord extends CPNValue {
	private final Map<String, CPNValue> values;

	/**
	 * @param values
	 */
	public CPNRecord(final Map<String, CPNValue> values) {
		super("");
		this.values = Collections.unmodifiableMap(new TreeMap<String, CPNValue>(values));
	}

	/**
	 * @param values
	 */
	public CPNRecord(final String time, final Map<String, CPNValue> values) {
		super(time);
		this.values = Collections.unmodifiableMap(new TreeMap<String, CPNValue>(values));
	}

	/**
	 * @return
	 */
	public Map<String, CPNValue> getValues() {
		return values;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		boolean first = true;
		for (final Entry<String, CPNValue> entry : values.entrySet()) {
			if (!first) {
				sb.append(", ");
			}
			first = false;
			sb.append(entry.getKey());
			sb.append(" = ");
			sb.append(entry.getValue());
		}
		sb.append(" }");
		return sb.toString();
	}

	/**
	 * @see org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNValue#matches(org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelData,
	 *      org.cpntools.accesscpn.model.cpntypes.CPNType)
	 */
	@Override
	protected boolean matchesInternal(final ModelData modelData, final CPNType type) {
		if (!(type instanceof org.cpntools.accesscpn.model.cpntypes.CPNRecord)) return false;
		final org.cpntools.accesscpn.model.cpntypes.CPNRecord record = (org.cpntools.accesscpn.model.cpntypes.CPNRecord) type;
		if (record.getValues().size() != values.size()) return false;
		for (final NameTypePair pair : record.getValues()) {
			final CPNValue value = values.get(pair.getName());
			if (value == null) return false;
			if (!value.matches(modelData, modelData.getType(pair.getSort()))) return false;
		}
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
		if (o == null || !(o instanceof CPNRecord)) return false;
		CPNRecord other = (CPNRecord) o; 
		return values.equals(other.values);
	}
}
