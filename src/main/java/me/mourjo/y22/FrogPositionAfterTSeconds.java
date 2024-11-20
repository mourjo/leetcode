package me.mourjo.y22;
/*
https://leetcode.com/problems/frog-position-after-t-seconds/

Given an undirected tree consisting of n vertices numbered from 1 to n. A frog starts jumping
from the vertex 1. In one second, the frog jumps from its current vertex to another unvisited
vertex if they are directly connected. The frog can not jump back to a visited vertex. In case
the frog can jump to several vertices it jumps randomly to one of them with the same probability,
otherwise, when the frog can not jump to any unvisited vertex it jumps forever on the same vertex.

The edges of the undirected tree are given in the array edges, where edges[i] = [fromi, toi]
means that exists an edge connecting directly the vertices fromi and toi.

Return the probability that after t seconds the frog is on the vertex target.

Example 1:
1 - 2,3,7
2 - 4,6
3 - 5
Input: n = 7, edges = [[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]], t = 2, target = 4
Output: 0.16666666666666666
Explanation: The figure above shows the given graph. The frog starts at vertex 1, jumping with
1/3 probability to the vertex 2 after second 1 and then jumping with 1/2 probability to vertex
4 after second 2. Thus the probability for the frog is on the vertex 4 after 2 seconds is
1/3 * 1/2 = 1/6 = 0.16666666666666666.


Example 2:
1 - 2,3,7
2 - 4,6
3 - 5
Input: n = 7, edges = [[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]], t = 1, target = 7
Output: 0.3333333333333333
Explanation: The figure above shows the given graph. The frog starts at vertex 1, jumping with
1/3 = 0.3333333333333333 probability to the vertex 7 after second 1.


Example 3:
Input: n = 7, edges = [[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]], t = 20, target = 6
Output: 0.16666666666666666


Constraints:
1 <= n <= 100
edges.length == n-1
edges[i].length == 2
1 <= edges[i][0], edges[i][1] <= n
1 <= t <= 50
1 <= target <= n
Answers within 10^-5 of the actual value will be accepted as correct.*/

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class FrogPositionAfterTSeconds {

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

  public static double frogPositionSlow(int n, int[][] edges, int t, int target) {
    int root = 1;
    if (target == root && t == 0) {
      return 1;
    }

    if (t == 0 && target != root) {
      return 0;
    }
    if (edges.length == 0) {
      return target == root ? 1 : 0;
    }

    HashMap<Integer, HashSet<Integer>> adjacency = new HashMap<>();
    HashSet<Integer> processed = new HashSet<>();
    for (int[] edge : edges) {
      if (!adjacency.containsKey(edge[0])) {
        adjacency.put(edge[0], new HashSet<>());
      }
      if (!adjacency.containsKey(edge[1])) {
        adjacency.put(edge[1], new HashSet<>());
      }
      adjacency.get(edge[0]).add(edge[1]);
      adjacency.get(edge[1]).add(edge[0]);
    }

    int[] parentOf = new int[n + 1];
    Queue<Integer> q = new LinkedList<>();
    q.add(1);

    while (!q.isEmpty()) {
      int currNode = q.remove();
      processed.add(currNode);
      HashSet<Integer> connections = adjacency.get(currNode);
      if (connections != null) {
        for (int nextNode : connections) {
          if (!processed.contains(nextNode)) {
            parentOf[nextNode] = currNode;
            q.add(nextNode);
          }
        }
      }
    }

    if (!processed.contains(target)) {
      return 0;
    }

    if (target == root) {
      if (t < 1) {
        return 1;
      }
      if (!adjacency.containsKey(root)) {
        return 0;
      }
      return adjacency.get(root).isEmpty() ? 1 : 0;
    }
    boolean isTerminal = adjacency.get(target).size() == 1;

    double probability = 1;
    int i = 0, node = target;
    for (; i < t && node != root; i++) {
      int p = parentOf[node];
      int branches = p == root ? adjacency.get(p).size() : adjacency.get(p).size() - 1;
      probability *= (1d / branches);
      node = p;
    }
    if (node == root && i != t && !isTerminal) {
      return 0;
    }
    return node == root ? probability : 0d;
  }

  public static double frogPosition(int n, int[][] edges, int time, int target) {
    LinkedList<Integer>[] adjacency = new LinkedList[n + 1];
    for (int[] edge : edges) {
      if (adjacency[edge[0]] == null) {
        adjacency[edge[0]] = new LinkedList<>();
      }
      if (adjacency[edge[1]] == null) {
        adjacency[edge[1]] = new LinkedList<>();
      }
      adjacency[edge[0]].add(edge[1]);
      adjacency[edge[1]].add(edge[0]);
    }

    return dfs(adjacency, 1, 1, time, target, 1);
  }

  public static double dfs(List<Integer>[] adjacency, int prev, int curr,
      int time, int target, double probability) {
    if (curr == target) {
      if (time == 0) {
        return probability;
      } else {
        if (adjacency[curr] == null) {
          return probability;
        }
        if (curr != prev && adjacency[curr].size() == 1) {
          return probability;
        }
        if (curr == prev && adjacency[curr].size() == 0) {
          return probability;
        }
        return 0;
      }
    }

    if (time == 0) {
      return 0;
    }

    if (adjacency[curr] == null) {
      return 0;
    }

    if (curr == prev) {
      probability *= (1d / (adjacency[curr].size()));
    } else {
      probability *= (1d / (adjacency[curr].size() - 1));
    }

    for (int next : adjacency[curr]) {
      if (next != prev) {
        double res = dfs(adjacency, curr, next, time - 1, target, probability);
        if (res != 0) {
          return res;
        }
      }
    }
    return 0;
  }

  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      assertEquals(0.16666666666666666,
          frogPosition(7,
              shuffle(new int[][]{
                  {1, 2},
                  {1, 3},
                  {1, 7},
                  {2, 4},
                  {2, 6},
                  {3, 5}
              }), 2, 4));

      assertEquals(0.3333333333333333,
          frogPosition(7,
              shuffle(new int[][]{
                  {1, 2},
                  {1, 3},
                  {1, 7},
                  {2, 4},
                  {2, 6},
                  {3, 5}
              }), 1, 7));

      assertEquals(0.16666666666666666,
          frogPosition(7,
              shuffle(new int[][]{
                  {1, 2},
                  {1, 3},
                  {1, 7},
                  {2, 4},
                  {2, 6},
                  {3, 5}
              }), 20, 6));

      assertEquals((0.5 * (1.0 / 3.0)),
          frogPosition(12,
              shuffle(new int[][]{
                  {1, 2},
                  {1, 3},
                  {3, 4},
                  {5, 3},
                  {6, 3},
                  {7, 5},
                  {5, 8},
                  {7, 9},
                  {10, 7},
                  {11, 7},
                  {12, 7}
              }), 3, 6));

      assertEquals((0.5 * (1.0 / 3.0)),
          frogPosition(12,
              shuffle(new int[][]{
                  {1, 2},
                  {1, 3},
                  {3, 4},
                  {5, 3},
                  {6, 3},
                  {7, 5},
                  {5, 8},
                  {7, 9},
                  {10, 7},
                  {11, 7},
                  {12, 7}
              }), 5, 6));

      assertEquals((0.5 * (1.0 / 3.0)),
          frogPosition(12,
              shuffle(new int[][]{
                  {1, 2},
                  {1, 3},
                  {3, 4},
                  {5, 3},
                  {6, 3},
                  {7, 5},
                  {5, 8},
                  {7, 9},
                  {10, 7},
                  {11, 7},
                  {12, 7}
              }), 100, 6));

      assertEquals(1, frogPosition(3,
          new int[][]{
              {1, 3},
              {2, 3}
          }, 1, 3));

      assertEquals(0, frogPosition(3,
          shuffle(new int[][]{
              {1, 3},
              {2, 3}
          }), 0, 3));

      assertEquals(0, frogPosition(3,
          shuffle(new int[][]{
              {1, 3},
              {2, 3}
          }), 10, 30));

      assertEquals(0, frogPosition(3,
          shuffle(new int[][]{
              {1, 3},
              {2, 3}
          }), 10, 1));

      assertEquals(1, frogPosition(3,
          new int[][]{
              {1, 3},
              {2, 3}
          }, 10, 2));

      assertEquals(0, frogPosition(3,
          shuffle(new int[][]{
              {1, 3},
              {2, 3}
          }), 10, 3));

      assertEquals(0, frogPosition(10,
          new int[][]{
          }, 10, 2));

      assertEquals(1, frogPosition(10,
          new int[][]{
          }, 10, 1));

      assertEquals(0, frogPosition(10,
          new int[][]{
              {5, 6}}, 10, 9));

      assertEquals(0, frogPosition(10,
          new int[][]{
              {1, 2}}, 10, 9));

      assertEquals(0,
          frogPosition(16,
              shuffle(new int[][]{
                  {2, 1},
                  {3, 1},
                  {4, 1},
                  {5, 4},
                  {6, 3},
                  {7, 4},
                  {8, 7},
                  {9, 5},
                  {10, 4},
                  {11, 7},
                  {12, 3},
                  {13, 11},
                  {14, 3},
                  {15, 13},
                  {16, 15}}),
              1,
              1
          ));

      assertEquals(0,
          frogPosition(9,
              shuffle(new int[][]{
                  {2, 1},
                  {3, 1},
                  {4, 2},
                  {5, 3},
                  {6, 5},
                  {7, 4},
                  {8, 7},
                  {9, 7}}),
              1,
              8));
    }
  }
}
