package aoc2021;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day7 implements AoCTask {
    ArrayList<Integer> positions;

    public static void main(String[] args) {
        Executor ex = new Executor();

        AoCTask small = new Day7();
        AoCTask big = new Day7();

        ex.execute_timed(small, "inputs/input_s_7", 0);
        System.out.println();
        ex.execute_timed(big, "inputs/input_7", 0);
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
