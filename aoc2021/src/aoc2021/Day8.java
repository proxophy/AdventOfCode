package aoc2021;

import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Day8 implements AoCTask {
    String[][] input;
    int n;

    public static void main(String[] args) {
        Executor ex = new Executor();

        AoCTask small = new Day8();
        AoCTask big = new Day8();

        ex.execute_timed(small, "inputs/input_s_8", 10);
        System.out.println();
        ex.execute_timed(big, "inputs/input_8", 200);
    }

    @Override
    public void readInput(Scanner scr, int n) {
        this.n = n;
        input = new String[this.n][14];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 10; j++) {
                input[i][j] = scr.next();
            }
            scr.next();
            for (int j = 10; j < 14; j++) {
                input[i][j] = scr.next();
            }
        }

    }

    @Override
    public String task1() {
        int c = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 10; j < 14; j++) {
                String curr = input[i][j];
                int l = curr.length();
                if (l == 2 || l == 4 || l == 3 || l == 7) {
                    c++;
                }
            }
        }
        return "" + c;
    }

    @Override
    public String task2() {
        int sum = 0;
        for (int i = 0; i < this.n; i++) {
            sum += Integer.parseInt(solveLine(input[i]));
        }
        return "" + sum;
    }

    // probably could have been sold more efficiently, but was fun
    // HashMaps are cool
    public String solveLine(String[] line) {
        // input digit
        ArrayList<String> digits = new ArrayList<String>(Arrays.asList(Arrays.copyOfRange(line, 0, 10)));
        // code x is digit y
        HashMap<Set<Character>, Integer> sol = new HashMap<Set<Character>, Integer>();
        // wire x maps to segment y, not complete
        HashMap<String, String> chars = new HashMap<String, String>();
        // i is code codes[i] or null if not yet determined
        String[] codes = new String[10];

        // 1,4,7,8
        for (String s : digits) {
            if (s.length() == 2) {
                sol.put(toSet(s), 1);
                codes[1] = s;
            } else if (s.length() == 4) {
                sol.put(toSet(s), 4);
                codes[4] = s;
            } else if (s.length() == 3) {
                sol.put(toSet(s), 7);
                codes[7] = s;
            } else if (s.length() == 7) {
                sol.put(toSet(s), 8);
                codes[8] = s;
            }
        }
        digits.remove(codes[1]);
        digits.remove(codes[4]);
        digits.remove(codes[7]);
        digits.remove(codes[8]);

        // compare 7 and 1, get segment for wire a
        // not used
        for (int i = 0; i < 3; i++) {
            if (!codes[1].contains("" + codes[7].charAt(i))) {
                chars.put("a", codes[7].charAt(i) + "");
            }
        }

        // 3
        for (String s : digits) {
            if (s.length() == 5) {
                // not 5 or 2
                if (s.contains("" + codes[1].charAt(0)) && s.contains("" + codes[1].charAt(1))) {
                    sol.put(toSet(s), 3);
                    codes[3] = s;
                    break;
                }
            }
        }
        digits.remove(codes[3]);

        // 9
        LinkedList<String> sixsegm = new LinkedList<String>(); // will include 0 and 8
        for (String s : digits) {
            if (s.length() == 6) {
                // when true, segments of 4 are subset of segments
                boolean subset = true;
                for (int j = 0; j < 4; j++) {
                    subset = subset && s.contains(codes[4].charAt(j) + "");
                }
                if (subset) {
                    sol.put(toSet(s), 9);
                    codes[9] = s;
                } else {
                    sixsegm.add(s);
                }
            }
        }
        digits.remove(codes[9]);

        // 0 and 6
        String miss = ""; // find char not included in digit(0)
        String secondmiss = ""; // find char not included in digit(1)
        for (int i = 0; i < 7; i++) {
            if (!sixsegm.get(0).contains(codes[8].charAt(i) + "")) {
                miss = codes[8].charAt(i) + "";
            }
            if (!sixsegm.get(1).contains(codes[8].charAt(i) + "")) {
                secondmiss = codes[8].charAt(i) + "";
            }
        }
        // if c is missing segment
        if (codes[1].contains(miss)) {
            chars.put("c", miss);
            chars.put("d", secondmiss);
            sol.put(toSet(sixsegm.get(0)), 6);
            codes[6] = sixsegm.get(0);
            sol.put(toSet(sixsegm.get(1)), 0);
            codes[0] = sixsegm.get(1);
        } else {
            chars.put("d", miss);
            chars.put("c", secondmiss);
            sol.put(toSet(sixsegm.get(1)), 6);
            codes[6] = sixsegm.get(1);
            sol.put(toSet(sixsegm.get(0)), 0);
            codes[0] = sixsegm.get(0);
        }
        digits.remove(codes[0]);
        digits.remove(codes[6]);

        // 2 and 5
        if (digits.get(0).contains(chars.get("c"))) {
            sol.put(toSet(digits.get(0)), 2);
            codes[2] = digits.get(0);
            sol.put(toSet(digits.get(1)), 5);
            codes[5] = digits.get(1);
        } else {
            sol.put(toSet(digits.get(1)), 2);
            codes[2] = digits.get(1);
            sol.put(toSet(digits.get(0)), 5);
            codes[5] = digits.get(0);
        }

        String s = "";
        for (int i = 10; i < 14; i++) {
            s += sol.get(toSet(line[i]));
        }

        return s;
    }

    public static Set<Character> toSet(String s) {
        return s.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
    }

}
