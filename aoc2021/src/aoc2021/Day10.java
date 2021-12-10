package aoc2021;

import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;

public class Day10 implements AoCTask {
    String[] lines;

    public static void main(String[] args) {
        Executor ex = new Executor();

        AoCTask small = new Day10();
        AoCTask big = new Day10();

        ex.execute_timed(small, "inputs/input_s_10", 10);
        System.out.println();
        ex.execute_timed(big, "inputs/input_10", 98);
    }

    @Override
    public void readInput(Scanner scr, int n) {
        lines = new String[n];
        for (int i = 0; i < n; i++) {
            lines[i] = scr.nextLine();
        }
    }

    @Override
    public String task1() {
        int sum = 0;
        for (String line : lines) {
            sum += score(invalidChar(line));
        }
        return "" + sum;
    }

    public char invalidChar(String line) {
        if (!isOpening(line.charAt(0))) {
            return line.charAt(0);
        }

        int l = line.length();
        int i = 1;
        ArrayList<Character> stack = new ArrayList<Character>();
        stack.add(line.charAt(0));

        while (i < l) {
            char c = line.charAt(i);
            if (isOpening(c)) {
                stack.add(c);
            } else {
                char d = stack.remove(stack.size() - 1);
                if (!matching(d, c)) {
                    return c;
                }
            }

            i++;
        }

        return 'a';
    }

    public boolean isOpening(char a) {
        return a == '(' || a == '[' || a == '{' || a == '<';
    }

    public boolean matching(char a, char b) {
        if (a == '(' && b == ')') {
            return true;
        } else if (a == '[' && b == ']') {
            return true;
        } else if (a == '{' && b == '}') {
            return true;
        } else if (a == '<' && b == '>') {
            return true;
        }
        return false;
    }

    public int score(char c) {
        switch (c) {
            case ')':
                return 3;
            case ']':
                return 57;
            case '}':
                return 1197;
            case '>':
                return 25137;
            default:
                return 0;
        }
    }

    @Override
    public String task2() {
        ArrayList<Long> scores = new ArrayList<Long>();
        for (String line : lines) {
            if (invalidChar(line) != 'a') {
                continue;
            }
            scores.add(sortingScore(line));
        }
        Collections.sort(scores);

        return "" + scores.get(scores.size() / 2);
    }

    public Long sortingScore(String line) {
        int l = line.length();
        int i = 1;
        ArrayList<Character> stack = new ArrayList<Character>();
        stack.add(line.charAt(0));

        while (i < l) {
            char c = line.charAt(i);
            if (isOpening(c)) {
                stack.add(c);
            } else {
                stack.remove(stack.size() - 1);
            }
            i++;
        }
        Collections.reverse(stack);

        long cost = 0;

        for (char c : stack) {
            cost *= 5;
            cost += cost(c);
        }

        return cost;
    }

    public int cost(char c) {
        switch (c) {
            case '(':
                return 1;
            case '[':
                return 2;
            case '{':
                return 3;
            case '<':
                return 4;
            default:
                return 0;
        }
    }

}
