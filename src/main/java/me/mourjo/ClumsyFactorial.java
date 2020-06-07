package me.mourjo;

/*
https://leetcode.com/problems/clumsy-factorial/
Normally, the factorial of a positive integer n is the product of all positive integers
less than or equal to n.
For example, factorial(10) = 10 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1.

We instead make a clumsy factorial: using the integers in decreasing order, we swap out
the multiply operations for a fixed rotation of operations:
    -> multiply (*),
    -> divide (/),
    -> add (+)
    -> subtract (-)
in this order.

For example, clumsy(10) = 10 * 9 / 8 + 7 - 6 * 5 / 4 + 3 - 2 * 1.  However, these operations
are still applied using the usual order of operations of arithmetic: we do all multiplication
and division steps before any addition or subtraction steps, and multiplication and division
steps are processed left to right.

Additionally, the division that we use is floor division such that 10 * 9 / 8 equals 11.
This guarantees the result is an integer.

Implement the clumsy function as defined above: given an integer N, it returns the clumsy
factorial of N.


Example 1:
Input: 4
Output: 7
Explanation: 7 = 4 * 3 / 2 + 1

Example 2:
Input: 10
Output: 12
Explanation: 12 = 10 * 9 / 8 + 7 - 6 * 5 / 4 + 3 - 2 * 1

Note:
1 <= N <= 10000
-2^31 <= answer <= 2^31 - 1  (The answer is guaranteed to fit within a 32-bit integer.)
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.Queue;

public class ClumsyFactorial {

  public static int clumsyclumsy(int N) {
    if (N == 1) {
      return 1;
    }
    if (N == 2) {
      return 2;
    }
    int i = 0;
    char[] op = new char[]{'*', '/', '+', '-'};
    int acc1 = N;
    Queue<Integer> q = new LinkedList<>();
    int sign = 1;
    while (i < N - 1) {
      int k = N - i - 1;
      switch (op[i % 4]) {
        case '*':
          acc1 *= (sign * k);
          sign = 1;
          break;
        case '/':
          acc1 /= k;
          sign = 1;
          break;
        case '+':
          acc1 += k;
          sign = 1;
          break;
        case '-':
          q.add(acc1);
          acc1 = k;
          sign = -1;
          break;
      }

      i++;
    }

    int result = sign * acc1;
    while (!q.isEmpty()) {
      result += q.remove();
    }
    return result;
  }

  public static int clumsy(int x) {
    if (x == 1) {
      return 1;
    }
    if (x == 2) {
      return 2;
    }
    if (x == 3) {
      return 6;
    }
    if (x == 4) {
      return 7;
    }

    if (x % 4 == 0) {
      return x + 1;
    }
    if (x % 4 <= 2) {
      return x + 2;
    }
    return x - 1;
  }

  public static void main(String[] args) {
    assertEquals(1, clumsy(1)); // 1 -> 1
    assertEquals(2, clumsy(2)); // 2 -> 2
    assertEquals(6, clumsy(3)); // 3 -> 6
    assertEquals(7, clumsy(4)); // 4 -> 7
    assertEquals(7, clumsy(5)); // 5 -> 7 (n % 4 <= 2, add 2)
    assertEquals(8, clumsy(6)); // 6 -> 8 (n % 4 <= 2, add 2)
    assertEquals(6, clumsy(7)); // 7 -> 6 (n % 4 == 1, subtract 1)
    assertEquals(9, clumsy(8)); // 8 -> 9 (n % 4 == 0, add 1)
    assertEquals(11, clumsy(9)); // 9 -> 11
    assertEquals(12, clumsy(10)); // 10 -> 12
    assertEquals(10, clumsy(11)); // 11 -> 10
    assertEquals(13, clumsy(12)); // 12 -> 13
    assertEquals(15, clumsy(13)); // 13 -> 15
    assertEquals(21, clumsy(20)); // 20 -> 21
  }
}
