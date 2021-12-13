package aoc2021;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;

public class Day13_2 implements AoCTask {
    TreeSet<Point> points;
    long maxx = 0;
    long maxy = 0;
    long xc;
    long yc;
    ArrayList<Instruction> instr;
    Comparator<Point> comp;

    public static void main(String[] args) {
        // set approach
        Executor ex = new Executor();

        Day13_2 small = new Day13_2();
        Day13_2 big = new Day13_2();

        ex.execute_timed(small, "inputs/input_s_13", 0);
        System.out.println();
        ex.execute_timed(big, "inputs/input_13_m", 10);

    }

    @Override
    public void readInput(Scanner scr, int n) {
        comp = new Comparator<Point>() {

            @Override
            public int compare(Point o1, Point o2) {
                if (o1.x == o2.x && o1.y == o2.y) {
                    return 0;
                } else if (o1.x < o2.x) {
                    return -1;

                } else {
                    return 1;
                }
            }
        };

        String line = scr.nextLine();
        points = new TreeSet<Point>(comp);
        instr = new ArrayList<Instruction>();
        do {
            String[] els = line.split(",");
            points.add(new Point(Long.parseLong(els[0]), Long.parseLong(els[1])));
            maxx = Math.max(maxx, Long.parseLong(els[0]));
            maxy = Math.max(maxy, Long.parseLong(els[1]));
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
        fold(instr.get(0));
        return "" + points.size();
    }

    public void fold(Instruction inst) {
        long pos = inst.pos;
        TreeSet<Point> new_points = new TreeSet<Point>(comp);

        for (Point p : points) {

            if (inst.x) {
                if (p.x >= pos && p.x - 2 * (p.x - pos) >= 0) {
                    p.x -= 2 * (p.x - pos);
                }
            } else {

                if (p.y >= pos && p.y - 2 * (p.y - pos) >= 0) {
                    p.y -= 2 * (p.y - pos);
                }
            }
            new_points.add(p);
        }

        points = new_points;
        if (inst.x) {
            maxx = pos;
        } else {
            maxy = pos;
        }

    }

    @Override
    public String task2() {

        for (Instruction inst : instr) {
            fold(inst);
        }

        return "" + this.toString();
    }

    public class Instruction {
        boolean x;
        Long pos;

        Instruction(String line) {
            Scanner sc = new Scanner(line);
            sc.next();
            sc.next();
            String[] elms = sc.next().split("=");
            sc.close();
            this.x = elms[0].equals("x");
            this.pos = Long.parseLong(elms[1]);
        }

        @Override
        public String toString() {
            return "(" + (this.x ? "x" : "y") + ", " + this.pos + ")";
        }

    }

    @Override
    public String toString() {
        boolean[][] grid = makegrid();
        String sol = "\n";
        for (int i = 0; i <= maxy; i++) {
            String line = "";
            for (int j = 0; j <= maxx; j++) {
                line += (grid[i][j] ? "#" : " ");
            }
            sol += line + "\n";
        }
        return sol;
    }

    public boolean[][] makegrid() {
        boolean[][] res = new boolean[(int) maxy + 1][(int) maxx + 1];
        for (Point p : points) {
            if (p.y >= maxy) {
                System.out.println(p.toString() + " fault");
            }
            res[(int) p.y][(int) p.x] = true;
        }

        return res;
    }

}

class Point {
    long x;
    long y;

    Point(long x, long y) {
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
            return false;
        }
        Point other = (Point) o;

        return other.x == this.x && other.y == this.y;
    }

}
