package me.mourjo;

import java.util.Arrays;

public class ThreeSumClosest {
    /** https://leetcode.com/problems/3sum-closest/
     * Given an array nums of n integers and an integer target, find three
     * integers in nums such that the sum is closest to target. Return the
     * sum of the three integers. You may assume that each input would
     * have exactly one solution.
     *
     * Example:
     *
     * Given array nums = [-1, 2, 1, -4], and target = 1.
     *
     * The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
     */

    public static int threeSumClosest(int[] a, int target) {
        int minDist = Integer.MAX_VALUE, result = -1;

        Arrays.sort(a);

        for (int pivot = 0; pivot < a.length-2; pivot++) {
            int low = pivot + 1, high = a.length - 1;
            while (low < high) {
                int s = a[pivot] + a[low] + a[high];
                int dist = Math.abs(s - target);

                if (dist < minDist) {
                    minDist = dist;
                    result = s;
                }

                if (s < target)
                    low++;
                else if (s > target)
                    high--;
                else
                    return s;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Utilities.check(threeSumClosest(new int[]{-1,2,1,-4}, 1), 2);
        Utilities.check(threeSumClosest(new int[]{1,2,3,4,-100}, 4), 6);
        Utilities.check(threeSumClosest(new int[]{-1,2,0,-10,-4,10}, 0), 0);
    }
}
