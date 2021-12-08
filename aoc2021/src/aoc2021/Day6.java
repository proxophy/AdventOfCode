package aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Day6 implements AoCTask {
    ArrayList<Fish> fish = new ArrayList<Fish>();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scr;

        File file = new File("input_s");
        scr = new Scanner(file);
        Day6 small = new Day6();
        small.readInput(scr, 0);
        System.out.println(small.task2());
        scr.close();

        File file2 = new File("input");
        scr = new Scanner(file2);
        Day6 big = new Day6();
        big.readInput(scr, 0);
        scr.close();
        System.out.println(big.task2());
    }

    public void readInput(Scanner scr, int n) {
        String line = scr.nextLine();
        String[] args = line.split(",");
        for (String s : args) {
            int val = Integer.parseInt(s);
            fish.add(new Fish(val));
        }

    }

    public String task1() {
        for (int i = 1; i <= 80; i++) {
            ArrayList<Fish> new_fish = new ArrayList<Fish>();
            new_fish.addAll(fish);
            for (Fish f : fish) {
                if (f.haveBaby()) { // new fish
                    new_fish.add(new Fish(8));
                }

                f.set();
            }
            fish = new_fish;

        }

        return "" + fish.size();
    }

    public String task2() {
        // much faster than task 1 version, thanks to DP

        // nums[i] describes how many fish have internal timer i
        long[] nums = new long[9];
        // initialize
        for (Fish f : fish) {
            nums[f.dleft]++;
        }
        // simulate
        for (int j = 1; j <= 256; j++) {
            long[] old_nums = Arrays.copyOf(nums, nums.length);
            for (int i = 0; i < 8; i++) {
                nums[i] = old_nums[i + 1];
            }
            nums[6] += old_nums[0];
            nums[8] = old_nums[0];
        }

        long sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += nums[i];
        }

        return "" + sum;
    }

}

class Fish {
    int dleft;

    Fish(int l) {
        this.dleft = l;
    }

    @Override
    public String toString() {
        return "" + dleft;
    }

    void set() {
        if (dleft == 0) {
            dleft = 6;
        } else {
            dleft--;
        }
    }

    boolean haveBaby() {
        return dleft == 0;
    }
}