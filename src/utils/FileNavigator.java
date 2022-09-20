package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.AdjacencyList;
import entities.Email;

public class FileNavigator {

	private File file;
	private int total;
	private int missingTo;
	private int missingFrom;
	private AdjacencyList adj;

	public FileNavigator(String path) {
		if (!new File(path).exists()) {
			throw new RuntimeException(String.format("Invalid path [%s]", path));
		}
		this.total = 0;
		this.file = new File(path);
	}

	public void listGraph(AdjacencyList adj) {
		this.adj = adj;
		long startTime = System.currentTimeMillis();
		recursiveList(file.listFiles());
		long endTime = System.currentTimeMillis();
		
		int millis = (int) (endTime - startTime);
		int seconds = millis / 1000;
		millis = millis % 1000;
		double percentFail = (double) (missingFrom + missingTo) * 100 / total;
		
		System.out.printf("%d succeded, %.2f%% failed (from:%d, to:%d).\n%d.%03ds elapsed\n", 
				total, percentFail, missingFrom, missingTo, seconds, millis);
		
		return;
	}
	
	public void listGraph() {
		long startTime = System.currentTimeMillis();
		recursiveList(file.listFiles());
		long endTime = System.currentTimeMillis();
		
		int millis = (int) (endTime - startTime);
		int seconds = millis / 1000;
		millis = millis % 1000;
		double percentFail = (double) (missingFrom + missingTo) * 100 / total;
		
		System.out.printf("%d succeded, %.2f%% failed (from:%d, to:%d).\n%d.%03ds elapsed", 
				total, percentFail, missingFrom, missingTo, seconds, millis);
		
		return;
	}

	private void recursiveList(File[] files) {
		for (File file : files) {
			if (file.isDirectory()) {
				if (file.getName().equals("sent")) {
					for(File internalFile : file.listFiles()) {
						extractSenderAndRecipients(internalFile);
					}
				}
				recursiveList(file.listFiles());
			}
		}
	}
	
	private void extractSenderAndRecipients(File file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String text = br.readLine();
			while (text != null) {

				String[] splitFrom = text.split(" ");
				if (splitFrom.length == 2 && splitFrom[0].equals("From:")) {
					String[] splitTo = br.readLine().split(" ");
					if (splitTo.length >= 2 && splitTo[0].equals("To:")) {
						
						if(splitFrom[1].isBlank()) {
							System.err.printf("Error on 'From:' [%s]\n", file.getPath());
							missingFrom++;
							return;
						}
						
						Email sender = new Email(splitFrom[1]);
						List<Email> recipients = new ArrayList<>();

						for (int i = 1; i < splitTo.length; i++) {
							adj.addAdjacency(sender, new Email(splitTo[i].replaceAll(",", "")));
							recipients.add(new Email(splitTo[i].replaceAll(",", "")));
						}
						if (splitTo.length > 2) {
							String[] extraLines = br.readLine().split(" ");
							while (!extraLines[0].equals("Subject:")) {
								for (int i = 0; i < extraLines.length; i++) {
									Email newFromExtra = new Email(extraLines[i].replaceAll(",", "").replaceAll("\t", ""));
									adj.addAdjacency(sender, newFromExtra);
									recipients.add(newFromExtra);
								}
								extraLines = br.readLine().split(" ");
							}
						}
						total++;
						return;
					} else {
						missingTo++;
						System.err.printf("Error on 'To:' [%s]\n", file.getPath());
						return;
					}
				}
				text = br.readLine();
			}
			System.err.printf("Error on 'From:' [%s]\n", file.getPath());
			missingFrom++;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
