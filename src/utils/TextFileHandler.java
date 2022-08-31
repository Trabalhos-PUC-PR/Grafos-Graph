package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TextFileHandler {

	String path;
	BufferedReader reader;
	
	public TextFileHandler(String path) {
		this.path = path;
		fileReaderFactory(path);
	}
	
	private void fileReaderFactory(String path) {
		File file = new File(path);
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			this.reader = br;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String readLine() {
		String line = "";
        try {
			if ((line = reader.readLine()) != null) {
				return line;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public String readLine(int index) {
		fileReaderFactory(path);
		String aux = readLine();
        for(int i = 0 ; i < index; i++) {
        	aux = readLine();
        }
        return aux;
	}
	
}
