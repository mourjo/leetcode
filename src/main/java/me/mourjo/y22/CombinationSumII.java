package me.mourjo.y22;

import java.util.*;

public class CombinationSumII {

    /**
     * https://leetcode.com/problems/combination-sum-ii/ Given a collection of candidate numbers
     * (candidates) and a target number (target), find all unique combinations in candidates where
     * the candidate numbers sums to target.
     * <p>
     * Each number in candidates may only be used once in the combination.
     * <p>
     * Note:
     * <p>
     * All numbers (including target) will be positive integers. The solution set must not contain
     * duplicate combinations. Example 1:
     * <p>
     * Input: candidates = [10,1,2,7,6,1,5], target = 8, A solution set is: [ [1, 7], [1, 2, 5], [2,
     * 6], [1, 1, 6] ] Example 2:
     * <p>
     * Input: candidates = [2,5,2,1,2], target = 5, A solution set is: [ [1,2,2], [5] ]
     */

    public static void dfs(int[] candidates,
                           List<List<Integer>> result,
                           Stack<Integer> currentPath,
                           int remaining,
                           int currentIndex) {
        if (remaining == 0) {
            result.add(new ArrayList<>(currentPath));
        } else {
            for (int i = currentIndex; i < candidates.length && candidates[i] <= remaining; i++) {
                // for all candidates greater than the current index

                // if I have seen the same candidate, do not process again
                if (i > currentIndex && candidates[i] == candidates[i - 1]) {
                    continue;
                }

                currentPath.push(candidates[i]);
                dfs(candidates, result, currentPath, remaining - candidates[i], i + 1);
                currentPath.pop();

            }
        }
    }

    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new LinkedList<>();
        dfs(candidates, result, new Stack<Integer>(), target, 0);
        return result;
    }

    public static void main(String[] args) {

        System.out.println("Expected: [[1, 7], [1, 2, 5], [2, 6], [1, 1, 6]]");
        System.out.println("Actual: " + combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8));
        System.out.println();

        System.out.println("Expected: [[1, 2, 2], [5]]");
        System.out.println("Actual: " + combinationSum2(new int[]{2, 5, 2, 1, 2}, 5));
        System.out.println();

        System.out.println("Expected: [[2,3]]");
        System.out.println("Actual: " + combinationSum2(new int[]{3, 1, 2}, 5));
        System.out.println();
    }
}
