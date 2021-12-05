package aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day4 implements AoCTask {
    ArrayList<Board> boards = new ArrayList<Board>();
    ArrayList<Integer> drawn = new ArrayList<Integer>();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scr;

        File file = new File("input_s");
        scr = new Scanner(file);
        Day4 small = new Day4();
        small.readInput(scr, 0);
        System.out.println(small.task2());
        scr.close();

        File file2 = new File("input");
        scr = new Scanner(file2);
        Day4 big = new Day4();
        big.readInput(scr, 1000);
        scr.close();
        System.out.println(big.task2());

    }

    @Override
    public void readInput(Scanner scr, int n) {
        // read in numbers
        String firstline = scr.nextLine();
        firstline = firstline.replace(',', ' ');
        Scanner linescr = new Scanner(firstline);
        while (linescr.hasNextInt()) {
            drawn.add(linescr.nextInt());
        }
        linescr.close();

        // read in numbers
        while (scr.hasNextInt()) {
            int[][] nums = new int[5][5];
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    nums[i][j] = scr.nextInt();
                }
            }
            Board board = new Board(nums);
            boards.add(board);
        }

    }

    @Override
    public int task1() {
        for (int n : drawn) {
            Board move = play(n);
            if (move != null) {
                return move.sumUnmarked() * n;
            }
        }
        return 0;
    }

    public Board play(int num) {
        for (Board b : boards) {
            b.crossNumber(num);
            if (b.isWinning()) {
                return b;
            }
        }

        return null;

    }

    @Override
    public int task2() {
        ArrayList<Board> ingame = new ArrayList<Board>();
        ingame.addAll(boards);

        for (int num : drawn) {
            for (Board b : boards) {
                if (b.haswon) {
                    continue;
                }
                b.crossNumber(num);
                if (b.isWinning()) {
                    if (ingame.size() == 1) {
                        return ingame.get(0).sumUnmarked() * num;
                    }
                    ingame.remove(b);
                }
            }
        }
        return 0;
    }

}

class Board {
    int[][] numbers;
    boolean[][] marked;
    boolean haswon = false;

    Board(int[][] numbers) {
        this.numbers = numbers;
        this.marked = new boolean[numbers.length][numbers[0].length];
    }

    void printBoard() {
        for (int i = 0; i < this.numbers.length; i++) {
            System.out.println(Arrays.toString(this.numbers[i]));
        }
    }

    void printMarked() {
        for (int i = 0; i < this.numbers.length; i++) {
            System.out.println(Arrays.toString(this.marked[i]));
        }
    }

    void crossNumber(int num) {
        for (int i = 0; i < this.numbers.length; i++) {
            for (int j = 0; j < this.numbers[i].length; j++) {
                if (this.numbers[i][j] == num) {
                    marked[i][j] = true;
                }
            }
        }
    }

    boolean isWinning() {
        for (int i = 0; i < this.numbers.length; i++) {
            boolean row = true;
            boolean column = true;
            for (int j = 0; j < this.numbers[i].length; j++) {
                row = row && marked[i][j];
                column = column && marked[j][i];
            }
            if (row || column) {
                haswon = true;
                return true;
            }

        }
        return false;
    }

    int sumUnmarked() {
        int sum = 0;
        for (int i = 0; i < this.numbers.length; i++) {
            for (int j = 0; j < this.numbers[i].length; j++) {
                if (!marked[i][j])
                    sum += numbers[i][j];
            }
        }
        return sum;
    }
}