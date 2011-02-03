package org.cpntools.accesscpn.model.impl;

import java.util.Iterator;

/**
 * @author mw
 * @param <T> type to iterate over
 */
public abstract class FilterIterator<T> implements Iterator<T> {
	Iterator<T> realIterator;
	T nextElement;
	boolean hasNext;

	/**
	 * @param realIterator real iterator
	 */
	public FilterIterator(final Iterator<T> realIterator) {
		this.realIterator = realIterator;
		hasNext = true;
		fill();
	}

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return hasNext;
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	public T next() {
		final T next = nextElement;
		fill();
		return next;
	}

	private void fill() {
		if (hasNext) {
			while (realIterator.hasNext()) {
				final T potentialNextElement = realIterator.next();
				if (accept(potentialNextElement)) {
					this.nextElement = potentialNextElement;
					return;
				}
			}
			hasNext = false;
		}
	}

	/**
	 * @param value a value
	 * @return whether to include value in iterator
	 */
	public abstract boolean accept(T value);

	/**
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
