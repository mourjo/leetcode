package me.mourjo.y22;

import java.util.Comparator;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
https://leetcode.com/problems/ipo/

Suppose LeetCode will start its IPO soon. In order to sell a good price of its shares to Venture Capital, LeetCode would like to work on some projects to increase its capital before the IPO. Since it has limited resources, it can only finish at most k distinct projects before the IPO. Help LeetCode design the best way to maximize its total capital after finishing at most k distinct projects.

You are given several projects. For each project i, it has a pure profit Pi and a minimum capital of Ci is needed to start the corresponding project. Initially, you have W capital. When you finish a project, you will obtain its pure profit and the profit will be added to your total capital.

To sum up, pick a list of at most k distinct projects from given projects to maximize your final capital, and output your final maximized capital.

Example 1:
Input: k=2, W=0, Profits=[1,2,3], Capital=[0,1,1].

Output: 4

Explanation: Since your initial capital is 0, you can only start the project indexed 0.
             After finishing it you will obtain profit 1 and your capital becomes 1.
             With capital 1, you can either start the project indexed 1 or the project indexed 2.
             Since you can choose at most 2 projects, you need to finish the project indexed 2 to get the maximum capital.
             Therefore, output the final maximized capital, which is 0 + 1 + 3 = 4.
Note:
You may assume all numbers in the input are non-negative integers.
The length of Profits array and Capital array will not exceed 50,000.
The answer is guaranteed to fit in a 32-bit signed integer.
 */
public class MaximiseProfitMinimizeCostLimitedTrials {

    public static int findMaximizedCapital(int k, int W, int[] p, int[] c) {
        PriorityQueue<Integer> profits = new PriorityQueue<>(new Comparator<Integer>() {
            public int compare(Integer x, Integer y) {
                return Integer.compare(p[x], p[y]);
            }
        }.reversed());

        PriorityQueue<Integer> costs = new PriorityQueue<>(new Comparator<Integer>() {
            public int compare(Integer x, Integer y) {
                return Integer.compare(c[x], c[y]);
            }
        });

        for (int i = 0; i < c.length; i++) {
            costs.add(i);
        }

        for (int i = 0; i < k; i++) {
            while (!costs.isEmpty() && c[costs.peek()] <= W) {
                profits.add(costs.remove());
            }
            if (profits.isEmpty()) {
                break;
            }
            W += p[profits.remove()];
        }

        return W;
    }

    public static void main(String[] args) {
        assertEquals(4, findMaximizedCapital(2, 0, new int[]{1, 2, 3}, new int[]{0, 1, 1}));
    }
}
