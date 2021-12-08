package aoc2021;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Day7 implements AoCTask {
    ArrayList<Integer> positions;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scr;

        File file = new File("input_s");
        scr = new Scanner(file);
        Day7 small = new Day7();
        small.readInput(scr, 0);
        System.out.println(small.task2());
        scr.close();

        File file2 = new File("input");
        scr = new Scanner(file2);
        Day7 big = new Day7();
        big.readInput(scr, 0);
        scr.close();
        System.out.println(big.task2());
    }

    @Override
    public void readInput(Scanner scr, int n) {
        positions = new ArrayList<Integer>();
        String[] inp = scr.nextLine().split(",");
        for (String e : inp) {
            positions.add(Integer.parseInt(e));
        }
        Collections.sort(positions);
    }

    @Override
    public String task1() {
        long[] fuel = new long[positions.get(positions.size() - 1) + 1];
        for (int i = 0; i < fuel.length; i++) {
            fuel[i] = 0;
            for (int pos : positions) {
                fuel[i] += Math.abs(pos - i);
            }
        }
        long min = fuel[0];
        for (int i = 0; i < fuel.length; i++) {
            min = Math.min(min, fuel[i]);
        }

        return "" + min;
    }

    @Override
    public String task2() {
        long[] fuel = new long[positions.get(positions.size() - 1) + 1];
        for (int i = 0; i < fuel.length; i++) {
            fuel[i] = 0;
            for (int pos : positions) {
                fuel[i] += Math.abs(pos - i) * (Math.abs(pos - i) + 1) / 2; // Euler's formula
            }
        }
        long min = fuel[0];
        for (int i = 0; i < fuel.length; i++) {
            min = Math.min(min, fuel[i]);
        }

        return "" + min;
    }

}
