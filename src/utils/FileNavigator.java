package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileNavigator {

	private File file;
	private int total;
	private int missingTo;
	private int missingFrom;

	public FileNavigator(String path) {
		if (!new File(path).exists()) {
			throw new RuntimeException(String.format("Invalid path [%s]", path));
		}
		this.total = 0;
		this.file = new File(path);
	}

	public void recursiveList() {
		long startTime = System.currentTimeMillis();
		recursiveList(file.listFiles());
		long endTime = System.currentTimeMillis();
		int millis = (int) (endTime - startTime);
		int seconds = millis / 1000;
		millis = millis % 1000;
		System.out.printf("%d succeded, (from:%d, to:%d) failed.\n%d.%03ds elapsed", total, missingFrom, missingTo,
				seconds, millis);
	}

	private void recursiveList(File[] files) {
		for (File file : files) {
			if (file.isDirectory()) {
				if (file.getName().equals("sent")) {
					extractFromToOfEmail(file.listFiles());
				}
				recursiveList(file.listFiles());
			}
		}
	}

	public void extractFromToOfEmail(File[] files) {
		for (File file : files) {
			extractFromToOfEmail(file);
		}
	}

	private void extractFromToOfEmail(File file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String text = br.readLine();
			while (text != null) {

				String[] splitFrom = text.split(" ");
				if (splitFrom.length == 2 && splitFrom[0].equals("From:")) {
					String[] splitTo = br.readLine().split(" ");
					if (splitTo.length >= 2 && splitTo[0].equals("To:")) {

						String sender = splitFrom[1];
						List<String> recipients = new ArrayList<>();
						
						for (int i = 1; i < splitTo.length; i++) {
							recipients.add(splitTo[i].replaceAll(",", ""));
						}
						if (splitTo.length > 2) {
							String[] extraLines = br.readLine().split(" ");
							while (!extraLines[0].equals("Subject:")) {
								for (int i = 0; i < extraLines.length; i++) {
									recipients.add(extraLines[i].replaceAll(",", "").replaceAll("\t", ""));
								}
								extraLines = br.readLine().split(" ");
							}
						}
						
						

						total++;
						return;
					} else {
						missingTo++;
						System.out.printf("Error on 'To:' [%s]\n", file.getPath());
						return;
					}
				}
				text = br.readLine();
			}
			System.out.printf("Error on 'From:' [%s]\n", file.getPath());
			missingFrom++;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
