package app;

import java.util.ArrayList;
import java.util.List;
import entities.AdjacencyList;
import interfaces.Plotable;
import utils.DynamicFrame;
import utils.FileNavigator;

public class Main {

	public static void main(String[] args) {

		DynamicFrame display = new DynamicFrame();
		ArrayList<String> mainMenu = new ArrayList<>();
		mainMenu.add("Select an option:");
		mainMenu.add("1. Print graph;");
		mainMenu.add("2. Config program;");
		mainMenu.add("3. Clear the screen;");
		mainMenu.add("4. Exit the program;");
		display.printFrame(mainMenu);

		AdjacencyList adj = new AdjacencyList();
		FileNavigator fn = new FileNavigator("./fullData");
		
		adj = fn.listGraph();
		
		System.out.println("\nENVIADOS (top 20 que mais enviaram emails considerando repetições): ");
		adj.print(adj.getSumOfEdges(20));
		System.out.println("\nREFERENCIAS (top 20 que mais receberam emails): ");
		adj.print(adj.getSumOfReferences(20));
		
		System.out.println("Vertexes: "+ adj.getTotalVertexes());
		System.out.println("Edges: "+adj.getTotalEdges());

		List<Plotable> path1 = adj.depthSearch("drew.fossum@enron.com", "hestes@lynchchappell.com");
		List<Plotable> path2 = adj.breadthSearch("drew.fossum@enron.com", "hestes@lynchchappell.com");
		List<Plotable> path3 = adj.layeredListing("drew.fossum@enron.com", 2);
		List<Plotable> path4 = adj.shortestPath("drew.fossum@enron.com", "hestes@lynchchappell.com");
		
		System.out.println("\nDepth:");
		printList(path1);

		System.out.println("\nBreadth:");
		printList(path2);
		
		System.out.println("\nLayered Listing:");
		printList(path3);
		
		System.out.println("\nShortest:");
		printList(path4);
	}
	
	public static void printList(List<Plotable> list) {
		if(list != null) {
			for(Plotable p : list) {
				System.out.println(p.getLabel());
			}
		} else {
			System.out.println("No");
		}
	}

}
