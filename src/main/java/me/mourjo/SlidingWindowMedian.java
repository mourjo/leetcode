package me.mourjo;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Comparator;
import java.util.TreeSet;

  /*
https://leetcode.com/problems/sliding-window-median/
Median is the middle value in an ordered integer list. If the size of the list is even, there is
no middle value. So the median is the mean of the two middle value.

Examples:
[2,3,4] , the median is 3

[2,3], the median is (2 + 3) / 2 = 2.5

Given an array nums, there is a sliding window of size k which is moving from the very left of the
array to the very right. You can only see the k numbers in the window. Each time the sliding window
moves right by one position. Your job is to output the median array for each window in the original
array.

For example,
Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.

Window position                Median
---------------               -----
[1  3  -1] -3  5  3  6  7       1
 1 [3  -1  -3] 5  3  6  7       -1
 1  3 [-1  -3  5] 3  6  7       -1
 1  3  -1 [-3  5  3] 6  7       3
 1  3  -1  -3 [5  3  6] 7       5
 1  3  -1  -3  5 [3  6  7]      6
Therefore, return the median sliding window as [1,-1,-1,3,5,6].

Note:
You may assume k is always valid, ie: k is always smaller than input array's size for
non-empty array. Answers within 10^-5 of the actual value will be accepted as correct.
   */


public class SlidingWindowMedian {

  public static boolean isUnblanced(TreeSet left, TreeSet right, int k) {
    if (k % 2 == 0) {
      return left.size() != right.size();
    }
    return Math.abs(left.size() - right.size()) > 1;
  }

  public static void balance(TreeSet<Integer> left, TreeSet<Integer> right, int k) {
    while (isUnblanced(left, right, k)) {
      right.add(left.pollFirst());
    }
  }


  public static double calculateMedian(int[] a, TreeSet<Integer> left, TreeSet<Integer> right,
      int k) {
    if (k % 2 == 0) {
      return (0.0 + a[left.first()] + a[right.first()]) / 2.0;
    }
    return a[left.first()];
  }

  public static double[] medianSlidingWindow(int[] a, int k) {

    // left sorted in descending
    TreeSet<Integer> left = new TreeSet<>(new Comparator<Integer>() {
      @Override
      public int compare(Integer idx1, Integer idx2) {
        if (a[idx1] == a[idx2]) {
          return Integer.compare(idx2, idx1);
        }
        return Integer.compare(a[idx2], a[idx1]);
      }
    });

    // right sorted in ascending order
    TreeSet<Integer> right = new TreeSet<>(new Comparator<Integer>() {
      @Override
      public int compare(Integer idx1, Integer idx2) {
        if (a[idx1] == a[idx2]) {
          return Integer.compare(idx1, idx2);
        }
        return Integer.compare(a[idx1], a[idx2]);
      }
    });

    double[] medians = new double[a.length - k + 1];
    int count = 0;

    for (int i = 0; i < k; i++) {
      left.add(i);
    }

    balance(left, right, k);
    medians[count++] = calculateMedian(a, left, right, k);

    for (int i = 1; i < a.length - k + 1; i++) {
      int idxRemoved = i - 1;
      int idxAdded = i + k - 1;
      if (!left.remove(idxRemoved)) {
        right.remove(idxRemoved);
      }

      right.add(idxAdded);
      left.add(right.pollFirst());

      balance(left, right, k);
      medians[count++] = calculateMedian(a, left, right, k);
    }
    return medians;
  }

  public static void main(String[] args) {
    assertArrayEquals(new double[]{2, 3, 4, 5},
        medianSlidingWindow(new int[]{1, 2, 3, 4, 5, 6}, 3));

    assertArrayEquals(new double[]{2, 3, 4},
        medianSlidingWindow(new int[]{1, 2, 3, 4, 5}, 3));
    // 2,3,4

    // 2,3,4,5
    assertArrayEquals(new double[]{7, 5, 7},
        medianSlidingWindow(new int[]{9, 5, 7, 2, 10}, 3));
    // 7, 5, 7

    assertArrayEquals(new double[]{2147483647},
        medianSlidingWindow(new int[]{2147483647, 2147483647}, 2));

    assertArrayEquals(new double[]{1, -1, -1, 3, 5, 6},
        medianSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3));


  }

}
