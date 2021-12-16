package aoc2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Day15 implements AoCTask {
    int[][] oggrid;
    int m;
    int n;

    public static void main(String[] args) {
        Executor ex = new Executor();

        AoCTask small = new Day15();
        AoCTask big = new Day15();

        ex.execute_timed(small, "inputs/input_s_15", 10);
        System.out.println();
        ex.execute_timed(big, "inputs/input_15", 100);

    }

    @Override
    public void readInput(Scanner scr, int m) {
        oggrid = new int[m][];
        this.m = m;

        // make number grid
        for (int i = 0; i < m; i++) {
            String[] elms = scr.nextLine().split("");
            this.n = elms.length;
            int[] line = new int[this.n];
            for (int j = 0; j < this.n; j++) {
                line[j] = Integer.parseInt(elms[j]);
            }
            oggrid[i] = line;
        }

    }

    @Override
    public String task1() {
        Graph g = makeGraph(oggrid);

        return "" + g.dijsktra(map(0, 0, this.m), map(this.m - 1, this.n - 1, this.m));
    }

    public Graph makeGraph(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        Graph g = new Graph(m * n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i > 0) {
                    g.addEdge(map(i, j, m), map(i - 1, j, m), grid[i - 1][j]);
                }
                if (j < n - 1) {
                    g.addEdge(map(i, j, m), map(i, j + 1, m), grid[i][j + 1]);
                }
                if (i < m - 1) {
                    g.addEdge(map(i, j, m), map(i + 1, j, m), grid[i + 1][j]);
                }
                if (j > 0) {
                    g.addEdge(map(i, j, m), map(i, j - 1, m), grid[i][j - 1]);
                }
            }
        }
        return g;
    }

    public int map(int i, int j, int m) {
        return i * m + j;
    }

    @Override
    public String task2() {
        int[][] newgrid = new int[this.m * 5][this.n * 5];

        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {

                for (int ii = 0; ii < 5; ii++) {
                    for (int jj = 0; jj < 5; jj++) {
                        int newval = oggrid[i][j] + ii + jj;
                        newgrid[ii * this.m + i][jj * this.n + j] = ((newval - 1) % 9) + 1;
                    }
                }

            }
        }

        Graph g = makeGraph(newgrid);

        return "" + g.dijsktra(map(0, 0, m), map(newgrid.length - 1, newgrid[0].length - 1, newgrid.length));
    }

    public class Graph {
        ArrayList<ArrayList<Integer>> edges;
        int[][] weights;
        ArrayList<HashMap<Integer, Integer>> ws;

        int vc; // vertex count
        int ec; // edge count

        Graph(int vc) {
            this.vc = vc;
            this.edges = new ArrayList<ArrayList<Integer>>();
            this.ws = new ArrayList<HashMap<Integer, Integer>>();
            for (int i = 0; i < this.vc; i++) {
                edges.add(new ArrayList<Integer>());
                ws.add(new HashMap<Integer, Integer>());
            }

            this.ec = 0;
        }

        void addEdge(int u, int v, int weight) {
            if (edges.get(u).contains(v)) {
                return;
            }
            edges.get(u).add(v);
            ws.get(u).put(v, weight);
            this.ec++;
        }

        ArrayList<Integer> getEdges(int u) {
            return new ArrayList<Integer>(edges.get(u));
        }

        public int dijsktra(int s, int t) {
            // assumes graph is connected
            int[] dist = new int[this.vc];
            for (int i = 0; i < this.vc; i++) {
                dist[i] = Integer.MAX_VALUE;
            }

            boolean[] visited = new boolean[this.vc];
            dist[s] = 0;

            for (int c = 0; c < this.vc; c++) {
                // could be done more effectively with a priority queue
                int u = findSmallestDist(dist, visited);

                visited[u] = true;
                int newDistance = -1;

                // process all neighbouring nodes of u
                for (int i = 0; i < edges.get(u).size(); i++) {
                    Integer v = edges.get(u).get(i);
                    // proceed only if current node is not in 'visited'
                    if (!visited[v]) {
                        newDistance = dist[u] + ws.get(u).get(v);
                        // compare distances
                        if (newDistance < dist[v])
                            dist[v] = newDistance;
                    }
                }

            }
            return dist[t];
        }

        int findSmallestDist(int[] dist, boolean visited[]) {
            int mini = -1;
            int val = Integer.MAX_VALUE;
            for (int u = 0; u < this.vc; u++) {
                if (visited[u])
                    continue;
                if (dist[u] < val) {
                    val = dist[u];
                    mini = u;
                }
            }
            return mini;
        }

    }

    class Edge {
        int a;
        int b;

        Edge(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

}