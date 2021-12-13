package aoc2021;

import java.util.ArrayList;
import java.util.Scanner;

public class Day13 implements AoCTask {
    ArrayList<Point> points;
    int maxx = 0;
    int maxy = 0;
    int xc;
    int yc;
    ArrayList<Instruction> instr;
    boolean[][] grid;

    public static void main(String[] args) {
        Executor ex = new Executor();

        AoCTask small = new Day13();
        AoCTask big = new Day13();

        ex.execute_timed(small, "inputs/input_s_13", 0);
        System.out.println();
        ex.execute_timed(big, "inputs/input_13", 10);
    }

    @Override
    public void readInput(Scanner scr, int n) {

        String line = scr.nextLine();
        points = new ArrayList<Point>();
        instr = new ArrayList<Instruction>();
        do {
            String[] els = line.split(",");
            points.add(new Point(Integer.parseInt(els[0]), Integer.parseInt(els[1])));
            maxx = Math.max(maxx, Integer.parseInt(els[0]));
            maxy = Math.max(maxy, Integer.parseInt(els[1]));
            line = scr.nextLine();
        } while (!line.equals(""));

        while (scr.hasNextLine()) {
            instr.add(new Instruction(scr.nextLine()));
        }
        xc = maxx;
        yc = maxy;
    }

    @Override
    public String task1() {
        grid = makegrid();
        fold(instr.get(0));
        return "" + count();
    }

    public void fold(Instruction inst) {
        int pos = inst.pos;
        if (inst.x) {
            for (int i = 0; i <= maxy; i++) {
                for (int j = 0; j <= maxx; j++) {

                    if (0 <= pos + pos - j && pos + pos - j <= maxx) {
                        grid[i][pos + pos - j] = grid[i][j] || grid[i][pos + pos - j];
                        grid[i][j] = false;
                    }
                }
            }
            maxx = pos;
        } else {
            for (int i = 0; i <= maxy; i++) {
                for (int j = 0; j <= maxx; j++) {
                    if (0 <= pos + pos - i && pos + pos - i <= maxy) {
                        grid[pos + pos - i][j] = grid[i][j] || grid[pos + pos - i][j];
                        grid[i][j] = false;
                    } else {

                    }
                }
            }
            maxy = pos;
        }
    }

    public int count() {
        int c = 0;
        for (int i = 0; i <= maxy; i++) {
            for (int j = 0; j <= maxx; j++) {
                if (grid[i][j])
                    c++;
            }
        }
        return c;
    }

    @Override
    public String task2() {
        maxx = xc;
        maxy = yc;
        grid = makegrid();
        for (Instruction inst : instr) {
            fold(inst);
        }

        return this.toString();
    }

    public boolean[][] makegrid() {
        boolean[][] res = new boolean[maxy + 1][maxx + 1];
        for (Point p : points) {
            res[p.y][p.x] = true;
        }

        return res;
    }

    public class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Point)) {
                throw new RuntimeException();
            }
            Point other = (Point) o;

            return other.x == this.x && other.y == this.y;
        }
    }

    public class Instruction {
        boolean x;
        int pos;

        Instruction(String line) {
            Scanner sc = new Scanner(line);
            sc.next();
            sc.next();
            String[] elms = sc.next().split("=");
            sc.close();
            this.x = elms[0].equals("x");
            this.pos = Integer.parseInt(elms[1]);
        }

        @Override
        public String toString() {
            return "(" + (this.x ? "x" : "y") + ", " + this.pos + ")";
        }

    }

    @Override
    public String toString() {
        String sol = "\n";
        for (int i = 0; i <= maxy; i++) {
            String line = "";
            for (int j = 0; j <= maxx; j++) {
                line += (grid[i][j] ? "#" : ".");
            }
            sol += line + "\n";
        }
        return sol;
    }
}