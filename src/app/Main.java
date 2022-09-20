package app;

import java.util.ArrayList;

import entities.AdjacencyList;
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
		fn.listGraph(adj);
		adj.print();
	}

}
