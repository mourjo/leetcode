package me.mourjo;

/*
https://leetcode.com/problems/interleaving-string/
Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.

Example 1:

Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
Output: true
Example 2:

Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
Output: false
 */

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InterleaveString {

  public static boolean isInterleave(String s1, String s2, String s3) {
    if (s3.length() != s1.length() + s2.length()) {
      return false;
    }
    char[] c1 = s1.toCharArray();
    char[] c2 = s2.toCharArray();
    char[] c3 = s3.toCharArray();

    if (c1.length + c2.length != c3.length) {
      return false;
    }
    if (c3.length == 0) {
      return true;
    }
    boolean[][] invalid = new boolean[c1.length + 1][c2.length + 1];

    return dfs(c1, c2, c3, 0, 0, 0, invalid);
  }

  public static boolean dfs(char[] c1, char[] c2, char[] c3, int p1, int p2, int p3,
      boolean[][] invalidCache) {
    boolean result = false;

    if (invalidCache[p1][p2]) {
      return false;
    } else if (p3 == c3.length) {
      result = true; // max length = c3.length-1
    } else if (p1 < c1.length && p2 < c2.length && c3[p3] == c1[p1] && c3[p3] == c1[p1]) {
      result =
          dfs(c1, c2, c3, p1 + 1, p2, p3 + 1, invalidCache) ||
              dfs(c1, c2, c3, p1, p2 + 1, p3 + 1, invalidCache);
    } else if (p1 < c1.length && c3[p3] == c1[p1]) {
      result = dfs(c1, c2, c3, p1 + 1, p2, p3 + 1, invalidCache);
    } else if (p2 < c2.length && c3[p3] == c2[p2]) {
      result = dfs(c1, c2, c3, p1, p2 + 1, p3 + 1, invalidCache);
    }

    if (!result) {
      invalidCache[p1][p2] = true;
    }
    return result;

  }

  public static void main(String[] args) {
    assertTrue(isInterleave("", "", ""));
    assertTrue(isInterleave("a", "a", "aa"));
    assertFalse(isInterleave("a", "a", "ab"));
    assertTrue(isInterleave("cat", "bat", "cbaatt"));
    assertTrue(isInterleave("aabcc", "dbbca", "aadbbcbcac"));
    assertFalse(isInterleave("aabcc", "dbbca", "aadbbbaccc"));
  }
}
