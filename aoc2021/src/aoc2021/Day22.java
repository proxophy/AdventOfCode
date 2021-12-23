package aoc2021;

import java.util.Arrays;
import java.util.Scanner;

public class Day22 implements AoCTask {
    Cuboid[] cuboids;

    public static void main(String[] args) {
        Executor ex = new Executor();

        AoCTask small = new Day22();
        AoCTask big = new Day22();

        ex.execute_timed(small, "inputs/input_s_22", 4);
        // System.out.println();
        // ex.execute_timed(big, "inputs/input_22", 420);

    }

    @Override
    public void readInput(Scanner scr, int n) {
        this.cuboids = new Cuboid[n];
        for (int i = 0; i < n; i++) {
            boolean turn = scr.next().equals("on");
            String[] elms = scr.nextLine().split(",");
            cuboids[i] = new Cuboid(extractNumbers(elms[0]), extractNumbers(elms[1]), extractNumbers(elms[2]), turn);
        }
    }

    // s = "a=u1...u2" -> u1, u2
    public static long[] extractNumbers(String s) {
        String[] els = s.split("=")[1].replace("..", " ").split(" ");
        return new long[] { Long.parseLong(els[0]), Long.parseLong(els[1]) };
    }

    @Override
    public String task1() {
        // naive version
        int c = 0;
        boolean[][][] grid = new boolean[101][101][101];
        for (Cuboid cub : cuboids) {
            if (cub.sx < -50 || cub.sy < -50 || cub.sz < -50) {
                continue;
            } else if (cub.sx > 50 || cub.sy > 50 || cub.sz > 50) {
                continue;
            }
            for (int i = (int) cub.sx; i <= cub.ex; i++) {
                for (int j = (int) cub.sy; j <= cub.ey; j++) {
                    for (int k = (int) cub.sz; k <= cub.ez; k++) {
                        if (cub.turn && !grid[i + 50][j + 50][k + 50]) {
                            grid[i + 50][j + 50][k + 50] = true;
                            c++;
                        } else if (cub.turn) {
                            continue;
                        } else if (!cub.turn && grid[i + 50][j + 50][k + 50]) {
                            grid[i + 50][j + 50][k + 50] = false;
                            c--;
                        }

                    }
                }
            }
        }

        return "" + c;
    }

    @Override
    public String task2() {
        long c = cuboids[0].vol();

        for (int i = 1; i < cuboids.length; i++) {
            Cuboid newc = cuboids[i]; // new step
            if (newc.turn) {
                c += newc.vol();
            }

            for (int j = 0; j < i; j++) {
                Cuboid oldc = cuboids[j]; // step already applied
                if (!newc.isOverlapping(oldc)) {
                    continue;
                }
                if (newc.turn) {
                    c -= newc.overlappingCount(oldc);
                } else {
                    if (oldc.turn) {
                        // TODO
                    }
                }

            }
        }

        return "" + c;
    }

    public class Cuboid {
        long sx, ex, sy, ey, sz, ez;
        boolean turn;

        public Cuboid(long[] xc, long[] yc, long[] zc, boolean turn) {
            this.sx = xc[0];
            this.ex = xc[1];
            this.sy = yc[0];
            this.ey = yc[1];
            this.sz = zc[0];
            this.ez = zc[1];
            this.turn = turn;
        }

        public boolean isOverlapping(Cuboid other) {
            if (!((this.sx <= other.sx && other.sx <= this.ex)
                    || (this.sx <= other.ex && other.ex <= this.ex)))
                return false;
            else if (!((this.sy <= other.sy && other.sy <= this.ey)
                    || (this.sy <= other.ey && other.ey <= this.ey)))
                return false;
            else if (!((this.sz <= other.sz && other.sz <= this.ez)
                    || (this.sz <= other.ez && other.ez <= this.ez)))
                return false;
            return true;
        }

        public long overlappingCount(Cuboid other) {
            if (!this.isOverlapping(other)) {
                System.out.println("overlapping");
                return 0;
            }

            long xo = this.ex >= other.sx ? this.ex - other.sx + 1 : other.ex - this.sx + 1;
            long yo = this.ey >= other.sy ? this.ey - other.sy + 1 : other.ey - this.sy + 1;
            long zo = this.ez >= other.sz ? this.ez - other.sz + 1 : other.ez - this.sz + 1;
            assert (xo * yo * zo <= 0);

            return xo * yo * zo;
        }

        public long vol() {
            return (ex - sx + 1) * (ey - sy + 1) * (ey - sy + 1);
        }

        @Override
        public String toString() {
            return (turn ? "on " : "off ") +
                    String.format("(%d, %d), (%d, %d), (%d, %d)", sx, ex, sy, ey, sz, ez);
        }
    }

}
