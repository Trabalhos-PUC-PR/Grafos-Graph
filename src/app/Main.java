package app;

import java.util.List;
import entities.AdjacencyList;
import interfaces.Plotable;
import utils.FileNavigator;

public class Main {

	public static void main(String[] args) {
		AdjacencyList adj = new AdjacencyList();

		// change this path to the path of the folder that has the desired dataset
		FileNavigator fn = new FileNavigator("./mockData");

		adj = fn.listGraph();
		
		adj.printList();
		
		adj.kruskalMst();
		
		
//		System.out.println("\nENVIADOS (top 20 que mais enviaram emails considerando repetições): ");
//		adj.print(adj.getSumOfEdges(20));
//		System.out.println("\nREFERENCIAS (top 20 que mais receberam emails): ");
//		adj.print(adj.getSumOfReferences(20));
//
//		System.out.println("Vertexes: " + adj.getTotalVertexes());
//		System.out.println("Edges: " + adj.getTotalEdges());
//
////		List<Plotable> path1 = adj.depthSearch("drew.fossum@enron.com", "hestes@lynchchappell.com");
////		List<Plotable> path2 = adj.breadthSearch("drew.fossum@enron.com", "hestes@lynchchappell.com");
////		List<Plotable> path3 = adj.layeredListing("drew.fossum@enron.com", 2);
////		List<Plotable> path4 = adj.shortestPath("drew.fossum@enron.com", "d.smith@enron.com");
//
////		searches with the 2nd mock dataset (look for the graph image for more details)
//		List<Plotable> path1 = adj.depthSearch("E", "D");
//		List<Plotable> path2 = adj.breadthSearch("E", "D");
//		List<Plotable> path3 = adj.layeredListing("B", 2);
//		List<Plotable> path4 = adj.shortestPath("A", "C");
//
//		System.out.println("\nBusca por Profundidade:");
//		printList(path1);
//
//		System.out.println("\nBusca por Largura:");
//		printList(path2);
//
//		System.out.println("\nListagem por Camada:");
//		printList(path3);
//
//		System.out.println("\nMenor Caminho:");
//		printList(path4);
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