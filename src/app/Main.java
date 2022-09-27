package app;

import java.util.ArrayList;
import java.util.Collections;
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
		adj.print(adj.getSumOfWeightedEdges(20));
		System.out.println("\nREFERENCIAS (top 20 que mais receberam emails): ");
		adj.print(adj.getSumOfReferences(20));
		
		System.out.println("Vertexes: "+ adj.getTotalVertexes());
		System.out.println("Edges: "+adj.getTotalEdges());
		
		List<Plotable> path = adj.depthSearch("darron.giron@enron.com", "martha.benner@enron.com");
		
		if(path != null) {
			Collections.reverse(path);
			System.out.println("darron.giron@enron.com");
			for(Plotable p : path) {
				System.out.println(p.getLabel());
			}
		} else {
			System.out.println("Theres no path :(");
		}
		System.out.println();
	}

}
