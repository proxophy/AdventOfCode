package aoc2021;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day14 implements AoCTask {
    Map<String, Long> chars; // char, how often char occures
    Map<String, String> rules; // derivation rules
    Map<String, Long> pairs; // pair, how often occures

    public static void main(String[] args) {
        // set approach
        Executor ex = new Executor();

        AoCTask small = new Day14();
        AoCTask big = new Day14();

        ex.execute_timed(small, "inputs/input_s_14", 0);
        System.out.println();
        ex.execute_timed(big, "inputs/input_14", 10);

    }

    @Override
    public void readInput(Scanner scr, int n) {
        chars = new HashMap<String, Long>();
        rules = new HashMap<String, String>();
        pairs = new HashMap<String, Long>();

        String firstline = scr.nextLine();
        scr.nextLine();

        while (scr.hasNextLine()) {
            // replaceAll("\\s", "") strip of whitespaces
            String[] elms = scr.nextLine().replaceAll("\\s", "").split("->");
            rules.put(elms[0], elms[1]);
            chars.put(elms[1], 0l);
            pairs.put(elms[0], 0l);
        }

        for (int i = 0; i < firstline.length() - 1; i++) {
            String c = firstline.substring(i, i + 1);
            String p = firstline.substring(i, i + 2);

            if (!chars.containsKey(c)) {
                chars.put(c, 0l);
            }
            chars.put(c, chars.get(c) + 1);

            if (!pairs.containsKey(p)) {
                pairs.put(p, 0l);
            }
            pairs.put(p, pairs.get(p) + 1);
        }

        String c = firstline.substring(firstline.length() - 1, firstline.length());
        if (!chars.containsKey(c)) {
            chars.put(c, 0l);
        }
        chars.put(c, chars.get(c) + 1);
    }

    @Override
    public String task1() {
        for (int i = 0; i < 10; i++) {
            simulateStep();
        }
        return "" + (maxmin()[0] - maxmin()[1]);
    }

    public void simulateStep() {
        HashMap<String, Long> newPairs = new HashMap<String, Long>();

        for (String p : pairs.keySet()) {
            String news = "" + p.charAt(0) + rules.get(p) + p.charAt(1);
            String p1 = news.substring(0, 2);
            String p2 = news.substring(1, 3);

            if (newPairs.containsKey(p1)) {
                newPairs.put(p1, newPairs.get(p1) + pairs.get(p));
            } else {
                newPairs.put(p1, pairs.get(p));
            }

            if (newPairs.containsKey(p2)) {
                newPairs.put(p2, newPairs.get(p2) + pairs.get(p));
            } else {
                newPairs.put(p2, pairs.get(p));
            }

            if (pairs.get(p) != 0) {
                chars.put(rules.get(p), chars.get(rules.get(p)) + pairs.get(p));
            }
        }
        pairs = newPairs;
    }

    public long getLength() {
        long c = 0;
        for (long k : chars.values()) {
            c += k;
        }
        return c;
    }

    public long[] maxmin() {
        long max = 0;
        long min = Long.MAX_VALUE;
        for (String p : chars.keySet()) {
            max = Math.max(max, chars.get(p));
            min = Math.min(min, chars.get(p));
        }
        return new long[] { max, min };
    }

    @Override
    public String task2() {
        for (int i = 0; i < 30; i++) {
            simulateStep();
        }
        return "" + (maxmin()[0] - maxmin()[1]);
    }

}
