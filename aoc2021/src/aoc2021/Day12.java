package aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Day12 implements AoCTask {
    HashMap<String, Integer> mapping;
    ArrayList<ArrayList<Integer>> edges;
    HashMap<Integer, Boolean> upper;
    int n;

    public static void main(String[] args) {
        Executor ex = new Executor();

        AoCTask small = new Day12();
        AoCTask big = new Day12();

        ex.execute_timed(small, "inputs/input_s_12", 0);
        System.out.println();
        ex.execute_timed(big, "inputs/input_12", 10);

        ArrayList<Integer> test = new ArrayList<Integer>();
        test.add(2);
        System.out.println(test.contains(2));
    }

    @Override
    public void readInput(Scanner scr, int n) {
        mapping = new HashMap<String, Integer>();
        edges = new ArrayList<ArrayList<Integer>>();
        upper = new HashMap<Integer, Boolean>();

        int i = 0;
        while (scr.hasNextLine()) {
            String line = scr.nextLine();
            String[] elms = line.split("-");

            if (!mapping.containsKey(elms[0])) {
                mapping.put(elms[0], i);
                upper.put(mapping.get(elms[0]), elms[0].equals(elms[0].toUpperCase()));
                edges.add(new ArrayList<Integer>());
                i++;
            }

            if (!mapping.containsKey(elms[1])) {
                mapping.put(elms[1], i);
                upper.put(mapping.get(elms[1]), elms[1].equals(elms[1].toUpperCase()));
                edges.add(new ArrayList<Integer>());
                i++;
            }

            // edge
            edges.get(mapping.get(elms[0])).add(mapping.get(elms[1]));
            edges.get(mapping.get(elms[1])).add(mapping.get(elms[0]));

        }

        this.n = i;

    }

    @Override
    public String task1() {
        return "" + paths(mapping.get("start"), new boolean[this.n]);
    }

    public int paths(int s, boolean[] visited) {

        if (s == mapping.get("end")) {
            return 1;
        }

        if (isolated(s, visited)) {
            return 0;
        }

        int res = 0;
        if (!upper.get(s)) {
            visited[s] = true;
        }
        for (int i : edges.get(s)) {
            boolean[] vis = Arrays.copyOf(visited, visited.length);
            // if adjacent node is lowercase
            if (!upper.get(i)) {
                if (vis[i])
                    continue;
                else
                    vis[i] = true;
            }
            res += paths(i, vis);
        }

        return res;
    }

    // true if you cannot go anywhere from this node
    public boolean isolated(int u, boolean[] visited) {
        for (int v : edges.get(u)) {
            if (upper.get(v)) {
                return false;
            } else if (!visited[v]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String task2() {
        return "" + paths_2(mapping.get("start"), new boolean[this.n], false);
    }

    // part 2; joker: one small cave been visited twice
    public int paths_2(int s, boolean[] visited, boolean joker) {
        if (s == mapping.get("end")) {
            return 1;
        }

        if (isolated2(s, visited, joker)) {
            return 0;
        }

        int res = 0;

        if (!upper.get(s)) {
            visited[s] = true;
        }
        for (int i : edges.get(s)) {
            boolean[] vis = Arrays.copyOf(visited, visited.length);
            boolean jok = joker;
            if (!upper.get(i)) {
                if (vis[i] && (jok || i == mapping.get("start")))
                    continue;
                else if (vis[i] && !jok) {
                    jok = true;
                }
                vis[i] = true;
            }

            try {
                res += paths_2(i, vis, jok);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return res;
    }

    // true if you cannot go anywhere from this node
    public boolean isolated2(int u, boolean[] visited, boolean joker) {
        for (int v : edges.get(u)) {
            if (upper.get(v)) {
                return false;
            } else if (!visited[v]) {
                return false;
            } else if (visited[v] && !joker && (v != mapping.get("start"))) {
                return false;
            } else if (v == mapping.get("end")) {
                return false;
            }
        }
        return true;
    }
}
