package aoc2021;

import java.util.Scanner;

public class Day1 implements AoCTask {
	int[] input;

	public static void main(String[] args) {
		Executor ex = new Executor();

		AoCTask small = new Day1();
		AoCTask big = new Day1();

		ex.execute_timed(small, "inputs/input_s_1", 10);
		System.out.println();
		ex.execute_timed(big, "inputs/input_1", 2000);
	}

	@Override
	public void readInput(Scanner src, int n) {
		this.input = new int[n];
		for (int i = 0; i < n; i++) {
			input[i] = src.nextInt();
		}
	}

	@Override
	public String task1() {
		int c = 0;
		for (int i = 0; i < input.length - 1; i++) {
			if (input[i] <= input[i + 1]) {
				c++;
			}
		}
		return "" + c;
	}

	@Override
	public String task2() {
		int[] sums = new int[input.length - 2];
		for (int i = 0; i < input.length - 2; i++) {
			sums[i] = input[i] + input[i + 1] + input[i + 2];
		}

		int c = 0;
		for (int i = 0; i < sums.length - 1; i++) {
			if (sums[i] < sums[i + 1]) {
				c++;
			}
		}

		return "" + c;
	}

}
