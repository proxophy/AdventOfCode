package aoc2021;

import java.util.Arrays;
import java.util.Scanner;

public class Day25 implements AoCTask {
    int[][] grid;
    int[][] initgrid;
    int m;
    int n;

    public static void main(String[] args) {
        Executor ex = new Executor();

        AoCTask small = new Day25();
        AoCTask big = new Day25();

        ex.execute_timed(small, "inputs/input_s_25", 9);
        System.out.println();
        ex.execute_timed(big, "inputs/input_25", 137);
    }

    @Override
    public void readInput(Scanner scr, int m) {
        this.grid = new int[m][];
        this.m = m;

        for (int i = 0; i < m; i++) {
            String[] line = scr.nextLine().split("");
            this.n = line.length;
            this.grid[i] = new int[n];
            for (int j = 0; j < n; j++) {
                grid[i][j] = line[j].equals(".") ? 0 : (line[j].equals(">") ? 1 : 2);

            }
        }

        this.initgrid = this.grid;
    }

    public void printGrid() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(grid[i][j] == 0 ? "." : (grid[i][j] == 1 ? ">" : "v"));
            }
            System.out.println();
        }
    }

    @Override
    public String task1() {
        int c = 1;
        while (step()) {
            c++;
        }
        return "" + c;
    }

    public boolean step() {
        boolean a = eastStep();
        boolean b = southStep();
        return a || b;
    }

    public boolean eastStep() {
        int c = 0;
        int[][] newgrid = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    if (grid[i][(j + 1) % n] == 0) {
                        newgrid[i][(j + 1) % n] = 1;
                        c++;
                    } else {
                        newgrid[i][j] = grid[i][j];
                    }
                } else if (newgrid[i][j] == 0) {
                    newgrid[i][j] = grid[i][j];
                }
            }
        }
        this.grid = newgrid;
        return c != 0;
    }

    public boolean southStep() {
        int c = 0;
        int[][] newgrid = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    if (grid[(i + 1) % m][j] == 0) {
                        newgrid[(i + 1) % m][j] = 2;
                        c++;
                    } else {
                        newgrid[i][j] = grid[i][j];
                    }
                } else if (newgrid[i][j] == 0) {
                    newgrid[i][j] = grid[i][j];
                }
            }
        }
        this.grid = newgrid;
        return c != 0;
    }

    @Override
    public String task2() {
        // TODO Auto-generated method stub
        return null;
    }

}
