package aoc2021;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2 implements AoCTask {
    int[][] input;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input_s");
        Scanner src = new Scanner(file);
        Day2 small = new Day2();
        small.readInput(src, 6);
        System.out.println(small.task2());

        File file2 = new File("input");
        Scanner scr2 = new Scanner(file2);
        Day2 big = new Day2();
        big.readInput(scr2, 1000);
        System.out.println(big.task2());
    }

    @Override
    public void readInput(Scanner scr, int n) {
        input = new int[n][2];
        for (int i = 0; i < n; i++) {
            String dirs = scr.next();
            int dir = (dirs.equals("forward")) ? 1 : (dirs.equals("up") ? 0 : 2);
            input[i][0] = dir;
            input[i][1] = scr.nextInt();

        }

    }

    @Override
    public String task1() {
        int hor = 0;
        int vert = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i][0] == 1) {
                hor += input[i][1];
            } else if (input[i][0] == 0) {
                vert -= input[i][1];
            } else if (input[i][0] == 2) {
                vert += input[i][1];
            }

        }

        return "" + hor * vert;
    }

    @Override
    public String task2() {
        int hor = 0;
        int vert = 0;
        int aim = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i][0] == 1) {
                hor += input[i][1];
                vert += aim * input[i][1];
            } else if (input[i][0] == 0) {
                aim -= input[i][1];
            } else if (input[i][0] == 2) {
                aim += input[i][1];
            }
        }

        return "" + hor * vert;
    }

}
