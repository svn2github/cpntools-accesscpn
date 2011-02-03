package org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues;

import org.cpntools.accesscpn.engine.highlevel.instance.adapter.ModelData;
import org.cpntools.accesscpn.model.cpntypes.CPNAlias;
import org.cpntools.accesscpn.model.cpntypes.CPNSubset;
import org.cpntools.accesscpn.model.cpntypes.CPNType;


/**
 * @author mwesterg
 * 
 */
public abstract class CPNValue {
	private String time;

	/**
	 * @param time
	 */
	public CPNValue(final String time) {
		this.time = time;
	}

	/**
	 * @param time
	 */
	public void setTime(final String time) {
		this.time = time;
	}

	/**
	 * @param modelData
	 * @param type
	 * @return
	 */
	protected abstract boolean matchesInternal(ModelData modelData, CPNType type);

	/**
	 * @param modelData
	 * @param type
	 * @return
	 */
	public boolean matches(final ModelData modelData, final CPNType type) {
		return matchesInternal(modelData, getRealType(modelData, type));
	}

	protected CPNType getRealType(final ModelData modelData, final CPNType type) {
		if (type instanceof CPNAlias) {
			final CPNAlias alias = (CPNAlias) type;
			return getRealType(modelData, modelData.getType(alias.getSort()));
		} else if (type instanceof CPNSubset) {
			final CPNSubset subset = (CPNSubset) type;
			return getRealType(modelData, modelData.getType(subset.getSort()));
		}
		return type;
	}

	/**
	 * @return
	 */
	public String getTime() {
		return time;
	}
}
