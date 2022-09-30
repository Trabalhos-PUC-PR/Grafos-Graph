package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import interfaces.Plotable;

public class AdjacencyList {

	private Map<Plotable, List<Adjacency>> list;

	public AdjacencyList() {
		list = new LinkedHashMap<Plotable, List<Adjacency>>();
	}

	/**
	 * adds a given vertex to the graph
	 * 
	 * @param vertex the given vertex
	 * @return true if the vertex was added, false otherwise
	 */
	public boolean addVertex(Plotable vertex) {
		if (!list.containsKey(vertex)) {
			list.put(vertex, new ArrayList<Adjacency>());
			return true;
		}
		return false;
	}

	@Deprecated
	public boolean addAdjacency(int origin, int destiny) {
		return addAdjacency(getVertex(origin), getVertex(destiny), false);
	}

	@Deprecated
	public boolean addAdjacency(int origin, Plotable destiny, boolean autoCreateVertex) {
		if (getVertex(origin) == null) {
			return false;
		}
		return addAdjacency(getVertex(origin), destiny, autoCreateVertex);
	}

	@Deprecated
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
		if (origin == null || destiny == null) {
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

	/**
	 * gets total vertexes from graph
	 * 
	 * @return the total vertexes
	 */
	public int getTotalVertexes() {
		return list.size();
	}

	/**
	 * gets total of edges from the graph
	 * 
	 * @return the total of edges
	 */
	public int getTotalEdges() {
		int count = 0;
		for (Entry<Plotable, List<Adjacency>> entry : list.entrySet()) {
			count += entry.getValue().size();
		}
		return count;
	}

	/**
	 * gets the vertex corresponding to the given label
	 * 
	 * @param label the given label
	 * @return the vertex
	 */
	public Plotable getVertex(String label) {
		for (Entry<Plotable, List<Adjacency>> valueSet : list.entrySet()) {
			if (valueSet.getKey().getLabel().equals(label)) {
				return valueSet.getKey();
			}
		}
		return null;
	}

	@Deprecated
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

	/**
	 * gets adjacency between the origin and destiny
	 * 
	 * @param origin  the vertex of origin
	 * @param destiny the vertex of destiny
	 * @return the adjacency
	 */
	public Adjacency getAdjacency(Plotable origin, Plotable destiny) {
		List<Adjacency> adjacencies = list.get(origin);
		for (Adjacency adj : adjacencies) {
			if (adj.getPlotable().equals(destiny)) {
				return adj;
			}
		}
		return null;
	}

	/**
	 * Calculates the rate of entry of all vertexes, and stores it in a
	 * LinkedHashMap
	 * 
	 * @param limit the size of the returned, sorted map, -1 to have no limit
	 * @return a map sorted from the biggest sum to the lowest
	 */
	public Map<Plotable, Integer> getSumOfReferences(int limit) {
		Map<Plotable, Integer> aux = new LinkedHashMap<>();
		for (Entry<Plotable, List<Adjacency>> entry : list.entrySet()) {
			for (Adjacency adj : entry.getValue()) {
				if (aux.get(adj.getPlotable()) != null) {
					int auxInt = aux.get(adj.getPlotable());
//					auxInt += adj.getWeight();
					auxInt += 1;
					aux.put(adj.getPlotable(), auxInt);
				} else {
//					aux.put(adj.getPlotable(), adj.getWeight().intValue());
					aux.put(adj.getPlotable(), 1);
				}
			}
		}
		return sort(aux, limit);
	}

	/**
	 * Calculates the sum of the rate of exit, including the weight, of every edge,
	 * and stores it in a LinkedHashMap
	 * 
	 * @param limit the size of the returned, sorted map, -1 to have no limit
	 * @return a map sorted from the biggest sum to the lowest
	 */
	public Map<Plotable, Integer> getSumOfWeightedEdges(int limit) {
		Map<Plotable, Integer> aux = new LinkedHashMap<>();
		for (Entry<Plotable, List<Adjacency>> entry : list.entrySet()) {
			int count = 0;
			for (Adjacency adj : entry.getValue()) {
				count += adj.getWeight();
			}
			aux.put(entry.getKey(), count);
		}
		return sort(aux, limit);
	}

	/**
	 * Calculates the total of edges (w/o weight) of every vertex
	 * 
	 * @param limit the size of the returned, sorted map, -1 to have no limit
	 * @return a map sorted from the biggest sum to the lowest
	 */
	public Map<Plotable, Integer> getSumOfEdges(int limit) {
		Map<Plotable, Integer> aux = new LinkedHashMap<>();
		for (Entry<Plotable, List<Adjacency>> entry : list.entrySet()) {
			aux.put(entry.getKey(), entry.getValue().size());
		}
		return sort(aux, limit);
	}

	/**
	 * Sorts a given Map
	 * 
	 * @param list  The map, it must be composed of Plotables as keys and Integers
	 *              as values
	 * @param limit The limit will limit how many entries will be returned, -1 to
	 *              have no limit
	 * @return a LinkedHashMap sorted by the value of each entry, with the specified
	 *         limit
	 */
	public Map<Plotable, Integer> sort(Map<Plotable, Integer> list, int limit) {
		if (limit <= -1) {
			limit = Integer.MAX_VALUE;
		}
		return list.entrySet().stream()
				// Entry is a nested class from Map, heres the ref:
				.sorted(Map.Entry.<Plotable, Integer>comparingByValue().reversed()).limit(limit)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	/**
	 * Gets the list of adjacent vertexes from chosen string label
	 * 
	 * @param label The label
	 * @return A list of adjacent vertexes
	 */
	public List<Adjacency> getListOfAdjacency(String label) {
		return list.get(getVertex(label));
	}

	/**
	 * Gets the list of adjacent vertexes from chosen Plotable
	 * 
	 * @param label The object
	 * @return A list of adjacent vertexes
	 */
	public List<Adjacency> getListOfAdjacency(Plotable label) {
		return list.get(label);
	}

	/**
	 * Gets every connection that given vertex has, within the given layer limit
	 * 
	 * @param startLabel where the search will start
	 * @param limit      the layer where the search will end
	 * @return a list of vertexes that have some kind of adjacency with the start
	 *         vertex
	 */
	public List<Plotable> layeredListing(String startLabel, int limit) {
		Plotable start = getVertex(startLabel);
		if (start == null) {
			System.err.println("LayeredListing ERROR: Start does not exist");
			return null;
		}
		Set<Plotable> visited = new HashSet<>();
		Queue<Plotable> queue = new LinkedList<>();
		List<Plotable> path = new ArrayList<>();
		int currentLayerSize = 0;
		int atLayer = -1;

		queue.add(start);
		while (queue.size() > 0) {
			Plotable first = queue.remove();
			currentLayerSize--;
			if (!visited.contains(first)) {
				visited.add(first);
				List<Adjacency> adjacencyList = list.get(first);
				if (atLayer + 1 != limit) {
					for (Adjacency adjacency : adjacencyList) {
						queue.add(adjacency.getPlotable());
					}
				} else {
					path.add(first);
				}
				if (currentLayerSize <= 0) {
					currentLayerSize = queue.size();
					if (atLayer == limit) {
						return path;
					}
					atLayer++;
				}
			}
		}
		return path;
	}

	/**
	 * BreadthSearch implementation, gets path between the start and destiny
	 * (disregarding weight)
	 * 
	 * @param startLabel   the starting vertex
	 * @param destinyLabel the end vertex
	 * @return the path between the start and destiny
	 */
	public List<Plotable> breadthSearch(String startLabel, String destinyLabel) {
		Plotable start = getVertex(startLabel);
		Plotable destiny = getVertex(destinyLabel);
		return breadthSearch(start, destiny);
	}

	/**
	 * BreadthSearch implementation, gets path between the start and destiny
	 * (disregarding weight)
	 * 
	 * @param start   the starting vertex
	 * @param destiny the end vertex
	 * @return the path between the start and destiny
	 */
	public List<Plotable> breadthSearch(Plotable start, Plotable destiny) {
		if (start == null || destiny == null) {
			System.err.println("BreadthSearch ERROR: Start or Destiny does not exist");
			return null;
		}
		Set<Plotable> visited = new HashSet<>();
		Queue<Plotable> queue = new LinkedList<>();
		List<Plotable> path = new ArrayList<>();
		if (!start.equals(destiny)) {
			queue.add(start);
			while (queue.size() > 0) {
				Plotable first = queue.remove();
				if (!visited.contains(first)) {
					visited.add(first);
					path.add(first);
					if (first.equals(destiny)) {
						return path;
					}
					List<Adjacency> adjacencyList = list.get(first);
					for (Adjacency adjacency : adjacencyList) {
						queue.add(adjacency.getPlotable());
					}
				}
			}
		}
		return null;
	}

	/**
	 * Implementation of depth search, gets the path between the start and destiny
	 * (disregarding weight)
	 * 
	 * @param startLabel   the starting vertex
	 * @param destinyLabel the end vertex
	 * @return the path between the start and destiny
	 */
	public List<Plotable> depthSearch(String startLabel, String destinyLabel) {
		Plotable start = getVertex(startLabel);
		Plotable destiny = getVertex(destinyLabel);
		if (start == null || destiny == null) {
			System.err.println("DepthSearch ERROR: Start or Destiny does not exist");
			return null;
		}
		Set<Plotable> visited = new HashSet<>();
		if (!start.equals(destiny)) {
			visited.add(start);
			for (Adjacency adj : getListOfAdjacency(start)) {
				List<Plotable> aux = recursiveDepthSearch(visited, adj, destiny);
				if (aux != null) {
					aux.add(start);
					Collections.reverse(aux);
					return aux;
				}
			}
			return null;
		} else {
			List<Plotable> aux = new ArrayList<>();
			aux.add(destiny);
			aux.add(start);
			return aux;
		}
	}

	/**
	 * recursive depth search
	 * 
	 * @param visited    a map of visited vertexes
	 * @param currentAdj the current adjacency
	 * @param destiny    the vertex where we want to reach
	 * @return a list of plotable as the path taken to reach the destination, null
	 *         if it cant be reached
	 */
	private List<Plotable> recursiveDepthSearch(Set<Plotable> visited, Adjacency currentAdj, Plotable destiny) {
		if (!visited.contains(currentAdj.getPlotable())) {
			visited.add(currentAdj.getPlotable());
			if (currentAdj.getPlotable().getLabel().equals(destiny.getLabel())) {
				List<Plotable> aux = new ArrayList<Plotable>();
				aux.add(currentAdj.getPlotable());
				return aux;
			}
			for (Adjacency newAdj : list.get(currentAdj.getPlotable())) {
				List<Plotable> aux = recursiveDepthSearch(visited, newAdj, destiny);
				if (aux != null) {
					aux.add(currentAdj.getPlotable());
					return aux;
				}
			}
		}
		return null;
	}

	/**
	 * Prints a given map
	 * 
	 * @param list the map
	 */
	public void print(Map<Plotable, Integer> list) {
		int count = 0;
		for (Entry<Plotable, Integer> valueSet : list.entrySet()) {
			Plotable key = valueSet.getKey();
			int value = valueSet.getValue();
			System.out.printf("| (%02d) %" + 1 + "s || ", count, key.getLabel());
			System.out.printf("%2d | ", value);
			count++;
			System.out.println();
		}
	}

	/**
	 * A nested class, the only use for this class is to store info about the next step to be taken by the dijkstra algorithm
	 * @author kovalski
	 */
	private record VertexInfo(Double distance, Plotable previousVertex) {}

	/**
	 * Gets the shortest path between the starting and ending vertexes
	 * @param startLabel the starting vertex
	 * @param destinyLabel the ending vertex
	 * @return null if there is no connection between these two vertexes, else return a list of plotables with the shortest path
	 */
	public List<Plotable> shortestPath(String startLabel, String destinyLabel) {
		Plotable start = getVertex(startLabel);
		Plotable destiny = getVertex(destinyLabel);

		if (breadthSearch(start, destiny) == null) {
			return null;
		}

		Set<Plotable> visited = new HashSet<>();
		Map<Plotable, VertexInfo> infoTable = new LinkedHashMap<>();
		infoTable.put(start, new VertexInfo(0.0, null));
		visited.add(start);

		infoTable = getPathInInfoTable(visited, infoTable, start, destiny);
		List<Plotable> path = new ArrayList<>();
		path.add(destiny);
		Plotable previous = infoTable.get(destiny).previousVertex();
		while (previous != null) {
			path.add(previous);
			previous = infoTable.get(previous).previousVertex();
		}

		return path;
	}

	private Map<Plotable, VertexInfo> getPathInInfoTable(Set<Plotable> visited, Map<Plotable, VertexInfo> infoTable,
			Plotable vertex, Plotable destiny) {
		while (vertex != destiny) {
			visited.add(vertex);
			List<Adjacency> adjacents = getListOfAdjacency(vertex);
			for (Adjacency adjacency : adjacents) {
				Plotable adjacentVertex = adjacency.getPlotable();
				double newDist = Math.pow(adjacency.getWeight() + infoTable.get(vertex).distance(), 1);
				if (!infoTable.containsKey(adjacentVertex)) {
					infoTable.put(adjacentVertex, new VertexInfo(Double.MAX_VALUE, vertex));
				}
				if (infoTable.get(adjacentVertex).distance() > newDist) {
					infoTable.put(adjacentVertex, new VertexInfo(newDist, vertex));
				}
			}
			double lowest = Double.MAX_VALUE;
			for (Entry<Plotable, VertexInfo> info : infoTable.entrySet()) {
				if (!visited.contains(info.getKey()) && info.getValue().distance() < lowest) {
					lowest = info.getValue().distance();
					vertex = info.getKey();
				}
			}
		}
		return infoTable;
	}

	/**
	 * prints Adjacency List
	 * <p>
	 * messes up the terminal when printing lots of adjacencies
	 */
	@Deprecated
	public void printList() {
		Map<Plotable, List<Adjacency>> aux = list.entrySet().stream()
				.sorted(Map.Entry
						.<Plotable, List<Adjacency>>comparingByValue((a, b) -> Integer.compare(a.size(), a.size()))
						.reversed())
				.limit(10)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		int count = 0;
		for (Entry<Plotable, List<Adjacency>> valueSet : aux.entrySet()) {
			Plotable key = valueSet.getKey();
			List<Adjacency> innerList = valueSet.getValue();
			Collections.sort(innerList);
			System.out.printf("| (%02d) %" + 1 + "s || ", count, key.getLabel());
			for (Adjacency adjacency : innerList) {
				System.out.printf("%" + 1 + "s | ", adjacency);
			}
			count++;
			System.out.println();
		}
	}

}