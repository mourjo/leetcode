package me.mourjo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

/*
Given an array of integers nums and a positive integer k, find whether it's possible to
divide this array into k non-empty subsets whose sums are all equal.

Example 1:
Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
Output: True
Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.

Note:
1 <= k <= len(nums) <= 16.
0 < nums[i] < 10000.
 */
public class PartitionToKEqualSumSubsets {

  public static boolean canPartitionKSubsetsSlow(int[] nums, int k) {
    if (k < 1) {
      return false;
    }
    if (k == 1) {
      return true;
    }
    if (k > nums.length) {
      return false;
    }

    int sum = Arrays.stream(nums).sum(), groupMax = sum / k;
    if (sum % k != 0) {
      return false;
    }

    Arrays.sort(nums);
    if (nums[nums.length - 1] > groupMax) {
      return false;
    }
    int i = nums.length - 1;
    while (i >= 0 && nums[i] == groupMax) {
      i--;
      k--;
    }
    return dfs(nums, i, groupMax, new int[k]);
  }

  public static boolean dfs(int[] nums, int i, int max, int[] groupSums) {
    if (i < 0) {
      return true;
    }

    int item = nums[i];
    for (int g = 0; g < groupSums.length; g++) {
      if (groupSums[g] > max) {
        return false;
      }
      if (groupSums[g] + item > max) {
        continue;
      }

      groupSums[g] += item;
      if (dfs(nums, i - 1, max, groupSums)) {
        return true;
      }
      groupSums[g] -= item;

      // touch only one virgin group at a level
      // if we encounter the first group that has a 0-sum, then all groups after this have 0-sum
      // prevent repetitive work by not processing more than one 0-sum group
      if (groupSums[g] == 0) {
        break;
      }
    }
    return false;
  }

  public static boolean canPartitionKSubsets(int[] nums, int k) {
    if (k < 1) {
      return false;
    }
    if (k == 1) {
      return true;
    }
    if (k > nums.length) {
      return false;
    }

    int sum = Arrays.stream(nums).sum();
    if (sum % k != 0) {
      return false;
    }
    int perGroupSum = sum / k;
    Arrays.sort(nums);
    if (nums[nums.length - 1] > perGroupSum) {
      return false;
    }
    boolean[] visited = new boolean[nums.length];

    return dfs(nums, 0, perGroupSum, nums.length - 1, visited, k);
  }

  public static boolean dfs(int[] nums, int currSum, int target, int maxIdx, boolean[] visited,
      int numGroups) {
    if (numGroups <= 1) {
      return true;
    }

    if (target == currSum) {
      // group formed
      return dfs(nums, 0, target, nums.length - 1, visited, numGroups - 1);
    }

    // nums is sorted, be greedy
    for (int i = maxIdx; i >= 0; i--) {
      if (!visited[i] && currSum + nums[i] <= target) {
        visited[i] = true;
        if (dfs(nums, currSum + nums[i], target, i - 1, visited, numGroups)) {
          return true;
        }
        visited[i] = false;
      }
    }

    return false;
  }

  public static void main(String[] args) {
    assertTrue(canPartitionKSubsets(new int[]{4, 3, 2, 3, 5, 2, 1}, 4));
    assertTrue(canPartitionKSubsets(new int[]{4, 3, 2, 3, 5, 2, 1}, 2));
    assertTrue(canPartitionKSubsets(new int[]{4, 3, 2, 3, 5, 2, 1}, 1));
    assertFalse(canPartitionKSubsets(new int[]{4, 3, 2, 3, 5, 2, 1}, 3));
    assertTrue(canPartitionKSubsets(new int[]{1, 1, 1, 1}, 2));
    assertFalse(canPartitionKSubsets(new int[]{1, 1, 1, 1}, 3));
    assertFalse(canPartitionKSubsets(new int[]{1, 2, 1, 1}, 3));
    assertTrue(canPartitionKSubsets(new int[]{2, 3, 5}, 2));
    assertFalse(canPartitionKSubsets(new int[]{1, 1, 1}, 2));
    assertTrue(canPartitionKSubsets(new int[]{5, 2, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 3}, 15));
    assertTrue(canPartitionKSubsets(
        new int[]{960, 3787, 1951, 5450, 4813, 752, 1397, 801, 1990, 1095, 3643, 8133, 893, 5306,
            8341, 5246}, 6));
  }
}
