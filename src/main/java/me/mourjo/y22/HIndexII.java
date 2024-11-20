package me.mourjo.y22;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
https://leetcode.com/problems/h-index-ii/
Given an array of citations sorted in ascending order (each citation is a non-negative integer)
of a researcher, write a function to compute the researcher's h-index.

According to the definition of h-index on Wikipedia: "A scientist has index h if h of his/her N
papers have at least h citations each, and the other N − h papers have no more than h citations
each."

Example:

Input: citations = [0,1,3,5,6]
Output: 3
Explanation: [0,1,3,5,6] means the researcher has 5 papers in total and each of them had
             received 0, 1, 3, 5, 6 citations respectively.
             Since the researcher has 3 papers with at least 3 citations each and the remaining
             two with no more than 3 citations each, her h-index is 3.
Note:

If there are several possible values for h, the maximum one is taken as the h-index.

Follow up:

This is a follow up problem to H-Index, where citations is now guaranteed to be sorted in ascending order.
Could you solve it in logarithmic time complexity?
 */
public class HIndexII {

  public static int hIndex(int[] a) {
    if (a.length == 0) {
      return 0;
    }
    int low = 0, high = a.length - 1;
    while (low <= high) {
      int mid = (low + high) / 2;
      if (a[mid] == a.length - mid) {
        return a.length - mid;
      }
      if (a[mid] < a.length - mid) {
        low = mid + 1;
      } else {
        high = mid - 1;
      }
    }
    return a.length - low;
  }

  public static void main(String[] args) {
    assertEquals(3, hIndex(new int[]{0, 1, 3, 5, 6}));
    assertEquals(3, hIndex(new int[]{1, 2, 3, 4, 5}));
    assertEquals(5, hIndex(new int[]{10, 20, 30, 40, 50}));
    assertEquals(5, hIndex(new int[]{100, 200, 200, 200, 200}));
    assertEquals(2, hIndex(new int[]{0, 1, 2, 3, 4}));
    assertEquals(2, hIndex(new int[]{0, 0, 0, 5, 6}));
    assertEquals(5, hIndex(new int[]{6, 6, 6, 6, 6}));
    assertEquals(1, hIndex(new int[]{1, 1, 1, 1, 1}));
    assertEquals(2, hIndex(new int[]{1, 2, 3, 4}));
    assertEquals(4, hIndex(new int[]{1, 2, 3, 4, 4, 4, 4, 4, 4, 40000}));
    assertEquals(1, hIndex(new int[]{0, 0, 0, 0, 6}));
    assertEquals(1, hIndex(new int[]{0, 0, 0, 0, 1}));
    assertEquals(0, hIndex(new int[]{0, 0, 0, 0, 0}));
    assertEquals(1, hIndex(new int[]{1}));
    assertEquals(1, hIndex(new int[]{1, 1}));
    assertEquals(1, hIndex(new int[]{2}));
    assertEquals(1, hIndex(new int[]{200}));
    assertEquals(3, hIndex(new int[]{200, 200, 200}));
    assertEquals(8, hIndex(new int[]{10, 10, 10, 10, 10, 10, 10, 20}));
    assertEquals(3, hIndex(new int[]{1, 4, 7, 9}));
  }

}
