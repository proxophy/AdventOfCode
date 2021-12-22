package aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Day19 implements AoCTask {

    BScanner[] scanners;
    BScanner[] scanners_oriented;
    ArrayList<ArrayList<Integer>> transformations;
    int[][] posrel;

    public static void main(String[] args) {
        Executor ex = new Executor();

        AoCTask small = new Day19();
        AoCTask big = new Day19();

        ex.execute_timed(small, "inputs/input_s_19", 2);
        // System.out.println();
        // ex.execute_timed(big, "inputs/input_19", 0);

    }

    @Override
    public void readInput(Scanner scr, int n) {
        int i = 0;
        scanners = new BScanner[n];
        while (i < n) {
            scr.nextLine();
            String line = scr.nextLine();
            BScanner curr = new BScanner();

            while (!line.equals("")) {
                String[] elms = line.split(",");
                Integer[] pos = new Integer[elms.length];
                for (int j = 0; j < elms.length; j++) {
                    pos[j] = Integer.parseInt(elms[j]);
                }
                curr.beacons.add(pos);
                if (!scr.hasNextLine()) {
                    break;
                }
                line = scr.nextLine();
            }

            scanners[i] = curr;
            i++;
        }

    }

    @Override
    public String task1() {
        this.scanners_oriented = new BScanner[scanners.length];
        this.posrel = new int[scanners.length][];
        this.posrel[0] = new int[] { 0, 0, 0 };
        this.transformations = new ArrayList<ArrayList<Integer>>(scanners.length);

        for (int i = 0; i < scanners.length; i++) {
            this.transformations.add(new ArrayList<Integer>());
            // this.scanners_oriented[i] = scanners[i];
        }

        this.scanners_oriented[0] = scanners[0];

        for (int i = 0; i < scanners.length; i++) {
            for (int j = 0; j < scanners.length; j++) {
                if (i == j || posrel[j] != null)
                    continue;

                int[] prel = posrel(i, j);

                if (posrel[i] == null) {
                    continue;
                }
                if (prel == null) {
                    continue;
                }

                posrel[j] = new int[3];
                for (int k = 0; k < 3; k++) {
                    posrel[j][k] = (prel[k] + posrel[i][k]);
                }

            }

        }

        for (int i = 0; i < scanners.length; i++) {
            BScanner bs = scanners[i];

            for (int t : transformations.get(i)) {
                bs = bs.rotate(t);
            }

            bs = bs.translate(posrel[i][0], posrel[i][1], posrel[i][2]);
            // System.out.println("posrel " + Arrays.toString(posrel[i]));
            scanners_oriented[i] = bs;

            // if (scanners_oriented[i] == null) {
            // System.out.println(scanners[i]);
            // }
        }

        ArrayList<Integer[]> allbeacons = new ArrayList<Integer[]>();

        for (BScanner bs : scanners_oriented) {
            for (Integer[] beacon : bs.beacons) {
                if (!listContains(allbeacons, beacon)) {
                    allbeacons.add(beacon);
                } else {
                    System.out.println(Arrays.toString(beacon));
                }
            }
        }

        // printList(allbeacons);

        return "" + allbeacons.size();
    }

    public int[] posrel(int ai, int bi) {
        BScanner a = scanners[ai];
        BScanner b = scanners[bi];

        int[] rot = overlappingRot(a, b);
        if (rot[0] == -1) {
            return null;
        }

        int i = rot[1];
        int j = rot[2];

        transformations.get(bi).add(rot[0]);
        transformations.get(bi).addAll(transformations.get(ai));

        for (int t : transformations.get(bi)) {
            b = b.rotate(t);
        }

        for (int t : transformations.get(ai)) {
            a = a.rotate(t);
        }

        Integer[] ac = a.beacons.get(i);
        Integer[] bc = b.beacons.get(j);

        int[] sol = new int[] { ac[0] - bc[0], ac[1] - bc[1], ac[2] - bc[2] };

        return sol;
    }

    public int[] overlappingRot(BScanner a, BScanner b) {
        // int maxx = Math.min(a, b)
        int r = -1;
        int ai = -1;
        int bi = -1;
        for (int x = 0; x < 48; x++) {
            BScanner bc = b.rotate(x);
            for (int i = 0; i < a.beacons.size(); i++) {
                for (int j = 0; j < b.beacons.size(); j++) {
                    if (isOverlappingOnPoints(a, bc, i, j)) {
                        r = x;
                        ai = i;
                        bi = j;
                    }
                }
            }
        }

        return new int[] { r, ai, bi };
    }

    public boolean isOverlappingOnPoints(BScanner a, BScanner b, int ao, int bo) {
        ArrayList<Integer[]> adist = dist(a, ao);
        ArrayList<Integer[]> bdist = dist(b, bo);

        int count = 0;
        for (Integer[] dist : adist) {
            if (listContains(bdist, dist)) {
                count++;
            }
        }

        return count >= 12;
    }

    public static boolean[] whichMirror(int m) {
        switch (m) {
            case 0:
                return new boolean[] { false, false, false };
            case 1:
                return new boolean[] { false, false, true };
            case 2:
                return new boolean[] { false, true, false };
            case 3:
                return new boolean[] { false, true, true };
            case 4:
                return new boolean[] { true, false, false };
            case 5:
                return new boolean[] { true, false, true };
            case 6:
                return new boolean[] { true, true, false };
            case 7:
                return new boolean[] { true, true, true };

        }
        return null;
    }

    public Integer[] changeOr(Integer[] arr, int i) {
        Integer[] arrc = Arrays.copyOf(arr, arr.length);
        Integer[] cop = new Integer[arr.length];
        boolean[] mirr = whichMirror(i % 8);

        arrc[0] *= (mirr[0] ? -1 : 1);
        arrc[1] *= (mirr[1] ? -1 : 1);
        arrc[2] *= (mirr[2] ? -1 : 1);

        if (i / 8 == 0) { // xzy
            cop[0] = arrc[0];
            cop[1] = arrc[2];
            cop[2] = arrc[1];

        } else if (i / 8 == 1) { // zxy
            cop[0] = arrc[2];
            cop[1] = arrc[0];
            cop[2] = arrc[1];
        } else if (i / 8 == 2) { // yxz
            cop[0] = arrc[1];
            cop[1] = arrc[0];
            cop[2] = arrc[2];
        } else if (i / 8 == 3) { // xyz
            cop[0] = arrc[0];
            cop[1] = arrc[1];
            cop[2] = arrc[2];
        } else if (i / 8 == 4) { // yzx
            cop[0] = arrc[1];
            cop[1] = arrc[2];
            cop[2] = arrc[0];

        } else if (i / 8 == 5) { // zyx
            cop[0] = arrc[2];
            cop[1] = arrc[1];
            cop[2] = arrc[0];
        }

        return cop;
    }

    ArrayList<Integer[]> dist(BScanner sc, int or) {
        ArrayList<Integer[]> res = new ArrayList<Integer[]>();
        Integer[] org = sc.beacons.get(or);
        for (int i = 0; i < sc.beacons.size(); i++) {
            Integer[] curr = sc.beacons.get(i);
            res.add(new Integer[] { org[0] - curr[0], org[1] - curr[1], org[1] - curr[1] });
        }
        return res;
    }

    public static boolean listContains(ArrayList<Integer[]> list, Integer[] el) {
        for (Integer[] e : list) {
            if (Arrays.equals(e, el)) {
                return true;
            }
        }
        return false;
    }

    public static void printList(ArrayList<Integer[]> arg) {
        // System.out.print("[ ");
        for (Integer[] k : arg) {
            System.out.println(Arrays.toString(k) + " ");
        }
        // System.out.println("]");
    }

    @Override
    public String task2() {
        // TODO Auto-generated method stub
        return null;
    }

    public class BScanner {
        int index;
        ArrayList<Integer[]> beacons;

        public BScanner() {
            // this.index = index;
            this.beacons = new ArrayList<Integer[]>();
        }

        @Override
        public String toString() {
            String out = "sc " + index + " ";
            for (Integer[] b : beacons) {
                out += Arrays.toString(b) + " ";
            }
            return out;
        }

        public BScanner translate(int x, int y, int z) {
            BScanner res = new BScanner();
            for (Integer[] b : beacons) {
                Integer[] nb = Arrays.copyOf(b, b.length);
                nb[0] += x;
                nb[1] += y;
                nb[2] += z;
                res.beacons.add(nb);
            }
            return res;
        }

        public BScanner rotate(int or) {
            BScanner res = new BScanner();
            for (Integer[] b : beacons) {
                Integer[] nb = changeOr(b, or);
                res.beacons.add(nb);
            }
            return res;
        }

    }

}
