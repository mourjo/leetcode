package me.mourjo.y22;

/*
https://leetcode.com/problems/sum-of-distances-in-tree/

An undirected, connected tree with N nodes labelled 0...N-1 and N-1 edges are given.

The ith edge connects nodes edges[i][0] and edges[i][1] together.

Return a list ans, where ans[i] is the sum of the distances between node i and all other nodes.

Example 1:

Input: N = 6, edges = [[0,1],[0,2],[2,3],[2,4],[2,5]]
Output: [8,12,6,10,10,10]
Explanation:
Here is a diagram of the given tree:
  0
 / \
1   2
   /|\
  3 4 5
We can see that dist(0,1) + dist(0,2) + dist(0,3) + dist(0,4) + dist(0,5)
equals 1 + 1 + 2 + 2 + 2 = 8.  Hence, answer[0] = 8, and so on.
Note: 1 <= N <= 10000
 */

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class SumDistancesTree {

    public static int[] sumOfDistancesInTreeMemoryOverhead(int N, int[][] edges) {
        if (N == 1) {
            return new int[]{0};
        }
        int[] distances = new int[N];
        HashSet<Integer> completed = new HashSet<>();
        HashSet<Integer>[] processing = new HashSet[N];
        HashSet<Integer>[] processed = new HashSet[N];
        ArrayList<Integer>[] adjacency = new ArrayList[N];
        int hops = 0;

        for (int[] edge : edges) {
            int start = edge[0];
            int end = edge[1];
            if (adjacency[start] == null) {
                adjacency[start] = new ArrayList<>(N - 1);
            }
            adjacency[start].add(end);
            if (adjacency[end] == null) {
                adjacency[end] = new ArrayList<>(N - 1);
            }
            adjacency[end].add(start);
        }

        for (int i = 0; i < N; i++) {
            processing[i] = new HashSet<>();
            processing[i].add(i);
            processed[i] = new HashSet<>();
        }

        while (completed.size() < N) {
            for (int i = 0; i < N; i++) {
                if (!completed.contains(i)) {
                    HashSet<Integer> newProcess = new HashSet<>();
                    for (int node : processing[i]) {
                        for (int neighbour : adjacency[node]) {
                            if (!processed[i].contains(neighbour)) {
                                newProcess.add(neighbour);
                            }
                        }
                        distances[i] += hops;
                        processed[i].add(node);
                    }
                    processing[i] = newProcess;
                    if (processed[i].size() == N) {
                        completed.add(i);
                    }
                }
            }
            hops++;
        }

        return distances;
    }

    // less memory overhead but slow
    public static int[] sumOfDistancesInTreeSlow(int N, int[][] edges) {
        if (edges.length == 0) {
            return new int[]{0};
        }
        if (N == 0) {
            return new int[]{};
        }
        // dfs every node
        HashSet<Integer>[] adjacency = new HashSet[N];

        for (int[] edge : edges) {
            int start = edge[0];
            int end = edge[1];
            if (adjacency[start] == null) {
                adjacency[start] = new HashSet<>();
            }
            if (adjacency[end] == null) {
                adjacency[end] = new HashSet<>();
            }

            adjacency[start].add(end);
            adjacency[end].add(start);
        }

        int[] result = new int[N];

        for (int root = 0; root < N; root++) {
            result[root] = naiveDfs(adjacency, root, -1, 1);
        }
        return result;
    }

    public static int naiveDfs(HashSet<Integer>[] adjacency, int root, int previous, int offset) {
        int res = 0;
        for (int neighbour : adjacency[root]) {
            if (neighbour != root && neighbour != previous) {
                res += offset + naiveDfs(adjacency, neighbour, root, offset + 1);
            }
        }
        return res;
    }

    public static int[] sumOfDistancesInTree(int N, int[][] edges) {
        if (edges.length == 0) {
            return new int[]{0};
        }
        if (N == 0) {
            return new int[]{};
        }

        LinkedList<Integer>[] adjacency = new LinkedList[N];

        for (int[] edge : edges) {
            int start = edge[0];
            int end = edge[1];
            if (adjacency[start] == null) {
                adjacency[start] = new LinkedList<>();
            }
            if (adjacency[end] == null) {
                adjacency[end] = new LinkedList<>();
            }

            adjacency[start].add(end);
            adjacency[end].add(start);
        }

        int[] result = new int[N];
        int[] counts = new int[N];

        // calculate the actual distance from arbitrary root 0
        // count the number of nodes in each subtree
        result[0] = postorderDFS(adjacency, counts, 0, -1, 1);

        // for each node that is not already processed,
        // the sum of distances = previousRoot's sum - this node's count + (N - this node's count)
        //
        // when choosing a new root, all nodes under this root get promoted by 1 (one
        // more step away from the new root)
        //
        // all remaining nodes get demoted by 1

        preorderDFS(adjacency, counts, 0, result, -1);

        return result;
    }

    public static void preorderDFS(List<Integer>[] adjacency, int[] counts, int root, int[] result,
        int previous) {
        // Pre-order
        for (int newRoot : adjacency[root]) {
            if (newRoot != previous) {
                result[newRoot] =
                    result[root] - counts[newRoot] + (adjacency.length - counts[newRoot]);
                preorderDFS(adjacency, counts, newRoot, result, root);
            }
        }
    }

    public static int postorderDFS(List<Integer>[] adjacency, int[] counts, int root, int previous,
        int offset) {
        // Post-order
        int res = 0;
        counts[root]++;
        for (int node : adjacency[root]) {
            if (node != root && node != previous) {
                res += offset + postorderDFS(adjacency, counts, node, root, offset + 1);
                counts[root] += counts[node];
            }
        }
        return res;
    }

    public static void main(String[] args) {

        assertArrayEquals(
            new int[]{0},
            sumOfDistancesInTree(1,
                new int[][]{}));

        assertArrayEquals(
            new int[]{1, 1},
            sumOfDistancesInTree(2,
                new int[][]{
                    {0, 1}}));

        assertArrayEquals(
            new int[]{8, 12, 6, 10, 10, 10},
            sumOfDistancesInTree(6,
                new int[][]{
                    {0, 1},
                    {0, 2},
                    {2, 3},
                    {2, 4},
                    {2, 5}}));

        assertArrayEquals(
            new int[]{10, 6, 10, 10, 8, 12},
            sumOfDistancesInTree(6,
                new int[][]{
                    {0, 1},
                    {5, 4},
                    {2, 1},
                    {4, 1},
                    {3, 1}
                }));

    }
}
