package org.cpntools.accesscpn.engine.highlevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.cpntools.accesscpn.engine.highlevel.instance.cpnvalues.CPNValue;


/**
 * @author mwesterg
 *
 */
public class MultiSetUtils {
	/**
	 * @param <T>
	 * @param a
	 * @param b
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> max(List<? extends T> a, List<? extends T> b) {
		if (a == null) return (List<T>) b;
		if (b == null) return (List<T>) a;
		List<T> result = new ArrayList<T>(a);
		Map<T, Integer> coeffs = new HashMap<T, Integer>();
		buildMap(a, coeffs);
		for (T t : b) {
			Integer coeff = coeffs.remove(t);
			if (coeff != null) {
				int newCoeff = coeff - 1;
				if (newCoeff > 0)
					coeffs.put(t, newCoeff);
			} else {
				result.add(t);
			}
		}
		return result;
	}

	private static <T> void buildMap(List<? extends T> a, Map<T, Integer> coeffs) {
		for (T t : a) {
			Integer coeff = coeffs.get(t);
			if (coeff == null) {
				coeffs.put(t, 1);
			} else {
				coeffs.put(t, coeff + 1);
			}
		}
	}
	
	/**
	 * @param <T>
	 * @param a
	 * @return
	 */
	public static <T extends Comparable<T>> String toString(List<? extends T> a) {
		SortedMap<T, Integer> coeffs = new TreeMap<T, Integer>();
		buildMap(a, coeffs);
		return buildString(coeffs);
	}
	
	/**
	 * @param <T>
	 * @param a
	 * @return
	 */
	public static <T> String toString(List<? extends T> a, boolean unsorted) {
		if (a == null || a.isEmpty()) return "empty";
		Map<T, Integer> coeffs = new HashMap<T, Integer>();
		buildMap(a, coeffs);
		return buildString(coeffs);
	}
	
	private static <T> String buildString(Map<T, Integer> coeffs) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Entry<T, Integer> entry: coeffs.entrySet()) {
			if (!first) sb.append(" ++ ");
			first = false;
			sb.append(entry.getValue());
			sb.append('`');
			sb.append(entry.getKey());
		}
		return sb.toString();
	}

	/**
	 * @param <T>
	 * @param a
	 * @param b
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> min(List<? extends T> a, List<? extends T> b) {
		if (a == null) return (List<T>) b;
		if (b == null) return (List<T>) a;
		List<T> result = new ArrayList<T>();
		if (a.isEmpty() || b.isEmpty()) return result;
		Map<T, Integer> coeffs = new HashMap<T, Integer>();
		buildMap(a, coeffs);
		for (T t : b) {
			Integer coeff = coeffs.remove(t);
			if (coeff != null) {
				int newCoeff = coeff - 1;
				if (newCoeff > 0) {
					coeffs.put(t, newCoeff);
					result.add(t);
				}
			}
		}
		return result;
	}

}
