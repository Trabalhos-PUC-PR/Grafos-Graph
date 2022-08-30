package app;

import java.util.ArrayList;
import utils.DynamicFrame;

public class Main {

	public static void main(String[] args) {
		
			DynamicFrame display = new DynamicFrame();
			
			ArrayList<String> mainMenu = new ArrayList<>(); 
			
			display.setMaxWidth(50);
			
			mainMenu.add("Select an option:");
			mainMenu.add("1. Change config file");
			mainMenu.add("2. Print graph");

			display.printFrame(mainMenu);
		
	}

}
