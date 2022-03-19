package aoc2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Day24 implements AoCTask {

    Instruction[] instr;

    public static void main(String[] args) {
        Executor ex = new Executor();

        AoCTask small = new Day24();
        AoCTask big = new Day24();

        // ex.execute_timed(small, "inputs/input_s_24", 11);
        // System.out.println();
        ex.execute_timed(big, "inputs/input_24", 252);
    }

    @Override
    public void readInput(Scanner scr, int n) {

        instr = new Instruction[n];
        for (int i = 0; i < n; i++) {
            String line = scr.nextLine();
            instr[i] = parseInstruction(line);
        }
    }

    public Instruction parseInstruction(String line) {
        Scanner lscr = new Scanner(line);
        String instr = lscr.next();
        if (instr.equals("inp")) {
            return new Instruction(instr, lscr.next());
        }

        String a = lscr.next();
        if (lscr.hasNextInt()) {
            return new Instruction(instr, a, lscr.nextInt());
        } else {
            return new Instruction(instr, a, lscr.next());
        }

    }

    @Override
    public String task1() {
        long[] a = new long[14];
        long[] b = new long[14];
        long[] c = new long[14];

        for (int i = 0; i < 14; i++) {
            a[i] = instr[i * 18 + 4].valb;
            b[i] = instr[i * 18 + 15].valb;
            c[i] = instr[i * 18 + 5].valb;
        }

        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
        System.out.println(Arrays.toString(c));

        return null;

        // HashMap<String, Long> memory = new HashMap<String, Long>();
        // memory.put("w", 0l);
        // memory.put("x", 0l);
        // memory.put("y", 0l);
        // memory.put("z", 0l);
        // int[] inp = new int[] { 100000 };
        // InputReader ir = new InputReader(inp);
        // int i = 18;
        // while (i + 1 <= instr.length - 1 && !instr[i + 1].type.equals("inp")) {
        // instr[i].apply(memory, ir);
        // i++;
        // }

        // System.out.println(memory + " " + i);

    }

    @Override
    public String task2() {
        // TODO Auto-generated method stub
        return null;
    }

    public class Instruction {
        String type;
        String arga;
        String argb;
        long valb;

        public Instruction(String type, String arga, String argb) {
            this.type = type;
            this.arga = arga;
            this.argb = argb;
        }

        public Instruction(String type, String arga, long valb) {
            this.type = type;
            this.arga = arga;
            this.valb = valb;
        }

        public Instruction(String type, String arga) {
            this.type = type;
            this.arga = arga;
        }

        public void apply(HashMap<String, Long> memory, InputReader input) {
            long b = argb != null ? memory.get(argb) : valb;
            if (type.equals("inp")) {
                long x = input.read();
                memory.put(arga, x);
            } else if (type.equals("add"))
                memory.put(arga, memory.get(arga) + b);
            else if (type.equals("mul"))
                memory.put(arga, memory.get(arga) * b);
            else if (type.equals("div"))
                memory.put(arga, memory.get(arga) / b);
            else if (type.equals("mod"))
                memory.put(arga, memory.get(arga) % b);
            else if (type.equals("eql")) {
                long res = memory.get(arga).equals(b) ? 1 : 0;
                memory.put(arga, res);
            }
        }

        @Override
        public String toString() {
            String out = type + " " + arga;
            if (!type.equals("inp")) {
                out += " " + (argb != null ? argb : valb);
            }

            return out;
        }

    }

    public class InputReader {
        int[] input;
        int index;

        public InputReader(int[] input) {
            this.input = input;
            this.index = -1;
        }

        public int read() {
            index++;
            return input[index];
        }

    }

}
