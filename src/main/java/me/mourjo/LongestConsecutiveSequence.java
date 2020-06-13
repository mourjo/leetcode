package me.mourjo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Random;

/*
Given an unsorted array of integers, find the length of the longest consecutive elements sequence.

Your algorithm should run in O(n) complexity.

Example:

Input: [100, 4, 200, 1, 3, 2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
 */
public class LongestConsecutiveSequence {

  public static int[] shuffle(int[] a) {
    Random r = new Random();
    for (int i = 0; i < a.length; i++) {
      int j = r.nextInt(a.length);
      if (i != j) {
        a[i] = a[i] + a[j];
        a[j] = a[i] - a[j];
        a[i] = a[i] - a[j];
      }
    }
    return a;
  }

  public static int longestConsecutive(int[] nums) {
    if (nums.length == 0) {
      return 0;
    }
    HashSet<Integer> set = new HashSet<>(nums.length);
    for (int n : nums) {
      set.add(n);
    }

    int seq = 1;
    for (int n : nums) {
      int currSeq = 1;

      // consider those that have only one end (ie not middle)
      if (!set.contains(n - 1) && set.contains(n + 1)) {
        int m = n + 1;
        while (set.contains(m++)) {
          currSeq++;
        }
        seq = Math.max(seq, currSeq);
      }

      if (!set.contains(n + 1) && set.contains(n - 1)) {
        int m = n - 1;
        while (set.contains(m--)) {
          currSeq++;
        }
        seq = Math.max(seq, currSeq);
      }
    }
    return seq;
  }

  public static void main(String[] args) {
    assertEquals(5,
        longestConsecutive(
            shuffle(
                new int[]{1, 5, 2, 4, 3})));

    for (int i = 0; i < 10; i++) {
      assertEquals(4,
          longestConsecutive(
              shuffle(
                  new int[]{100, 4, 200, 1, 3, 2})));

      assertEquals(1,
          longestConsecutive(
              shuffle(
                  new int[]{100, 4, 200, 1, 300, 200})));

      assertEquals(5,
          longestConsecutive(
              shuffle(
                  new int[]{1, 2, 3, 4, 5})));

      assertEquals(3,
          longestConsecutive(
              shuffle(
                  new int[]{1, 2, 3, 40, 5})));

      assertEquals(0,
          longestConsecutive(
              shuffle(
                  new int[]{})));

      assertEquals(1,
          longestConsecutive(
              shuffle(
                  new int[]{1})));

      assertEquals(5,
          longestConsecutive(
              shuffle(
                  new int[]{1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 5, 10})));
    }
  }
}
