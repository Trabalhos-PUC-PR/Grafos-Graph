package utils;

import java.util.ArrayList;

public class DynamicFrame {
	private int frameMaxWidth;
	private int textOrientation;
	private int footerHeight;
	private int filler;

	/**
	 * Frame that surrounds given array list of strings
	 */
	public DynamicFrame() {
		this.frameMaxWidth = 35;
		this.textOrientation = -1;
		this.footerHeight = 0;
		this.filler = 7;
	}

	private void printBorderColumn() {
		System.out.print("+");
		for (int i = 0; i < frameMaxWidth; i++) {
			System.out.print("-");
		}
		System.out.println("+");
	}

	private void printBorderLine(int width) {
		System.out.print("+");
		for (int i = 0; i < width + filler + 2; i++) {
			System.out.print("-");
		}
		System.out.println("+");
	}

	/**
	 * sets frame max width, if param is 0, it does nothing
	 * 
	 * @param maxWidth
	 */
	public void setMaxWidth(int maxWidth) {
		if (maxWidth == 0) {
			return;
		}
		this.frameMaxWidth = maxWidth;
	}

	public void setTextToRight() {
		textOrientation = +1;
	}

	public void setTextToLeft() {
		textOrientation = -1;
	}

	/**
	 * prints frame with any text inside the contents arrayList, clips strings
	 * bigger than 35 characters
	 * 
	 * @param contents
	 */
	public void printFrame(ArrayList<String> contents) {
		int height = contents.size() + footerHeight;
		int width = 1;

		// defines what is the length of the biggest string
		for (int i = 0; i < height - footerHeight; i++) {
			int stringLength = contents.get(i).length();
			if (stringLength > width) {
				if (stringLength <= frameMaxWidth) {
					width = stringLength;
				} else {
					width = frameMaxWidth;
				}
			}
		}

		// checks if there is any string that is bigger than the max frame width
		// cuts said string to fit the frame's configuration
		for (int i = 0; i < contents.size(); i++) {
			if (contents.get(i).length() > frameMaxWidth) {
				String string = contents.get(i);
				contents.set(i, string.substring(0, frameMaxWidth));
				string = string.substring(frameMaxWidth, string.length());
				contents.add(i + 1, string);
			}
		}

		height = contents.size() + footerHeight;

		printBorderLine(width);

		for (int i = 0; i < height; i++) {
			String textContent;
			if (i >= contents.size()) {
				textContent = "";
			} else {
				textContent = contents.get(i);
			}
			System.out.printf("| %" + textOrientation * (width + filler) + "s |", textContent);
			System.out.println();
		}

		printBorderLine(width);
	}

	/**
	 * prints frame with the specified height, and with no text
	 * 
	 * @param height
	 */
	public void printFrame(int height) {
		printBorderColumn();

		for (int i = 0; i < height; i++) {
			System.out.printf("| %" + textOrientation * (frameMaxWidth + filler - 2) + "s |", "");
			System.out.println();
		}

		printBorderColumn();
	}

}
