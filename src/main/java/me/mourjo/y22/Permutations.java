package me.mourjo.y22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Permutations {
    /** https://leetcode.com/problems/permutations/
     * Given a collection of distinct integers, return all possible permutations.
     *
     * Example:
     *
     * Input: [1,2,3]
     * Output:
     * [
     *   [1,2,3],
     *   [1,3,2],
     *   [2,1,3],
     *   [2,3,1],
     *   [3,1,2],
     *   [3,2,1]
     * ]
     */

    static int marker = Integer.MIN_VALUE + 100; // Limitation : this cannot be part of the array
    public static void dfs (int[] nums, List<List<Integer>> result, Stack<Integer> current, int index) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i=0; i < nums.length; i++) {
            int actualIndex = (index + i) % nums.length;
            if (nums[actualIndex] != marker) {
                current.push(nums[actualIndex]);
                int t = nums[actualIndex];
                nums[actualIndex] = marker;
                dfs(nums, result, current, (actualIndex + 1) % nums.length);
                current.pop();
                nums[actualIndex] = t;
            }
        }
    }

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(nums, result, new Stack<>(), 0);
        return result;
    }

    public static void main(String[] args) {

        System.out.println("Expected:\n[[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]");
        System.out.println("Actual:\n" + permute(new int[]{1,2,3}));
        System.out.println("\n");

        System.out.println(permute(new int[]{1,2}));
        System.out.println(permute(new int[]{1}));
        System.out.println(permute(new int[]{}));
    }
}
