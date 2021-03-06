package aoc2021;

import java.util.Scanner;

public class Day3 implements AoCTask {
    String[] input;
    int n;

    public static void main(String[] args) {
        Executor ex = new Executor();

        AoCTask small = new Day3();
        AoCTask big = new Day3();

        ex.execute_timed(small, "inputs/input_s_3", 12);
        System.out.println();
        ex.execute_timed(big, "inputs/input_3", 1000);
    }

    @Override
    public void readInput(Scanner scr, int n) {
        input = new String[n];
        this.n = n;
        for (int i = 0; i < n; i++) {
            input[i] = scr.next();
        }
    }

    @Override
    public String task1() {
        int k = input[0].length();
        int onec = 0;
        int zeroc = 0;
        String gamma = "";
        String epsilon = "";
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < n; j++) {
                if (input[j].charAt(i) == '1') {
                    onec++;
                } else {
                    zeroc++;
                }
            }
            if (onec > zeroc) {
                gamma += "1";
                epsilon += "0";
            } else {
                gamma += "0";
                epsilon += "1";
            }
            onec = 0;
            zeroc = 0;
        }
        return "" + Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2);
    }

    @Override
    public String task2() {
        return "" + find_crit(false) * find_crit(true);
    }

    public int find_crit(boolean ox) {
        boolean[] out = new boolean[n];
        int outc = 0;
        int i = 0;
        while (outc < n - 1) {
            outc = find_crit_in(out, i, ox);
            i++;
        }
        for (int j = 0; j < n; j++) {
            if (!out[j]) {
                return Integer.parseInt(input[j], 2);
            }
        }

        return 0;
    }

    public int find_crit_in(boolean[] out, int in, boolean ox) {
        int outc = 0;

        int onec = 0;
        int zeroc = 0;
        for (int j = 0; j < n; j++) {
            if (out[j])
                continue;

            if (input[j].charAt(in) == '1')
                onec++;
            else
                zeroc++;

        }

        boolean crit; // when true, replace 0, else replace false
        if (ox) {
            crit = onec >= zeroc;
        } else {
            crit = zeroc > onec;
        }

        for (int i = 0; i < n; i++) {
            if (out[i]) {
                outc++;
            } else if (crit && input[i].charAt(in) == '0') {
                out[i] = true;
                outc++;
            } else if (!crit && input[i].charAt(in) == '1') {
                out[i] = true;
                outc++;
            }
        }

        return outc;
    }

}
