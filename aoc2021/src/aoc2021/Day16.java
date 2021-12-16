package aoc2021;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Day16 implements AoCTask {
    String[] lines;
    private static HashMap<String, String> hexToBin = new HashMap<String, String>();

    public static void main(String[] args) {
        Executor ex = new Executor();

        AoCTask small = new Day16();
        AoCTask big = new Day16();

        ex.execute_timed(small, "inputs/input_s_16", 15);
        System.out.println();
        ex.execute_timed(big, "inputs/input_16", 1);

    }

    @Override
    public void readInput(Scanner scr, int n) {
        lines = new String[n];
        for (int i = 0; i < n; i++) {
            lines[i] = scr.nextLine();
        }
        hexToBinFill();

    }

    public void hexToBinFill() {
        File f = new File("inputs/hexToBin");
        try {
            Scanner sc = new Scanner(f);
            for (int i = 0; i < 16; i++) {
                String k = sc.next();
                sc.next();
                String v = sc.next();
                hexToBin.put(k, v);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String task1() {
        return "" + parsePacket(binaryFormat(lines[0])).versionSum();
    }

    public Packet parsePacket(String l) {
        int v = Integer.parseInt(l.substring(0, 3), 2);
        int id = Integer.parseInt(l.substring(3, 6), 2);

        Packet p = new Packet(v, id);
        if (p.lit) { // id == 4
            String litValS = getLitVal(l.substring(6));
            p.encodLen = litValS.length() + litValS.length() / 4 + 6;
            p.litVal = Long.parseLong(litValS, 2);

        } else {

            boolean ltID = l.charAt(6) == '1';
            if (ltID) {
                int num = Integer.parseInt(l.substring(7, 18), 2);
                p.encodLen = parseSubpackets(l.substring(18), num, p) + 18;
            } else {
                int len = Integer.parseInt(l.substring(7, 22), 2);
                parseSubpackets(l.substring(22, 22 + len), p);
                p.encodLen = len + 22;
            }

        }

        return p;
    }

    // total length of encoding of sub-packets known
    public void parseSubpackets(String ls, Packet p) {
        int length = ls.length();
        int i = 0;
        while (i < length - 1) {
            Packet sp = parsePacket(ls.substring(i));
            i += sp.encodLen;
            p.subpackets.add(sp);
        }

    }

    // total number of sub-packets known
    public int parseSubpackets(String ls, int n, Packet p) {
        int c = 0;
        for (int i = 0; i < n; i++) {
            Packet sp = parsePacket(ls.substring(c));
            c += sp.encodLen;
            p.subpackets.add(sp);
        }

        return c;

    }

    public String getLitVal(String lv) {
        String num = "";
        int i = 0;
        char c = lv.charAt(i);

        while (c != '0') {
            num += lv.substring(i + 1, i + 5);
            i += 5;
            c = lv.charAt(i);
        }

        num += lv.substring(i + 1, i + 5);
        return num;

    }

    public String binaryFormat(String line) {
        String[] elms = line.split("");
        String sol = "";
        for (String e : elms) {
            sol += hexToBin.get(e);
        }

        return sol;
    }

    @Override
    public String task2() {

        return "" + parsePacket(binaryFormat(lines[0])).val();
    }

    public class Packet {
        int version;
        int id;
        boolean lit;
        long litVal;
        ArrayList<Packet> subpackets = new ArrayList<Packet>();
        int encodLen; // how long the encoding of that packet is

        Packet(int v, int id) {
            this.version = v;
            this.id = id;
            this.lit = this.id == 4;
        }

        @Override
        public String toString() {
            // return toStringHelper(0);
            return "(" + version + ", " + id + "): " + val();
        }

        public String toStringHelper(int depth) {
            String res = "";

            for (int i = 0; i < depth; i++) {
                res += " - ";
            }

            res += "(" + version + ", " + id + ") " + this.val();

            res += "\n";

            for (Packet sp : subpackets) {
                res += sp.toStringHelper(depth + 1);
            }
            return res;
        }

        public int versionSum() {
            int sum = this.version;
            for (Packet sp : subpackets) {
                sum += sp.versionSum();
            }

            return sum;
        }

        public long val() {
            switch (this.id) {
                case 0:
                    return this.sum();
                case 1:
                    return this.product();
                case 2:
                    return this.minimum();
                case 3:
                    return this.maximum();
                case 4:
                    return this.litVal;
                case 5:
                    return this.subpackets.get(0).val() > this.subpackets.get(1).val() ? 1 : 0;
                case 6:
                    return this.subpackets.get(0).val() < this.subpackets.get(1).val() ? 1 : 0;
                case 7:
                    return this.subpackets.get(0).val() == this.subpackets.get(1).val() ? 1 : 0;
            }

            return 0;
        }

        public long sum() {
            if (this.id == 4) {
                return this.litVal;
            }

            long res = 0;
            for (Packet sp : subpackets) {
                res += sp.val();
            }
            return res;
        }

        public long product() {
            if (this.id == 4) {
                return this.litVal;
            }

            long res = 1;
            for (Packet sp : subpackets) {
                res *= sp.val();
            }

            return res;
        }

        public long minimum() {
            if (this.id == 4) {
                return this.litVal;
            }

            long res = Long.MAX_VALUE;
            for (Packet sp : subpackets) {
                res = Long.min(res, sp.val());
            }

            return res;
        }

        public long maximum() {
            if (this.id == 4) {
                return this.litVal;
            }

            long res = Long.MIN_VALUE;
            for (Packet sp : subpackets) {
                res = Long.max(res, sp.val());
            }
            return res;
        }
    }
}
