package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import interfaces.Plotable;

public class AdjacencyList {

	private Map<Plotable, List<Adjacency>> list;

	public AdjacencyList() {
		list = new LinkedHashMap<Plotable, List<Adjacency>>();
	}

	public boolean addVertex(Plotable vertex) {
		if (!list.containsKey(vertex)) {
			list.put(vertex, new ArrayList<Adjacency>());
			return true;
		}
		return false;
	}

	public boolean addAdjacency(Plotable origin, Plotable destiny) {
		if (!list.containsKey(origin)) {
			addVertex(origin);
		}
		if (!list.containsKey(destiny)) {
			addVertex(destiny);
		}
		List<Adjacency> aux = list.get(origin);
		Adjacency adjacencyAux = new Adjacency(destiny, 1);
		if (aux.indexOf(adjacencyAux) == -1) {
			aux.add(adjacencyAux);
			return true;
		} else {
			aux.get(aux.indexOf(adjacencyAux)).upWeightByOne();
		}
		return false;
	}

	public Plotable getVertex(int index) {
		int cont = 0;
		for (Entry<Plotable, List<Adjacency>> valueSet : list.entrySet()) {
			if (cont == index) {
				return valueSet.getKey();
			}
			cont++;
		}
		return null;
	}

	// https://stackoverflow.com/questions/29567575/sort-map-by-value-using-lambdas-and-streams
	public void sort() {
		list = list.entrySet().stream()
				.sorted(Map.Entry.<Plotable, List<Adjacency>>comparingByValue((a, b) -> Integer.compare(a.size(), b.size())))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(e1, e2) -> e1, LinkedHashMap::new));
	}

	/**
	 * prints Adjacency List
	 */
	public void print() {
		sort();
		int count = 0;
		for (Entry<Plotable, List<Adjacency>> valueSet : list.entrySet()) {
			Plotable key = valueSet.getKey();
			List<Adjacency> innerList = valueSet.getValue();
			Collections.sort(innerList);
			System.out.printf("| (%02d) %s || ", count, key.getLabel());
			for (Adjacency adjacency : innerList) {
				System.out.printf("%-25s | ", adjacency);
			}
			count++;
			System.out.println();
		}
	}

}
