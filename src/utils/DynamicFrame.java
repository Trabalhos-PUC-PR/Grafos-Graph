package utils;

import java.util.ArrayList;

public class DynamicFrame {
	int frameMaxWidth;
	int signal;

	public DynamicFrame() {
		this.frameMaxWidth = 35;
		this.signal = -1;
	}

	/**
	 * sets frame max width, if param is 0, it does nothing
	 * @param maxWidth
	 */
	public void setMaxWidth(int maxWidth) {
		if(maxWidth == 0) {
			return;
		}
		this.frameMaxWidth = maxWidth;
	}
	
	public void setTextToLeft() {
		signal = +1;
	}
	
	public void setTextToRight() {
		signal = -1;
	}
	
	private void printBorderColumn() {
		System.out.print("+");
		for (int i = 0; i < frameMaxWidth; i++) {
			System.out.print("-");
		}
		System.out.println("+");
	}

	private void printBorderColumn(int width) {
		System.out.print("+");
		for (int i = 0; i < width + 2; i++) {
			System.out.print("-");
		}
		System.out.println("+");
	}

	/**
	 * prints frame with any text inside the contents arrayList, clips strings bigger than 35 characters
	 * @param contents
	 */
	public void printFrame(ArrayList<String> contents) {

		int height = contents.size() + 2;
		int width = Integer.MIN_VALUE;
		
		for (int i = 0; i < height - 2; i++) {
			int stringLength = contents.get(i).length();
			if (stringLength > width) {
				if (stringLength <= frameMaxWidth) {
					width = stringLength;
				}else {
					width = frameMaxWidth;					
				}
			}
		}

		for (int i = 0; i < contents.size(); i++) {
			if(contents.get(i).length() > frameMaxWidth) {
				String string = contents.get(i);
				contents.set(i, string.substring(0, frameMaxWidth));
				string = string.substring(frameMaxWidth, string.length());
				contents.add(i+1, string);
			}
		}

		if (width == Integer.MIN_VALUE) {
			width = 0;
		}
		
		height = contents.size() + 2;
		
		printBorderColumn(width);

		for (int i = 0; i < height; i++) {
			String textContent;
			if (i >= contents.size()) {
				textContent = "";
			} else {
				textContent = contents.get(i);
			}
			System.out.printf("| %" + signal*width + "s |", textContent);
			System.out.println();
		}

		printBorderColumn(width);
	}

	/**
	 * prints frame with the specified height, and with no text
	 * 
	 * @param height
	 */
	public void printFrame(int height) {
		printBorderColumn();

		for (int i = 0; i < height; i++) {
			System.out.printf("| %" + signal*(frameMaxWidth - 2) + "s |", "");
			System.out.println();
		}

		printBorderColumn();
	}

}
