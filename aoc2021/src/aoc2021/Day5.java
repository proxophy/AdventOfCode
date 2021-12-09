package aoc2021;

import java.util.Scanner;

public class Day5 implements AoCTask {
    Segment[] segments;
    int maxx = 0;
    int maxy = 0;

    public static void main(String[] args) {
        Executor ex = new Executor();

        AoCTask small = new Day5();
        AoCTask big = new Day5();

        ex.execute_timed(small, "inputs/input_s_5", 10);
        System.out.println();
        ex.execute_timed(big, "inputs/input_5", 500);
    }

    @Override
    public void readInput(Scanner scr, int n) {
        this.segments = new Segment[n];
        for (int i = 0; i < n; i++) {
            String line = scr.nextLine();
            line = line.replace(',', ' ');
            Scanner lscr = new Scanner(line);
            int x1 = lscr.nextInt();
            int y1 = lscr.nextInt();
            lscr.next();
            int x2 = lscr.nextInt();
            int y2 = lscr.nextInt();
            Segment seg = new Segment(x1, y1, x2, y2);
            lscr.close();
            segments[i] = seg;

            maxx = Math.max(maxx, Math.max(x1, x2));
            maxy = Math.max(maxy, Math.max(y1, y2));
        }
        // System.out.println(maxx + " " + maxy);
    }

    @Override
    public String task1() {
        int[][] grid = new int[this.maxy + 1][this.maxx + 1];
        for (Segment seg : this.segments) {
            if (seg.isHorizontal()) {
                for (int j = Math.min(seg.x1, seg.x2); j <= Math.max(seg.x1, seg.x2); j++) {
                    grid[seg.y1][j] = grid[seg.y1][j] + 1;
                }
            } else if (seg.isVertical()) {
                for (int i = Math.min(seg.y1, seg.y2); i <= Math.max(seg.y1, seg.y2); i++) {
                    grid[i][seg.x1] = grid[i][seg.x1] + 1;
                }
            }
        }
        int c = 0;
        for (int i = 0; i < this.maxy + 1; i++) {
            for (int j = 0; j < this.maxx + 1; j++) {
                if (grid[i][j] > 1)
                    c++;
            }
        }

        return "" + c;
    }

    @Override
    public String task2() {
        int[][] grid = new int[this.maxy + 1][this.maxx + 1];
        for (Segment seg : this.segments) {
            int xup = seg.x2 != seg.x1 ? (seg.x2 - seg.x1) / (Math.abs(seg.x2 - seg.x1)) : 0;
            int yup = seg.y2 != seg.y1 ? (seg.y2 - seg.y1) / (Math.abs(seg.y2 - seg.y1)) : 0;

            int x = seg.x1, y = seg.y1;
            while (x != seg.x2 || y != seg.y2) {
                grid[y][x]++;
                x += xup;
                y += yup;
            }

            grid[seg.y2][seg.x2]++;
        }
        int c = 0;
        for (int i = 0; i < this.maxy + 1; i++) {
            for (int j = 0; j < this.maxx + 1; j++) {
                if (grid[i][j] > 1)
                    c++;
            }
        }

        return "" + c;
    }

}

class Segment {
    int x1;
    int y1;
    int x2;
    int y2;

    Segment(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    boolean isHorizontal() {
        return y1 == y2;
    }

    boolean isVertical() {
        return x1 == x2;
    }

    @Override
    public String toString() {
        return "(" + x1 + ", " + y1 + ") -> " + "(" + x2 + ", " + y2 + ")";

    }
}