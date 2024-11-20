package me.mourjo.y22;

/*
https://leetcode.com/problems/scramble-string/
Given a string s1, we may represent it as a binary tree by partitioning it to two non-empty substrings recursively.

Below is one possible representation of s1 = "great":

    great
   /    \
  gr    eat
 / \    /  \
g   r  e   at
           / \
          a   t
To scramble the string, we may choose any non-leaf node and swap its two children.
For example, if we choose the node "gr" and swap its two children, it produces a scrambled
string "rgeat".

    rgeat
   /    \
  rg    eat
 / \    /  \
r   g  e   at
           / \
          a   t
We say that "rgeat" is a scrambled string of "great".
Similarly, if we continue to swap the children of nodes "eat" and "at", it produces a scrambled
string "rgtae".

    rgtae
   /    \
  rg    tae
 / \    /  \
r   g  ta  e
       / \
      t   a
We say that "rgtae" is a scrambled string of "great".

Given two strings s1 and s2 of the same length, determine if s2 is a scrambled string of s1.

Example 1:
Input: s1 = "great", s2 = "rgeat"
Output: true

Example 2:
Input: s1 = "abcde", s2 = "caebd"
Output: false
 */

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScrambledStrings {

  public static boolean isScramble(String s1, String s2) {
    if (s1.equals(s2)) {
      return true;
    }
    if (s1.length() != s2.length()) {
      return false;
    }
    if (s1.length() == 0) {
      return true;
    }
    if (s1.length() == 1) {
      return s1.charAt(0) == s2.charAt(0);
    }

    short[] freq1 = new short[26];
    for (int i = 0; i < s1.length(); i++) {
      freq1[s1.charAt(i) - 'a']++;
      freq1[s2.charAt(i) - 'a']--;
    }

    for (short value : freq1) {
      if (value != 0) {
        return false;
      }
    }

    for (int i = 1; i < s1.length(); i++) {
      String s1BeforeI = s1.substring(0, i);
      String s2BeforeI = s2.substring(0, i);
      String s1AfterI = s1.substring(i);
      String s2AfterI = s2.substring(i);

      if (isScramble(s1BeforeI, s2BeforeI) && isScramble(s1AfterI, s2AfterI)) {
        return true;
      }
      if (isScramble(s1BeforeI, s2.substring(s2.length() - i)) &&
          isScramble(s1AfterI, s2.substring(0, s2.length() - i))) {
        return true;
      }
    }
    return false;
  }


  public static void main(String[] args) {
    assertTrue(isScramble("great", "rgeat"));
    assertFalse(isScramble("abcde", "caebd"));
    assertTrue(isScramble("abb", "bba"));
  }
}
