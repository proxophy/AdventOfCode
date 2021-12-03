package aoc2021;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Day1 implements AoCTask {
	int[] input;

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input_s");
		Scanner src = new Scanner(file);
		Day1 small = new Day1();
		small.readInput(src, 10);
		System.out.println(small.task2());

		File file2 = new File("input");
		Scanner scr2 = new Scanner(file2);
		Day1 big = new Day1();
		big.readInput(scr2, 2000);
		System.out.println(big.task2());
	}

	@Override
	public void readInput(Scanner src, int n) {
		this.input = new int[n];
		for (int i = 0; i < n; i++) {
			input[i] = src.nextInt();
		}
	}

	@Override
	public int task1() {
		int c = 0;
		for (int i = 0; i < input.length - 1; i++) {
			if (input[i] <= input[i + 1]) {
				c++;
			}
		}
		return c;
	}

	@Override
	public int task2() {
		int[] sums = new int[input.length - 2];
		for (int i = 0; i < input.length - 2; i++) {
			sums[i] = input[i] + input[i + 1] + input[i + 2];
		}

		System.out.println(Arrays.toString(sums));

		int c = 0;
		for (int i = 0; i < sums.length - 1; i++) {
			if (sums[i] < sums[i + 1]) {
				c++;
			}
		}

		// TODO Auto-generated method stub
		return c;
	}

}
