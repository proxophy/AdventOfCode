package aoc2021;

import java.util.Scanner;

public class Day17 implements AoCTask {
    TargetArea ta;

    public static void main(String[] args) {
        Executor ex = new Executor();

        AoCTask small = new Day17();
        AoCTask big = new Day17();

        ex.execute_timed(small, "inputs/input_s_17", 0);
        System.out.println();
        ex.execute_timed(big, "inputs/input_17", 1);

    }

    @Override
    public void readInput(Scanner scr, int n) {
        String line = scr.nextLine();
        // I should learn regex ...
        line = line.replace("target area: x=", "");
        line = line.replace(", y=", " ");
        line = line.replace("..", " ");
        Scanner lscr = new Scanner(line);
        int minx = lscr.nextInt();
        int maxx = lscr.nextInt();
        int miny = lscr.nextInt();
        int maxy = lscr.nextInt();
        lscr.close();
        this.ta = new TargetArea(minx, maxx, miny, maxy);

    }

    @Override
    public String task1() {
        // vermax[i][j] will hold max vert pos for vel i, j
        int[][] vermax = new int[ta.maxx][ta.maxx + Math.abs(ta.maxy - ta.miny)];

        xloop: for (int i = 0; i < vermax.length; i++) {
            if (i * (i + 1) / 2 < ta.minx)
                continue;
            for (int j = 0; j < vermax[i].length; j++) {
                Probe curr = new Probe(i, j, ta);
                vermax[i][j] = curr.simulateTargeting();
                if (vermax[i][j] == -1) {
                    continue xloop;
                }
            }
        }

        int res = 0;
        for (int i = 0; i < vermax.length; i++) {
            for (int j = 0; j < vermax[i].length; j++) {
                res = Math.max(res, vermax[i][j]);
            }
        }

        return "" + res;
    }

    @Override
    public String task2() {
        int lenx = ta.maxx + 1;
        int leny = Math.abs(ta.miny) + ta.maxx + Math.abs(ta.maxy - ta.miny);
        int[][] vermax = new int[lenx][leny];

        int res = 0;

        xloop: for (int i = 0; i < vermax.length; i++) {
            if (i * (i + 1) / 2 < ta.minx)
                continue;
            for (int j = 0; j < vermax[i].length; j++) {
                Probe curr = new Probe(i, val(j), ta);

                vermax[i][j] = curr.simulateTargeting();

                if (vermax[i][j] == -1) {
                    continue xloop;
                } else if (vermax[i][j] >= 0) {
                    res++;
                }
            }
        }

        return "" + res;
    }

    public int val(int j) {
        return ta.miny + j;
    }

    public class Probe {
        int x, y;
        int vx, vy;
        int initvx, initvy;
        TargetArea ta;

        Probe(int vx, int vy, TargetArea ta) {
            this.x = 0;
            this.y = 0;
            this.vx = vx;
            this.vy = vy;
            this.ta = ta;
            this.initvx = vx;
            this.initvy = vy;
        }

        @Override
        public String toString() {
            return String.format("vel: (%d, %d)", initvx, initvy);
        }

        // tries to land in targetArea, if succesfull, return max. y pos, else return -1
        public int simulateTargeting() {
            int lastx = 0;
            int lasty = 0;
            int maxv = 0;

            while (!ta.isIn(this)) {
                lastx = this.x;
                lasty = this.y;
                simulateStep();
                maxv = Math.max(maxv, this.y);
                if (missed(lastx, lasty)) {
                    return xmissed(lastx, lasty) ? -1 : (ymissed(lastx, lasty) ? -2 : -3);
                }

            }
            return maxv;
        }

        public boolean missed(int lastx, int lasty) {
            return xmissed(lasty, lasty) || ymissed(lastx, lasty)
                    || (this.vx == 0 && this.x < ta.minx);
        }

        public boolean xmissed(int lastx, int lasty) {
            return lastx < ta.minx && ta.maxx < this.x;
        }

        public boolean ymissed(int lastx, int lasty) {
            return lasty < ta.miny && ta.maxy < this.y;
        }

        public void simulateStep() {
            this.x += this.vx;
            this.y += this.vy;
            this.vy--;
            if (this.vx > 0) {
                this.vx--;
            } else if (this.vx < 0) {
                this.vx++;
            }
        }
    }

    public class TargetArea {
        int minx, maxx, miny, maxy;

        TargetArea(int minx, int maxx, int miny, int maxy) {
            this.minx = minx;
            this.maxx = maxx;
            this.miny = miny;
            this.maxy = maxy;
        }

        @Override
        public String toString() {
            return String.format("[%d, %d], [%d, %d]", minx, maxx, miny, maxy);
        }

        boolean isIn(Probe p) {
            return minx <= p.x && p.x <= maxx && miny <= p.y && p.y <= maxy;
        }
    }
}