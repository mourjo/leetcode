package me.mourjo;

/*
https://leetcode.com/problems/consecutive-numbers-sum/
Given a positive integer N, how many ways can we write it as a sum of consecutive positive integers?

Example 1:

Input: 5
Output: 2
Explanation: 5 = 5 = 2 + 3
Example 2:

Input: 9
Output: 3
Explanation: 9 = 9 = 4 + 5 = 2 + 3 + 4
Example 3:

Input: 15
Output: 4
Explanation: 15 = 15 = 8 + 7 = 4 + 5 + 6 = 1 + 2 + 3 + 4 + 5
Note: 1 <= N <= 10 ^ 9.
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ConsecutiveNumbersSum {

  public static int consecutiveNumbersSum(int n) {
    int count = 0;
    boolean stopEven = false, stopOdd = false;

    for (int k = 2; k < n && !stopEven && !stopOdd; k++) {
      if (k % 2 == 0) {
        if (((double) n / k) - (n / k) == 0.5) {
          if ((n / k) >= (k / 2)) {
            count++;
          } else {
            stopEven = true;
          }
        }
      } else {
        if ((n % k == 0)) {
          if ((n / k) > (k - 1) / 2) {
            count++;
          } else {
            stopOdd = true;
          }
        }
      }
    }
    return count + 1;
  }

  @Test
  public static void main(String[] args) {
    assertEquals(2, consecutiveNumbersSum(5));
    assertEquals(3, consecutiveNumbersSum(9));
    assertEquals(4, consecutiveNumbersSum(15));

    // 4+5+6+7+8       +         9+10+11+12+13
    assertEquals(4, consecutiveNumbersSum(85));
    assertEquals(2, consecutiveNumbersSum(3));
    assertEquals(4, consecutiveNumbersSum(21));
  }
}
