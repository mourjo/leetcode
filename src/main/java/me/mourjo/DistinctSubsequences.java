package me.mourjo;

/*
https://leetcode.com/problems/distinct-subsequences/
Given a string S and a string T, count the number of distinct subsequences of S which equals T.

A subsequence of a string is a new string which is formed from the original string by deleting some
(can be none) of the characters without disturbing the relative positions of the remaining characters.
(ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).

It's guaranteed the answer fits on a 32-bit signed integer.

Example 1:

Input: S = "rabbbit", T = "rabbit"
Output: 3
Explanation:
As shown below, there are 3 ways you can generate "rabbit" from S.
(The caret symbol ^ means the chosen letters)

rabbbit
^^^^ ^^
rabbbit
^^ ^^^^
rabbbit
^^^ ^^^
Example 2:

Input: S = "babgbag", T = "bag"
Output: 5
Explanation:
As shown below, there are 5 ways you can generate "bag" from S.
(The caret symbol ^ means the chosen letters)

babgbag
^^ ^
babgbag
^^    ^
babgbag
^    ^^
babgbag
  ^  ^^
babgbag
    ^^^
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DistinctSubsequences {

  public static int numDistinct(String haystack, String n) {
    HashMap<Character, List<Integer>> nLookup = new HashMap<>(n.length());

    for (int i = 0; i < n.length(); i++) {
      char c = n.charAt(i);
      List<Integer> x = nLookup.getOrDefault(c, new ArrayList<>(n.length()));
      x.add(n.length() - i - 1); // indexes in reversed order
      nLookup.put(c, x);
    }

    int[] table = new int[n.length()];
    for (int i = haystack.length() - 1; i >= 0; i--) {
      char c = haystack.charAt(i);

      if (nLookup.containsKey(c)) {
        for (int loc : nLookup.get(c)) {
          if (loc > 0) {
            table[loc] += table[loc - 1];
          } else {
            table[loc] += 1;
          }
        }
      }
    }
    return table[table.length - 1];
  }

  public static void main(String[] args) {
    assertEquals(7, numDistinct("bbabbag", "bbag"));
    assertEquals(1, numDistinct("abc", "abc"));
    assertEquals(0, numDistinct("abcd", "abcde"));
    assertEquals(4, numDistinct("abcabc", "abc"));
    // aa
    assertEquals(10, numDistinct("aaaaa", "aa"));

    assertEquals(3, numDistinct("rabbbit", "rabbit"));
    assertEquals(5, numDistinct("babgbag", "bag"));
  }


}
