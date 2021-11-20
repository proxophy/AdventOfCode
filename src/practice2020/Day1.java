package practice2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Day1 {
	public static void main(String args[]) throws FileNotFoundException {
		int[] nums = readIn(200);
		System.out.println(Arrays.toString(nums));

//		outer: for (int i = 0; i < 200 - 1; i++) {
//			for (int j = i; j < 200; j++) {
//				if (nums[i] + nums[j] == 2020) {
//					System.out.println("found: " + nums[i] + " " + nums[j]);
//					System.out.println(nums[i] * nums[j]);
//					break outer;
//				}
//			}
//		}

		outer2: for (int i = 0; i < 200 - 2; i++) {
			for (int j = i+1; j < 200 -1; j++) {
				for (int k = j+1; k < 200; k++) {
					if (nums[i] + nums[j] + nums[k]== 2020) {
						System.out.println(nums[i] * nums[j] * nums[k]);
						break outer2;
					}
				}
			}
		}

	}

	public static int[] readIn(int n) throws FileNotFoundException {
		int[] numbers = new int[n];
		File myfile = new File("src/input");
		Scanner sc = new Scanner(myfile);

		for (int i = 0; i < n; i++) {
			numbers[i] = sc.nextInt();
		}
		sc.close();
		return numbers;
	}
}
