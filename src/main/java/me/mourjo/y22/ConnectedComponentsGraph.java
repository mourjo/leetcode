package me.mourjo.y22;

import java.util.*;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/

Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of
nodes), write a function to find the number of connected components in an undirected graph.

Example 1:

Input: n = 5 and edges = [[0, 1], [1, 2], [3, 4]]

     0          3
     |          |
     1 --- 2    4

Output: 2
Example 2:

Input: n = 5 and edges = [[0, 1], [1, 2], [2, 3], [3, 4]]

     0           4
     |           |
     1 --- 2 --- 3

Output:  1
Note:
You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1]
is the same as [1, 0] and thus will not appear together in edges.
 */
public class ConnectedComponentsGraph {

    public static int countComponentsDFS(int n, int[][] edges) {
        LinkedList<Integer>[] adjacency = new LinkedList[n];
        for (int[] edge : edges) {
            if (adjacency[edge[0]] == null) {
                adjacency[edge[0]] = new LinkedList<Integer>();
            }
            if (adjacency[edge[1]] == null) {
                adjacency[edge[1]] = new LinkedList<Integer>();
            }

            adjacency[edge[0]].add(edge[1]);
            adjacency[edge[1]].add(edge[0]);
        }

        HashSet<Integer> visited = new HashSet<>(n);
        int connected = 0;
        for (int i = 0; i < n && visited.size() < n; i++) {
            if (!visited.contains(i)) {
                dfs(adjacency, visited, i);
                connected++;
            }
        }

        return connected;
    }

    public static void dfs(List<Integer>[] adjacency, Set<Integer> visited, int node) {
        visited.add(node);
        if (adjacency[node] == null) {
            return;
        }
        for (int next : adjacency[node]) {
            if (!visited.contains(next)) {
                dfs(adjacency, visited, next);
            }
        }
    }

    // union find
    public static int findAncestor(int[] parent, int n) {
        int k = n;
        while (k != parent[k]) {
            parent[k] = parent[parent[k]]; // short circuit
            k = parent[k];
        }
        parent[n] = k;
        return k;
    }

    public static int countComponents(int n, int[][] edges) {
        if (edges.length == 0) {
            return n;
        }
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        int connected = n;

        for (int[] edge : edges) {
            int minV = Math.min(edge[0], edge[1]);
            int maxV = Math.max(edge[0], edge[1]);

            int minP = findAncestor(parent, minV);
            int maxP = findAncestor(parent, maxV);

            if (minP != maxP) {
                connected--;
                parent[maxP] = minP;
                parent[maxV] = minP;
            }
        }
        return connected;
    }

    public static void main(String[] args) {
        BiFunction<Integer, int[][], Integer>[] fns = new BiFunction[2];
        fns[0] = ConnectedComponentsGraph::countComponents;
        fns[1] = ConnectedComponentsGraph::countComponents;

        for (int i = 0; i < 10; i++) {
            for (int impl = 0; impl < 2; impl++) {

                assertEquals(2, fns[impl].apply(5,
                        shuffle(new int[][]{
                                {0, 1},
                                {1, 2},
                                {3, 4}
                        })));

                assertEquals(1, fns[impl].apply(5,
                        shuffle(new int[][]{
                                {0, 1},
                                {1, 2},
                                {2, 3},
                                {3, 4}
                        })));

                assertEquals(5, fns[impl].apply(5,
                        shuffle(new int[][]{})));

                assertEquals(3, fns[impl].apply(5,
                        shuffle(new int[][]{
                                {0, 1},
                                {3, 4}
                        })));
            }
        }
    }

    public static int[][] shuffle(int[][] x) {
        Random r = new Random();
        for (int i = 0; i < x.length; i++) {
            int[] t = x[i];
            int j = r.nextInt(x.length);
            x[i] = x[j];
            x[j] = t;
            if (r.nextBoolean()) {
                int t2 = x[j][0];
                x[j][0] = x[j][1];
                x[j][1] = t2;
            }
        }
        return x;
    }
}
