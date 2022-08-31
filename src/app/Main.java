package app;

import java.util.ArrayList;
import java.util.Scanner;

import utils.DynamicFrame;
import utils.TextFileHandler;

public class Main {

	public static void main(String[] args) {

		DynamicFrame display = new DynamicFrame();
		ArrayList<String> mainMenu = new ArrayList<>();

		mainMenu.add("Select an option:");
		mainMenu.add("1. Print graph;");
		mainMenu.add("2. Config program;");
		mainMenu.add("3. Clear the screen;");
		mainMenu.add("4. Exit the program;");
		
		Scanner sc = new Scanner(System.in);

		while (true) {
			display.printFrame(mainMenu);
			System.out.print("Option: ");
			int option = Integer.parseInt(sc.nextLine());

			switch (option) {
			case 1:
				System.out.println("Not implemented yet!");
				break;
			case 2:
				TextFileHandler fh = new TextFileHandler("./data/teste.txt");
				System.out.println(fh.readLine(1));
				System.out.println(fh.readLine(0));
				System.out.println(fh.readLine(3));
				break;
			case 3:
				// thats 50 \n to simulate a clear command
				System.out.println(
						"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
						+ "\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
						+ "\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
						+ "\n\n\n\n\n\n\n");
				break;
			case 4:
				sc.close();
				return;
			}
		}
	}

}
