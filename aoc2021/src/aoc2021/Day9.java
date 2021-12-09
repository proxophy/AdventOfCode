package aoc2021;

import java.util.Arrays;
import java.util.Scanner;

public class Day9 implements AoCTask {
    int[][] grid;
    int m;
    int n;
    int[][] basin;

    public static void main(String[] args) {
        Executor ex = new Executor();

        AoCTask small = new Day9();
        AoCTask big = new Day9();

        ex.execute_timed(small, "inputs/input_s_9", 5);
        System.out.println();
        ex.execute_timed(big, "inputs/input_9", 100); // Suddenly part 2 is wrong ._.
    }

    @Override
    public void readInput(Scanner scr, int n) {
        this.m = n;
        grid = new int[this.m][];
        for (int k = 0; k < m; k++) {
            String[] elms = scr.nextLine().split("");
            int[] line = new int[elms.length];
            for (int i = 0; i < elms.length; i++)
                line[i] = Integer.parseInt(elms[i]);
            grid[k] = line;
        }
        this.n = grid[0].length;

    }

    @Override
    public String task1() {
        int sol = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (lowpoint(i, j)) {
                    sol += grid[i][j] + 1;
                }
            }
        }

        return "" + sol;
    }

    public boolean lowpoint(int i, int j) {
        int pos = grid[i][j];
        int hc = 0; // number of neighbours that are higher
        int nb = 4; // number of poss neighbours

        // north
        if (i > 0) {
            hc = grid[i - 1][j] > pos ? hc + 1 : hc;
        } else {
            nb--;
        }
        // east
        if (j < n - 1) {
            hc = grid[i][j + 1] > pos ? hc + 1 : hc;
        } else {
            nb--;
        }
        // south
        if (i < m - 1) {
            hc = grid[i + 1][j] > pos ? hc + 1 : hc;
        } else {
            nb--;
        }
        // west
        if (j > 0) {
            hc = grid[i][j - 1] > pos ? hc + 1 : hc;
        } else {
            nb--;
        }

        return hc == nb;
    }

    @Override
    public String task2() {
        // can be done more effectively probably but ehhh

        basin = new int[m][n];
        int lc = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (lowpoint(i, j)) {
                    basin[i][j] = lc;
                    lc++;
                } else if (grid[i][j] == 9) {
                    basin[i][j] = -1;
                }
            }
        }

        while (!allAssigned(basin)) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (basin[i][j] == 0)
                        neighbourBasin(i, j);
                }
            }
        }

        int[] sizes = new int[lc - 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (basin[i][j] > 0) {
                    sizes[basin[i][j] - 1]++;
                }
            }
        }
        return "" + sizes[sizes.length - 1] * sizes[sizes.length - 2] * sizes[sizes.length - 3];
    }

    public void neighbourBasin(int i, int j) {
        // north
        if (i > 0) {
            if (basin[i - 1][j] > 0) {
                basin[i][j] = basin[i - 1][j];
                return;
            }
        }
        // east
        if (j < n - 1) {
            if (basin[i][j + 1] > 0) {
                basin[i][j] = basin[i][j + 1];
                return;
            }
        }
        // south
        if (i < m - 1) {
            if (basin[i + 1][j] > 0) {
                basin[i][j] = basin[i + 1][j];
                return;
            }
        }
        // west
        if (j > 0) {
            if (basin[i][j - 1] > 0)
                basin[i][j] = basin[i][j - 1];
        }

    }

    public boolean allAssigned(int[][] basin) {
        int c = 0;
        for (int i = 0; i < basin.length; i++) {
            for (int j = 0; j < basin[0].length; j++) {
                if (basin[i][j] != 0) {
                    c++;
                }
            }
        }
        return c == basin.length * basin[0].length;
    }

    public static void print2dArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(Arrays.toString(arr[i]));
        }
    }

}
