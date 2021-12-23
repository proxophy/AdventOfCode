package aoc2021;

import java.util.Arrays;
import java.util.Scanner;

public class Day21 implements AoCTask {
    Player p1;
    Player p2;

    public static void main(String[] args) {
        Executor ex = new Executor();

        AoCTask small = new Day21();
        AoCTask big = new Day21();

        ex.execute_timed(small, "inputs/input_s_21", 2);
        // System.out.println();
        // ex.execute_timed(big, "inputs/input_21", 2);

    }

    @Override
    public void readInput(Scanner scr, int n) {
        String[] line = scr.nextLine().split(" ");
        p1 = new Player(line[1], line[4]);
        line = scr.nextLine().split(" ");
        p2 = new Player(line[1], line[4]);
    }

    @Override
    public String task1() {
        int c = 0;
        int i = 0;
        boolean turn = true;
        while (!(p1.score >= 1000 || p2.score >= 1000)) {
            if (turn) {
                p1.playDet100(i + 1);
            } else {
                p2.playDet100(i + 1);
            }
            i = (i + 3) % 100;
            c++;
            turn = !turn;

        }

        int minScore = turn ? p1.score : p2.score;

        return "" + 3 * c * minScore;
    }

    @Override
    public String task2() {
        // pos1, score1, pos2, score2
        long[][][][] dp = new long[10][22][10][22];
        long[] res = new long[2];
        // Init
        dp[p1.pos][0][p2.pos][0] = 1;
        play(dp, true, p1.pos, 0, p2.pos, 0, res);
        System.out.println(Arrays.toString(res));
        return null;
    }

    // turn true: p1's turn
    public void play(long[][][][] table, boolean turn, int p1pos, int p1sc, int p2pos, int p2sc, long[] res) {
        if (p1sc >= 21) {
            res[0] += table[p1pos][p1sc][p2pos][p2sc];
            return;
        } else if (p2sc >= 21) {
            res[1] += table[p1pos][p1sc][p2pos][p2sc];
            return;
        }

        if (turn) {
            if (p1sc > 21 || p2sc > 21) {
                System.out.println(p1sc + " " + p2sc + " " + turn);
            }
            table[(p1pos + 1) % 10][newscore(p1sc, 1)][p2pos][p2sc] += table[p1pos][p1sc][p2pos][p2sc];
            table[(p1pos + 2) % 10][newscore(p1sc, 2)][p2pos][p2sc] += table[p1pos][p1sc][p2pos][p2sc];
            table[(p1pos + 3) % 10][newscore(p1sc, 3)][p2pos][p2sc] += table[p1pos][p1sc][p2pos][p2sc];
            play(table, !turn, (p1pos + 1) % 10, newscore(p1sc, 1), p2pos, p2sc, res);
            play(table, !turn, (p1pos + 2) % 10, newscore(p1sc, 2), p2pos, p2sc, res);
            play(table, !turn, (p1pos + 3) % 10, newscore(p1sc, 3), p2pos, p2sc, res);
        } else {
            if (p1sc > 21 || p2sc > 21) {
                System.out.println(p1sc + " " + p2pos + " " + turn);
            }
            table[p1pos][p1sc][(p1pos + 1) % 10][newscore(p2sc, 1)] += table[p1pos][p1sc][p2pos][p2sc];
            table[p1pos][p1sc][(p1pos + 2) % 10][newscore(p2sc, 2)] += table[p1pos][p1sc][p2pos][p2sc];
            table[p1pos][p1sc][(p1pos + 3) % 10][newscore(p2sc, 3)] += table[p1pos][p1sc][p2pos][p2sc];
            play(table, !turn, p1pos, p1sc, (p2pos + 1) % 10, newscore(p2sc, 1), res);
            play(table, !turn, p1pos, p1sc, (p2pos + 2) % 10, newscore(p2sc, 2), res);
            play(table, !turn, p1pos, p1sc, (p2pos + 3) % 10, newscore(p2sc, 3), res);
        }

    }

    public static int newscore(int curr, int step) {
        return curr + step > 21 ? 21 : curr + step;
    }

    class Player {
        int id;
        int pos;
        int score;

        Player(String id, String pos) {
            this.id = Integer.parseInt(id);
            this.pos = Integer.parseInt(pos) - 1;
            this.score = 0;
        }

        @Override
        public String toString() {
            return "I: " + this.id + " P: " + (this.pos + 1) + " S: " + this.score;
        }

        public void playDet100(int i) {
            int roll = 3 * i + 3;
            if (i == 99) {
                roll -= 100;
            } else if (i == 100) {
                roll -= 200;
            }

            this.pos = (this.pos + roll) % 10;
            this.score += this.pos + 1;
            // System.out.println(this.id + " " + (pos + 1) + " " + score);
        }
    }

}
