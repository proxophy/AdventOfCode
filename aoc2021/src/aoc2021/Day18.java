package aoc2021;

import java.util.Arrays;
import java.util.Scanner;

public class Day18 implements AoCTask {
    Snailnumber[] numbers;
    String[] lines;

    public static void main(String[] args) {
        Executor ex = new Executor();

        AoCTask small = new Day18();
        AoCTask big = new Day18();

        ex.execute_timed(small, "inputs/input_s_18", 10);
        System.out.println();
        ex.execute_timed(big, "inputs/input_18", 100);

    }

    @Override
    public void readInput(Scanner scr, int n) {
        this.numbers = new Snailnumber[n];
        this.lines = new String[n];
        for (int i = 0; i < n; i++) {
            String line = scr.nextLine();
            this.lines[i] = line;
            this.numbers[i] = parseLine(line, 0);
        }

    }

    public Snailnumber parseLine(String line, int depth) {

        String[] leftright = split(line);

        Snailnumber left = null;
        Snailnumber right = null;
        int leftv = -1;
        int rightv = -1;

        boolean leftc = leftright[0].charAt(0) == '[';
        boolean rightc = leftright[1].charAt(0) == '[';

        if (leftc) {
            left = parseLine(leftright[0], depth + 1);
        } else {
            leftv = Integer.parseInt(leftright[0]);
        }

        if (rightc) {
            right = parseLine(leftright[1], depth + 1);
        } else {
            rightv = Integer.parseInt(leftright[1]);
        }

        Snailnumber res = new Snailnumber(left, right, leftv, rightv);
        if (left != null)
            left.setParent(res);
        if (right != null)
            right.setParent(res);

        return res;
    }

    public String[] split(String line) {
        String l = line.substring(1, line.length() - 1);

        if (l.charAt(0) != '[') {
            l = l.replaceAll("\\s+", "");
            return l.split(",", 2);
        }

        int c = 1;
        int i = 1;
        while (c != 0) {
            if (l.charAt(i) == '[')
                c++;
            else if (l.charAt(i) == ']')
                c--;
            i++;
        }

        return new String[] { l.substring(0, i), l.substring(i + 1) };
    }

    @Override
    public String task1() {
        Snailnumber res = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            res = add(res, numbers[i]);
        }
        System.out.println(res);
        return "" + res.magnitude();
    }

    @Override
    public String task2() {
        int res = 0;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {

                if (i == j) {
                    continue;
                }
                Snailnumber sni = parseLine(lines[i], 0);
                Snailnumber snj = parseLine(lines[j], 0);

                res = Math.max(res, add(sni, snj).magnitude());
            }
        }
        return "" + res;
    }

    public Snailnumber add(Snailnumber a, Snailnumber b) {

        Snailnumber res = new Snailnumber(a, b, -1, -1);
        a.parent = res;
        b.parent = res;

        while (reduce(res)) {
        }

        return res;
    }

    public boolean reduce(Snailnumber sn) {
        if (sn.explode()) {
            return true;
        }
        if (sn.split()) {
            return true;
        }
        return false;
    }

    public class Snailnumber {
        Snailnumber left, right;
        Snailnumber parent;
        int leftv, rightv;
        boolean leaf;

        Snailnumber(Snailnumber left, Snailnumber right, int leftv, int rightv) {
            this.left = left;
            this.right = right;
            this.leftv = leftv;
            this.rightv = rightv;
            this.leaf = !(this.leftv == -1 || this.rightv == -1);
        }

        @Override
        public String toString() {
            return "[" + (leftv == -1 ? left.toString() : leftv) + "," + (rightv == -1 ? right.toString() : rightv)
                    + "]";
        }

        public int magnitude() {
            int res = 0;
            res += ((left != null) ? left.magnitude() : leftv) * 3;
            res += ((right != null) ? right.magnitude() : rightv) * 2;

            return res;
        }

        public void setParent(Snailnumber par) {
            this.parent = par;
        }

        public int depth() {
            int i = 0;
            Snailnumber curr = parent;
            while (curr != null) {
                i++;
                curr = curr.parent;
            }
            return i;
        }

        public boolean explode() {
            Snailnumber expl = findExploding();
            if (expl == null)
                return false;
            expl.incrementLeft();
            expl.incrementRight();

            if (expl.parent.right == expl) {
                expl.parent.right = null;
                expl.parent.rightv = 0;
            } else if (expl.parent.left == expl) {
                expl.parent.left = null;
                expl.parent.leftv = 0;
            }

            expl.parent.leaf = !(expl.parent.leftv == -1 || expl.parent.rightv == -1);
            return true;
        }

        public void incrementLeft() {
            int val = this.leftv;
            if (!this.leaf) {
                return;
            }
            Snailnumber currp = parent;
            Snailnumber curr = this;
            // move up
            while (currp.left == curr) {

                currp = currp.parent;
                curr = curr.parent;
                // ended up at the root;
                if (currp == null) {
                    break;
                }
            }

            if (currp != null) {

                if (currp.left == null) {
                    currp.leftv += val;
                    return;
                }
                curr = currp.left;
                while (curr.right != null) {
                    curr = curr.right;
                }
                curr.rightv += val;

            }
        }

        public void incrementRight() {
            int val = this.rightv;
            if (!this.leaf) {
                return;
            }
            Snailnumber currp = parent;
            Snailnumber curr = this;
            // move up
            while (currp.right == curr) {
                currp = currp.parent;
                curr = curr.parent;
                // ended up at the root;
                if (currp == null) {
                    break;
                }
            }

            if (currp != null) {
                if (currp.right == null) {
                    currp.rightv += val;
                    return;
                }
                curr = currp.right;
                while (curr.left != null) {
                    curr = curr.left;
                }
                curr.leftv += val;

            }
        }

        public Snailnumber findExploding() {
            if (this.leaf) {
                if (this.isExploding()) {
                    return this;
                }
                return null;
            } else if (this.left != null && left.findExploding() != null) {
                return left.findExploding();
            } else if (this.right != null && right.findExploding() != null) {
                return right.findExploding();
            }

            return null;
        }

        public boolean isExploding() {
            return this.depth() >= 4;
        }

        public boolean split() {
            Snailnumber split = findSplitting();

            if (split == null)
                return false;

            if (split.leftv >= 10) {
                int val = split.leftv;
                int lv = val / 2;
                int rv = val / 2 + (val % 2 != 0 ? 1 : 0);
                Snailnumber newleft = new Snailnumber(null, null, lv, rv);
                newleft.setParent(split);
                split.left = newleft;
                split.leftv = -1;
            } else if (split.rightv >= 10) {
                int val = split.rightv;
                int lv = val / 2;
                int rv = val / 2 + (val % 2 != 0 ? 1 : 0);

                Snailnumber newright = new Snailnumber(null, null, lv, rv);
                newright.setParent(split);
                split.right = newright;
                split.rightv = -1;
            }

            split.leaf = !(split.leftv == -1 || split.rightv == -1);
            return true;
        }

        public Snailnumber findSplitting() {

            if (this.left != null && this.left.findSplitting() != null) {
                return this.left.findSplitting();
            } else if (this.leftv > 9) {
                return this;
            } else if (this.right != null && this.right.findSplitting() != null) {
                return this.right.findSplitting();
            } else if (this.rightv > 9) {
                return this;
            }
            return null;
        }
    }

}