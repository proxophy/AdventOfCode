package aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day11 implements AoCTask {
    int[][] grid;
    int[][] grid2; // copy of original grid
    int n;

    public static void main(String[] args) {
        Executor ex = new Executor();

        AoCTask small = new Day11();
        AoCTask big = new Day11();

        ex.execute_timed(small, "inputs/input_s_11", 10);
        System.out.println();
        ex.execute_timed(big, "inputs/input_11", 10);
    }

    @Override
    public void readInput(Scanner scr, int n) {
        this.n = n;
        grid = new int[this.n][];
        for (int k = 0; k < n; k++) {
            String[] elms = scr.nextLine().split("");
            int[] line = new int[elms.length];
            for (int i = 0; i < elms.length; i++)
                line[i] = Integer.parseInt(elms[i]);
            grid[k] = line;
        }

        // copy for task 2
        grid2 = new int[grid.length][];
        for (int i = 0; i < grid.length; i++)
            grid2[i] = grid[i].clone();

    }

    @Override
    public String task1() {
        int c = 0;
        for (int i = 1; i <= 100; i++) {
            c += simulateStep();
        }
        grid = grid2; // setting back because task 2 shouldn't start after step 100

        return "" + c;
    }

    public int simulateStep() {
        boolean[][] flashing = new boolean[n][n];

        ArrayList<Pos> flashes = new ArrayList<Pos>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j]++;
                if (grid[i][j] > 9) {
                    flashing[i][j] = true;
                    flashes.add(new Pos(i, j));
                }
            }
        }

        while (flashes.size() > 0) {
            Pos p = flashes.remove(0);
            flash(p, flashing, flashes);
        }

        int c = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (flashing[i][j]) {
                    c++;
                }
            }
        }
        return c;
    }

    public void flash(Pos p, boolean[][] flashing, ArrayList<Pos> flashes) {
        // adjacent on left side
        if (p.j > 0) {
            if (p.i > 0) {
                // upper
                inc(p.i - 1, p.j - 1, flashing, flashes);
            }
            // middle
            inc(p.i, p.j - 1, flashing, flashes);
            if (p.i < this.n - 1) {
                // lower
                inc(p.i + 1, p.j - 1, flashing, flashes);
            }
        }
        // adjacent on same side
        if (p.i > 0) {
            // upper
            inc(p.i - 1, p.j, flashing, flashes);
        }
        if (p.i < this.n - 1) {
            // lower
            inc(p.i + 1, p.j, flashing, flashes);
        }
        // adjacent on right side
        if (p.j < this.n - 1) {
            if (p.i > 0) {
                // upper
                inc(p.i - 1, p.j + 1, flashing, flashes);
            }
            // middle
            inc(p.i, p.j + 1, flashing, flashes);
            if (p.i < this.n - 1) {
                // lower
                inc(p.i + 1, p.j + 1, flashing, flashes);
            }
        }
        flashes.remove(p);
        grid[p.i][p.j] = 0;
    }

    // inc val for a pos if not flashing, if now flashing, then add to list
    public void inc(int i, int j, boolean[][] flashing, ArrayList<Pos> flashes) {
        if (flashing[i][j]) {
            return;
        }
        grid[i][j]++;
        if (grid[i][j] > 9) {
            flashing[i][j] = true;
            flashes.add(new Pos(i, j));
        }
    }

    @Override
    public String task2() {
        int i = 0;
        do {
            simulateStep();
            i++;
        } while (!allSame());
        return "" + i;
    }

    public boolean allSame() {
        int a = grid[0][0];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != a) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void print2d(Object[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(Arrays.toString(arr[i]));
        }
    }

    public static void print2db(boolean[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(Arrays.toString(arr[i]));
        }
    }

    public class Pos {
        int i;
        int j;

        Pos(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString() {
            return "(" + i + ", " + j + ")";
        }
    }

}
