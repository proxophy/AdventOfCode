package aoc2021;

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
            this.numbers[i] = parseLine(line);
        }
    }

    public Snailnumber parseLine(String line) {
        String[] leftright = split(line);

        Snailnumber left = null;
        Snailnumber right = null;
        int leftv = -1;
        int rightv = -1;

        boolean leftc = leftright[0].charAt(0) == '[';
        boolean rightc = leftright[1].charAt(0) == '[';

        if (leftc) {
            left = parseLine(leftright[0]);
        } else {
            leftv = Integer.parseInt(leftright[0]);
        }

        if (rightc) {
            right = parseLine(leftright[1]);
        } else {
            rightv = Integer.parseInt(leftright[1]);
        }

        Snailnumber res = new Snailnumber(left, right, leftv, rightv);
        if (left != null)
            left.parent = res;
        if (right != null)
            right.parent = res;

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
                res = Math.max(res, add(numbers[i], numbers[j]).magnitude());
            }
        }
        return "" + res;
    }

    public Snailnumber add(Snailnumber a, Snailnumber b) {
        Snailnumber res = new Snailnumber(-1, -1);
        Snailnumber ac = a.copy(res);
        Snailnumber bc = b.copy(res);
        res.left = ac;
        res.right = bc;

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

        Snailnumber(int leftv, int rightv) {
            this.left = this.right = null;
            this.leftv = leftv;
            this.rightv = rightv;
            this.leaf = !(this.leftv == -1 || this.rightv == -1);
        }

        Snailnumber(Snailnumber left, Snailnumber right, int leftv, int rightv) {
            this.left = left;
            this.right = right;
            this.leftv = leftv;
            this.rightv = rightv;
            this.leaf = !(this.leftv == -1 || this.rightv == -1);
        }

        Snailnumber copy(Snailnumber p) {
            Snailnumber cop = new Snailnumber(leftv, rightv);
            cop.parent = p;
            Snailnumber nl = null, nr = null;
            if (left != null) {
                nl = left.copy(cop);
                cop.left = nl;
            }
            if (right != null) {
                nr = right.copy(cop);
                cop.right = nr;
            }
            return cop;
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
            if (depth() == 4) {
                incrementLeft();
                incrementRight();
                if (parent.right == this) {
                    parent.right = null;
                    parent.rightv = 0;
                } else if (parent.left == this) {
                    parent.left = null;
                    parent.leftv = 0;
                }
                parent.leaf = !(parent.leftv == -1 || parent.rightv == -1);
            }

            boolean l = false;
            boolean r = false;
            if (left != null)
                l = left.explode();
            if (right != null)
                r = right.explode();

            return l || r;
        }

        public void incrementLeft() {
            int val = this.leftv;
            if (!this.leaf) {
                return;
            }
            Snailnumber curr = this;
            // move up
            while (curr.parent.left == curr) {
                curr = curr.parent;
                // ended up at the root;
                if (curr.parent == null) {
                    return;
                }
            }

            if (curr.parent.left == null) {
                curr.parent.leftv += val;
                return;
            }
            curr = curr.parent.left;
            while (curr.right != null) {
                curr = curr.right;
            }
            curr.rightv += val;
        }

        public void incrementRight() {
            int val = this.rightv;
            if (!this.leaf) {
                return;
            }
            Snailnumber curr = this;
            // move up
            while (curr.parent.right == curr) {
                curr = curr.parent;
                // ended up at the root;
                if (curr.parent == null) {
                    return;
                }
            }

            if (curr.parent.right == null) {
                curr.parent.rightv += val;
                return;
            }
            curr = curr.parent.right;
            while (curr.left != null) {
                curr = curr.left;
            }
            curr.leftv += val;
        }

        public boolean split() {
            if (left != null && left.split())
                return true;

            if (leftv >= 10) {
                int val = leftv;
                Snailnumber newleft = new Snailnumber(val / 2, val - val / 2);
                newleft.parent = this;
                left = newleft;
                leftv = -1;
                this.leaf = false;
                return true;
            }

            if (right != null && right.split())
                return true;

            if (rightv >= 10) {
                int val = rightv;

                Snailnumber newright = new Snailnumber(val / 2, val - val / 2);
                newright.parent = this;
                right = newright;
                rightv = -1;
                this.leaf = false;
                return true;
            }
            return false;
        }

    }

}