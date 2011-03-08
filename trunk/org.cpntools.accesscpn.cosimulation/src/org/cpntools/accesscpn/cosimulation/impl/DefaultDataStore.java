package org.cpntools.accesscpn.cosimulation.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Observable;

import org.cpntools.accesscpn.cosimulation.DataStore;
import org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNValue;

/**
 * 
 * @author mwesterg
 * 
 */
public class DefaultDataStore extends Observable implements DataStore {

	private boolean changed;
	private Collection<CPNValue> modifiableValues = new ArrayList<CPNValue>(), values = Collections
			.unmodifiableCollection(modifiableValues);

	@Override
	public synchronized void addValue(final CPNValue value) {
		modifiableValues.add(value);
		setChanged();
		notifyObservers(value);
	}

	@Override
	public synchronized Collection<CPNValue> getValues() {
		changed = false;
		return values;
	}

	@Override
	public boolean isChanged() {
		return changed;
	}

	@Override
	public synchronized boolean removeValue(final CPNValue value) {
		if (modifiableValues.remove(value)) {
			setChanged();
			notifyObservers(value);
			return true;
		}
		return false;
	}

	@Override
	public synchronized void setValues(final Collection<CPNValue> values) {
		modifiableValues = new ArrayList<CPNValue>(values);
		this.values = Collections.unmodifiableCollection(modifiableValues);
		setChanged();
		notifyObservers(modifiableValues);
	}

	@Override
	protected void setChanged() {
		changed = true;
		super.setChanged();
	}

}
