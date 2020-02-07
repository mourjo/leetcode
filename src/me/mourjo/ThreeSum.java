package me.mourjo;

import java.util.*;

public class ThreeSum {
    /**
     * https://leetcode.com/problems/3sum/
     * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0?
     * Find all unique triplets in the array which gives the sum of zero.
     *
     * Note:
     *
     * The solution set must not contain duplicate triplets.
     *
     * Example:
     *
     * Given array nums = [-1, 0, 1, 2, -1, -4],
     *
     * A solution set is:
     * [
     *   [-1, 0, 1],
     *   [-1, -1, 2]
     * ]
     */

    public static List<List<Integer>> threeSum(int[] a) {
        List<List<Integer>> result = new LinkedList<>();

        // sorting for removing duplicates
        Arrays.sort(a);

        int n = a.length;

        for (int pivot = 0; pivot < n - 2; pivot++) {
            // skip if the element at last pivot was same as current
            if (pivot > 0 && a[pivot-1] == a[pivot])
                continue;

            int low = pivot + 1;
            int high = n - 1;

            while (low < high) {
                if (a[low] + a[high] + a[pivot] == 0) {
                    result.add(Arrays.asList(a[low], a[high], a[pivot]));

                    // skip all elements of the same value for low and high
                    while (low < high && a[low] == a[low + 1]) low++; // contiguous same value
                    while (low < high && a[high] == a[high - 1]) high--;
                    low++;  // actual (non-equal) next low
                    high--;
                }

                else if (a[low] + a[high] + a[pivot] < 0) {
                    while (low < high && a[low] == a[low + 1]) low++; // contiguous same value
                    low++;
                }
                else {
                    while (low < high && a[high] == a[high - 1]) high--;
                    high--;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(threeSum(new int[] {-1,0,1,2,-1,-4}));
        System.out.println("Expected: [[-1, 0, 1], [-1, -1, 2]]\n");

        System.out.println(threeSum(new int[]{1,23,4,56,7,8,9}));
        System.out.println("Expected: []\n");

        System.out.println(threeSum(new int[]{0,0,0}));
        System.out.println("Expected: [[0, 0, 0]]\n");

        System.out.println(threeSum(new int[]{-4,-2,-2,-2,0,1,2,2,2,3,3,4,4,6,6}));
        System.out.println("Expected: [[-2, 6, -4], [0, 4, -4], [1, 3, -4], [2, 2, -4], [-2, 4, -2], [0, 2, -2]]\n");

        System.out.println(threeSum(new int[]{1,-1,-1,0}));
        System.out.println("Expected: [[0, 1, -1]]\n");
    }
}
