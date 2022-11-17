package app;

import java.util.List;

import entities.AdjacencyList;
import interfaces.Plotable;
import utils.CSVManager;

public class Main {

	public static void main(String[] args) {
		
		AdjacencyList adj = AdjacencyList.generateRandomGraph(150, 140, false);
		adj.printList(adj.getTotalVertexes());
		System.out.println(adj.getTotalVertexes());
		System.out.println(adj.isConnected());
		System.out.println(adj.isEulerian());
		System.out.println(adj.isCyclic());

	}

	public static void printList(List<Plotable> list) {
		if (list != null) {
			System.out.print("[");
			for (Plotable p : list) {
				System.out.print(p.getLabel());
				if (list.indexOf(p) != list.size() - 1) {
					System.out.print(">");
				}
			}
			System.out.print("]");
		} else {
			System.out.println("There's no path, nor any vertex");
		}
	}

}