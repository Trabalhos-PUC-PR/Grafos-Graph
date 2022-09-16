package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileNavigator {

	private File file;
	private int total;
	private int missing;

	public FileNavigator(String path) {
		if (!new File(path).exists()) {
			throw new RuntimeException(String.format("Invalid path [%s]", path));
		}
		this.total = 0;
		this.file = new File(path);
	}

	public void recursiveList() {
		recursiveList(file.listFiles(), 0);
		System.out.printf("%d succeded, %d failed", total, missing);
	}

	private void recursiveList(File[] files, int layer) {
		layer++;
		for (File file : files) {
			if (file.isDirectory()) {
				if (file.getName().equals("sent")) {
					extractFromToOfEmail(file.listFiles());
				}
				recursiveList(file.listFiles(), layer);
			}
		}
	}

	public void extractFromToOfEmail(File[] files) {
		for(File file : files) {
			extractFromToOfEmail(file);
		}
	}
	
	private void extractFromToOfEmail(File file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String text = br.readLine();
			while(text != null) {
				String[] toSplit = text.split(" ");
				if(toSplit.length == 2 && toSplit[0].equals("To:")) {
					System.out.println(toSplit[1]);
					total++;
					return;
				}
				text = br.readLine();
			}
			System.out.printf("%s does not have a [To: ] string!\n",file.getPath());
			missing++;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
