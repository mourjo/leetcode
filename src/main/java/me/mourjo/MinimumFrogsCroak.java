package me.mourjo;

/*
https://leetcode.com/problems/minimum-number-of-frogs-croaking/
Given the string croakOfFrogs, which represents a combination of the string "croak" from different frogs, that is, multiple frogs can croak at the same time, so multiple “croak” are mixed. Return the minimum number of different frogs to finish all the croak in the given string.

A valid "croak" means a frog is printing 5 letters ‘c’, ’r’, ’o’, ’a’, ’k’ sequentially.
The frogs have to print all five letters to finish a croak. If the given string is not a
combination of valid "croak" return -1.



Example 1:

Input: croakOfFrogs = "croakcroak"
Output: 1
Explanation: One frog yelling "croak" twice.
Example 2:

Input: croakOfFrogs = "crcoakroak"
Output: 2
Explanation: The minimum number of frogs is two.
The first frog could yell "crcoakroak".
The second frog could yell later "crcoakroak".
Example 3:

Input: croakOfFrogs = "croakcrook"
Output: -1
Explanation: The given string is an invalid combination of "croak" from different frogs.
Example 4:

Input: croakOfFrogs = "croakcroa"
Output: -1
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinimumFrogsCroak {

  public static int minNumberOfFrogs(String croakOfFrogs) {
    int c = 0, r = 0, o = 0, a = 0, k = 0, n = 0, maxn = 0;
    for (char ch : croakOfFrogs.toCharArray()) {
      switch (ch) {
        case 'c':
          c++;
          n++;
          break;
        case 'r':
          r++;
          break;
        case 'o':
          o++;
          break;
        case 'a':
          a++;
          break;
        case 'k':
          k++;
          n--;
          break;
        default:
          return -1;
      }
      if (r > c || o > r || a > o || k > a) {
        return -1;
      }
      maxn = Math.max(maxn, n);
    }
    if (c == r && c == o && c == a && c == k) {
      return maxn;
    }
    return -1;
  }

  public static void main(String[] args) {
    assertEquals(1, minNumberOfFrogs("croak"));
    assertEquals(1, minNumberOfFrogs("croakcroak"));
    assertEquals(2, minNumberOfFrogs("crcoakroak"));
    assertEquals(-1, minNumberOfFrogs("croakcrook"));
    assertEquals(-1, minNumberOfFrogs("croakcroa"));
    assertEquals(-1, minNumberOfFrogs("croakcrook"));
    assertEquals(-1, minNumberOfFrogs("croak8"));
    assertEquals(3, minNumberOfFrogs("croakcroakcrocrocroakakak"));
  }
}
