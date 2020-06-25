package me.mourjo;
/*
https://leetcode.com/problems/first-missing-positive/

Given an unsorted integer array, find the smallest missing positive integer.

Example 1:

Input: [1,2,0]
Output: 3
Example 2:

Input: [3,4,-1,1]
Output: 2
Example 3:

Input: [7,8,9,11,12]
Output: 1
Note:

Your algorithm should run in O(n) time and use constant extra space.

 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Arrays;

public class FirstMissingSmallest {

  public static int firstMissingPositiveNonConstantSpace(int[] nums) {

    if (nums.length == 0) {
      return 1;
    }
    BigInteger bmap = BigInteger.ZERO;

    int max = 0;

    for (int a : nums) {
      if (max < a) {
        max = a;
      }

      if (a > 0 && a <= nums.length) {
        BigInteger v = BigInteger.ONE.shiftLeft(a - 1);
        bmap = bmap.or(v);
      }
    }

    for (int i = 0; i < nums.length; i++) {
      BigInteger v = BigInteger.ONE.shiftLeft(i);
      if (bmap.and(v).equals(BigInteger.ZERO)) {
        return i + 1;
      }

    }

    return max + 1;
  }

  public static int firstMissingPositive(int[] nums) {
    if (nums.length == 0) {
      return 1;
    }
    if (Arrays.stream(nums).noneMatch(x -> x == 1)) {
      return 1;
    }

    for (int i = 0; i < nums.length; i++) {
      if (nums[i] < 1) {
        nums[i] = 1;
      }
      if (nums[i] > nums.length) {
        nums[i] = 1;
      }
    }

    for (int i = 0; i < nums.length; i++) {
      int val = Math.abs(nums[i]);
      if (nums[val - 1] > 0) {
        nums[val - 1] = -nums[val - 1];
      }
    }

    int i = 0;
    while (i < nums.length) {
      if (nums[i] > 0) {
        return i + 1;
      }
      i++;
    }

    return nums.length + 1;
  }

  public static void main(String[] args) {
    assertEquals(1, firstMissingPositive(new int[]{}));
    assertEquals(2, firstMissingPositive(new int[]{1}));
    assertEquals(1, firstMissingPositive(new int[]{500}));
    assertEquals(6, firstMissingPositive(new int[]{1, 2, 3, 4, 5}));
    assertEquals(2, firstMissingPositive(new int[]{1, -2, -3, -4, 5}));
    assertEquals(2, firstMissingPositive(new int[]{1, -2, -3, -4, 5}));
    assertEquals(1, firstMissingPositive(new int[]{-1, -2, -3, -4, 5}));
    assertEquals(2, firstMissingPositive(new int[]{-1, -2, -3, -4, 5, 1}));
    assertEquals(3, firstMissingPositive(new int[]{-1, -2, 2, -3, -4, 5, 1}));
    assertEquals(6, firstMissingPositive(new int[]{-1, -2, 2, -3, 4, 5, 1, 3}));
    assertEquals(3, firstMissingPositive(new int[]{1, 2, 0}));
    assertEquals(2, firstMissingPositive(new int[]{3, 4, -1, 1}));
    assertEquals(1, firstMissingPositive(new int[]{7, 8, 9, 11, 12}));
    assertEquals(2, firstMissingPositive(new int[]{100, 102, -1, 1}));
    assertEquals(2, firstMissingPositive(new int[]{100000, 1000002, -1, 1}));
    assertEquals(1, firstMissingPositive(
        new int[]{4, 27, -9, 35, 24, 11, 25, 8, -9, 16, 65, 60, 10, 13, 24, -2, -9, -10, 25, 9, 36,
            29, 50, 46, 48, 35, 67, -8, 22, 66, -7, 36, 0, -6, 56, 9, -10, 12, 2, -8, 38, -2, 37,
            34, 21, -7, 17, -2, -7, 53, 17, 15, 67, 52, -8, 47, 18, -9, 24, 50, 42, 27, -8, 27, 31,
            58, 21, 7, 2}));
  }
}
