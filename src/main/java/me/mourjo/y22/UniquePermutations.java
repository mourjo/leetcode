package me.mourjo.y22;

import java.util.*;

public class UniquePermutations {
    /**
     * https://leetcode.com/problems/permutations-ii/
     * Given a collection of numbers that might contain duplicates, return all possible unique permutations.
     *
     * Example:
     *
     * Input: [1,1,2]
     * Output:
     * [
     *   [1,1,2],
     *   [1,2,1],
     *   [2,1,1]
     * ]
     */

    static final int marker = Integer.MIN_VALUE + 100;

    public static void dfs (int nums[], List<List<Integer>> result, Stack<Integer> current) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
        }
        else {
            for (int i=0; i<nums.length; i++) {
                if (i+1 < nums.length && nums[i] == nums[i+1])
                    continue;
                if (nums[i] != marker) {
                    int t = nums[i];
                    current.push(t);
                    nums[i] = marker;
                    dfs(nums, result, current);
                    current.pop();
                    nums[i] = t;
                }
            }
        }
    }

    public static List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new LinkedList<>();
        dfs(nums, result, new Stack<>());
        return result;
    }

    public static void main(String[] args) {

        System.out.println("Expected:\n[[1, 1, 2], [1, 2, 1], [2, 1, 1]]");
        System.out.println("Actual:\n" + permuteUnique(new int[]{1,1,2}));
        System.out.println();


        System.out.println(permuteUnique(new int[]{1,1,1}));
        System.out.println(permuteUnique(new int[]{1}));
        System.out.println(permuteUnique(new int[]{}));
    }
}
