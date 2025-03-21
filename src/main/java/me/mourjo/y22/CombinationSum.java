package me.mourjo.y22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class CombinationSum {

    /**
     * https://leetcode.com/problems/combination-sum/ Given a set of candidate numbers (candidates)
     * (without duplicates) and a target number (target), find all unique combinations in candidates
     * where the candidate numbers sums to target.
     * <p>
     * The same repeated number may be chosen from candidates unlimited number of times.
     * <p>
     * Note:
     * <p>
     * All numbers (including target) will be positive integers. The solution set must not contain
     * duplicate combinations. Example 1:
     * <p>
     * Input: candidates = [2,3,6,7], target = 7, A solution set is: [ [7], [2,2,3] ] Example 2:
     * <p>
     * Input: candidates = [2,3,5], target = 8, A solution set is: [ [2,2,2,2], [2,3,3], [3,5] ]
     */

    public static void combine(List<List<Integer>> result,
        Stack<Integer> current,
        int[] candidates,
        int remaining,
        int index) {
        if (remaining == 0) {
            result.add(new LinkedList<>(current));
        }
        if (remaining < 0) {
            return;
        } else {
            for (int i = index; i < candidates.length; i++) {
                int x = candidates[i];
                if (x <= remaining) {
                    current.push(x);
                    combine(result, current, candidates, remaining - x, i);
                    current.pop();
                }
            }
        }
    }

    public static List<List<Integer>> combinationSumRecursive(int[] candidates, int target) {
        List<List<Integer>> result = new LinkedList<>();
        Stack<Integer> current = new Stack<>();
        combine(result, current, candidates, target, 0);

        return result;
    }


    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<List<Integer>>> table = new ArrayList<>();
        Arrays.sort(candidates);

        for (int i = 1; i <= target; i++) {
            List<List<Integer>> currentCombinations = new ArrayList<>();

            for (int j = 0; j < candidates.length && candidates[j] <= i; j++) {
                if (candidates[j] == i) {
                    currentCombinations.add(Arrays.asList(candidates[j]));
                } else {
                    // lookup precomputed targets
                    int remaining = i - candidates[j];
                    List<List<Integer>> computedCombinations = table.get(
                        remaining - 1); // zero is the first index
                    for (List<Integer> comb : computedCombinations) {
                        if (comb.get(0) <= candidates[j]) { // remove duplicate combinations
                            List<Integer> newComb = new ArrayList<>();
                            newComb.add(
                                candidates[j]); // add in reverse order (greatest to smallest)
                            newComb.addAll(comb);
                            currentCombinations.add(newComb);
                        }
                    }
                }
            }
            table.add(currentCombinations);
        }
        return table.get(target - 1);
    }

    public static void main(String[] args) {
        System.out.println("Expected: [[7], [2, 2, 3]]");
        System.out.println("Actual: " + combinationSum(new int[]{2, 3, 6, 7}, 7));
        System.out.println();

        System.out.println("Expected: [[2, 2, 2, 2], [2, 3, 3], [3, 5]]");
        System.out.println("Actual: " + combinationSum(new int[]{2, 3, 5}, 8));
        System.out.println();

        System.out.println(
            "Expected:\n[[1, 1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 2], [1, 1, 1, 1, 3], [1, 1, 1, 2, 2], [1, 1, 2, 3], [1, 2, 2, 2], [1, 3, 3], [2, 2, 3]]");
        System.out.println("Actual:\n" + combinationSum(new int[]{1, 2, 3}, 7));
        System.out.println();

        System.out.println("Expected: [[3, 4, 4], [3, 8], [4, 7]]");
        System.out.println("Actual: " + combinationSum(new int[]{8, 7, 4, 3}, 11));
        System.out.println();

    }
}
