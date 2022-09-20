package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

	public int getTotalVertexes() {
		return list.size();
	}

	public int getTotalEdges() {
		int count = 0;
		for (Entry<Plotable, List<Adjacency>> entry : list.entrySet()) {
			count += entry.getValue().size();
		}
		return count;
	}
	
	public boolean addAdjacency(int origin, int destiny) {
		return addAdjacency(getVertex(origin), getVertex(destiny), false);
	}

	public boolean addAdjacency(int origin, Plotable destiny, boolean autoCreateVertex) {
		if(getVertex(origin) == null) {
			return false;
		}
		return addAdjacency(getVertex(origin), destiny, autoCreateVertex);
	}
	
	public boolean addAdjacency(int origin, Plotable destiny) {
		return addAdjacency(getVertex(origin), destiny, false);
	}
	
	/**
	 * Add an adjacency between two vertexes, if needed, the overload of this method
	 * can create new vertexes when setting adjacencies
	 * 
	 * @param origin  Where the adjacency will begin
	 * @param destiny Where the adjacency will end
	 * @return true if the adjacency was set up succesfully, else returns false
	 */
	public boolean addAdjacency(Plotable origin, Plotable destiny) {
		return addAdjacency(origin, destiny, false);
	}

	/**
	 * @param origin           Where the adjacency will begin
	 * @param destiny          Where the adjacency will end
	 * @param autoCreateVertex If true, creates new vertexes when needed
	 * @return true if the adjacency was set up succesfully, false otherwise. If
	 *         autoCreateVertex is true, it returns false only when the adjacency
	 *         already exists
	 */
	public boolean addAdjacency(Plotable origin, Plotable destiny, boolean autoCreateVertex) {
		if(origin == null || destiny == null) {
			throw new IllegalArgumentException("Error adding adjacency, origin or destiny are null!");
		}
		if (autoCreateVertex) {
			addVertex(origin);
			addVertex(destiny);
		} else {
			if (!list.containsKey(origin) || !list.containsKey(destiny)) {
				return false;
			}
		}
		List<Adjacency> aux = list.get(origin);
		Adjacency adjacencyAux = new Adjacency(destiny, 1);
		if (aux.indexOf(adjacencyAux) == -1) {
			aux.add(adjacencyAux);
			return true;
		}
		return false;
	}

	public Plotable getVertex(String label) {
		for (Entry<Plotable, List<Adjacency>> valueSet : list.entrySet()) {
			if (valueSet.getKey().getLabel().equals(label)) {
				return valueSet.getKey();
			}
		}
		return null;
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
				.sorted(Map.Entry.<Plotable, List<Adjacency>>comparingByValue((a, b) -> Integer.compare(a.size(), b.size()))
						.reversed())
//				.limit(1)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	/**
	 * prints Adjacency List
	 */
	public void print() {
		sort();
		int count = 0;
		final int emailPadding = -50;
		for (Entry<Plotable, List<Adjacency>> valueSet : list.entrySet()) {
			Plotable key = valueSet.getKey();
			List<Adjacency> innerList = valueSet.getValue();
			Collections.sort(innerList);
			System.out.printf("| (%02d) %" + emailPadding + "s || ", count, key.getLabel());
			for (Adjacency adjacency : innerList) {
				System.out.printf("%" + emailPadding + "s | ", adjacency);
			}
			count++;
			System.out.println();
		}
	}

}
