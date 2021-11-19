package practice2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static void readIn(File f) throws FileNotFoundException {
		Scanner sc = new Scanner(f);
		for (int i = 0; i < 1000; i++) {

		}

	}

}

class Password {
	int min;
	int max;
	char let;
	String str;

	public Password(int min, int max, char let, String str) {
		this.min = min;
		this.max = max;
		this.let = let;
		this.str = str;
	}
}
