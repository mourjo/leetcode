package me.mourjo.y22;

/*
https://leetcode.com/problems/optimize-water-distribution-in-a-village/

There are n houses in a village. We want to supply water for all the houses by building
wells and laying pipes.

For each house i, we can either build a well inside it directly with cost wells[i], or
pipe in water from another well to it. The costs to lay pipes between houses are given
by the array pipes, where each pipes[i] = [house1, house2, cost] represents the cost to
connect house1 and house2 together using a pipe. Connections are bidirectional.

Find the minimum total cost to supply water to all houses.

Example 1:
(2) ---------1--------- (1)
  \
   \
    ---------1--------- (3)

Input: n = 3, wells = [1,2,2], pipes = [[1,2,1],[2,3,1]]
Output: 3
Explanation:
The image shows the costs of connecting houses using pipes.
The best strategy is to build a well in the first house with cost 1 and connect the other
houses to it with cost 2 so the total cost is 3.

Constraints:

1 <= n <= 10000
wells.length == n
0 <= wells[i] <= 10^5
1 <= pipes.length <= 10000
1 <= pipes[i][0], pipes[i][1] <= n
0 <= pipes[i][2] <= 10^5
pipes[i][0] != pipes[i][1]
 */

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OptimizeWaterDistributionVillage {

    public static final int WELL_NODE = 0;

    public static int findAncestor(int[] parent, int x) {
        if (parent[x] == x) {
            return x;
        }
        // short circuit
        parent[x] = findAncestor(parent, parent[x]);
        return parent[x];
    }

    public static void addParent(int[] parent, int x, int y) {
        if (x < y) {
            parent[y] = x;
        } else {
            parent[x] = y;
        }
    }

    public static int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        int[][] edges = new int[pipes.length + wells.length][3];
        int[] parents = new int[wells.length + 1];

        System.arraycopy(pipes, 0, edges, 0, pipes.length);

        parents[WELL_NODE] = WELL_NODE;

        for (int i = 0; i < n; i++) {
            edges[i + pipes.length] = new int[]{WELL_NODE, i + 1, wells[i]};
            parents[i + 1] = i + 1;
        }

        Arrays.sort(edges, Comparator.comparingInt(o -> o[2]));

        int cost = 0;
        for (int[] edge : edges) {
            int p1 = findAncestor(parents, edge[0]);
            int p2 = findAncestor(parents, edge[1]);

            if (p1 != p2) {
                addParent(parents, p1, p2);
                cost += edge[2];
            }
        }
        return cost;
    }

    public static void main(String[] args) {
        assertEquals(3,
                minCostToSupplyWater(3, new int[]{1, 2, 2}, new int[][]{
                        {1, 2, 1},
                        {2, 3, 1}
                }));

        assertEquals(1002,
                minCostToSupplyWater(3, new int[]{1000, 2000, 2000}, new int[][]{
                        {1, 2, 1},
                        {2, 3, 1}
                }));

        assertEquals(1002,
                minCostToSupplyWater(3, new int[]{1000, 2000, 2000}, new int[][]{
                        // there has to be one well
                        {1, 2, 1},
                        {2, 3, 1},
                        {1, 3, 1}
                }));

        assertEquals(3,
                minCostToSupplyWater(3, new int[]{1, 1, 1}, new int[][]{
                        {1, 2, 1},
                        {2, 3, 1},
                        {1, 3, 1}
                }));

        assertEquals(204321,
                minCostToSupplyWater(6, new int[]{4625, 65696, 86292, 68291, 37147, 7880},
                        new int[][]{
                                {2, 1, 79394},
                                {3, 1, 45649},
                                {4, 1, 75810},
                                {5, 3, 22340},
                                {6, 1, 6222}
                        }));

        assertEquals(131704,
                minCostToSupplyWater(5, new int[]{46012, 72474, 64965, 751, 33304},
                        new int[][]{{2, 1, 6719}, {3, 2, 75312}, {5, 3, 44918}}));
    }
}
