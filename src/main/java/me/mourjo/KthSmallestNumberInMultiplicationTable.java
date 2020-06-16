package me.mourjo;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
https://leetcode.com/problems/kth-smallest-number-in-multiplication-table/solution/
Nearly every one have used the Multiplication Table. But could you find out the k-th smallest
number quickly from the multiplication table?

Given the height m and the length n of a m * n Multiplication Table, and a positive integer k,
you need to return the k-th smallest number in this table.

Example 1:
Input: m = 3, n = 3, k = 5
Output: 3
Explanation:
The Multiplication Table:
1	2	3
2	4	6
3	6	9
The 5-th smallest number is 3 (1, 2, 2, 3, 3).

Example 2:
Input: m = 2, n = 3, k = 6
Output: 6
Explanation:
The Multiplication Table:
1	2	3
2	4	6
The 6-th smallest number is 6 (1, 2, 2, 3, 4, 6).

Note:
The m and n will be in the range [1, 30000].
The k will be in the range [1, m * n]
 */
public class KthSmallestNumberInMultiplicationTable {

  public static int findKthNumber(int m, int n, int k) {
    int low = 1, high = m * n;
    while (low < high) {
      // maintain high as the greatest value that satisfies >= kth rank
      // minimize high as much as possible
      int mid = (low + high) / 2;
      if (enough(mid, m, n, k)) {
        high = mid; // we want to minimize
      } else {
        low = mid + 1;
      }
    }
    return high;
  }

  public static boolean enough(int mid, int rows, int cols, int requiredRank) {
    int rank = 0;
    for (int i = 1; i <= rows; i++) {
      // for each row, calculate the number of elements <= mid
      // sum on each row to get the number of elements in total <= mid
      //
      // in the table of 5s, the number of elements <= 30 is 6 (30/6)
      // in the table of 5s, the number of elements <= 29 is 5 (20/5)
      // in the table of 8s, the number of elements <= 87 is 10 (87/8)
      //
      // if the number of elements in a table exceeds the number of columns,
      // should be ignored: 87102938471/5 is 17420587694 but if the number
      // of columns is 10, the max number of elements in the 5th row <=
      // 87102938471 is 10.
      rank += Math.min(mid / i, cols);
    }
    return requiredRank <= rank;
  }

  public static void main(String[] args) {
    assertEquals(3, findKthNumber(3, 3, 5));
    assertEquals(6, findKthNumber(2, 3, 6));
  }
}
