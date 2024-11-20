package me.mourjo.y22;
/*
https://leetcode.com/problems/super-palindromes/

Let's say a positive integer is a superpalindrome if it is a palindrome, and it is also
the square of a palindrome.

Now, given two positive integers L and R (represented as strings), return the number of
superpalindromes in the inclusive range [L, R].

Example 1:
Input: L = "4", R = "1000"
Output: 4
Explanation: 4, 9, 121, and 484 are superpalindromes.
Note that 676 is not a superpalindrome: 26 * 26 = 676, but 26 is not a palindrome.

Note:
1 <= len(L) <= 18
1 <= len(R) <= 18
L and R are strings representing integers in the range [1, 10^18).
int(L) <= int(R)
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuperPalindrome {

  public static final int magic = 100_000;

  public static int superpalindromesInRange(String L, String R) {
    // max = 10^18
    // max check = sqrt(10^18) = 10^9
    // palin check max = half of max check = 10 ^ 5
    long lower = Long.parseLong(L), higher = Long.parseLong(R);
    int count = 0;

    for (int i = 1; i < magic; i++) {
      long oddPalin = mirror(i, true);
      oddPalin *= oddPalin;
      if (lower <= oddPalin && oddPalin <= higher && isPalin(oddPalin)) {
        count++;
      }

      if (higher < oddPalin) {
        break;
      }

      long evenPalin = mirror(i, false);
      evenPalin *= evenPalin;
      if (lower <= evenPalin && evenPalin <= higher && isPalin(evenPalin)) {
        count++;
      }
    }
    return count;
  }

  public static long mirror(int i, boolean isOddLen) {
    long s = i;
    for (int j = isOddLen ? i / 10 : i; j > 0; j /= 10) {
      int digit = j % 10;
      s = s * 10 + digit;
    }
    return s;
  }

  public static boolean isPalin(long v) {
    long rev = 0, orig = v;
    while (v > 0) {
      rev = rev * 10 + (v % 10);
      v /= 10;
    }
    return rev == orig;
  }

  public static void main(String[] args) {

    assertEquals(4, superpalindromesInRange("4", "1000"));
  }
}
