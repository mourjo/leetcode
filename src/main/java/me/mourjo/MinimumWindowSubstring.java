package me.mourjo;

/*
https://leetcode.com/problems/minimum-window-substring/
Given a string S and a string T, find the minimum window in S which will contain all
the characters in T in complexity O(n).

Example:

Input: S = "ADOBECODEBANC", T = "ABC"
Output: "BANC"
Note:

If there is no such window in S that covers all characters in T, return the empty string "".
If there is such a window, you are guaranteed that there will always be only one unique
minimum window in S.
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

public class MinimumWindowSubstring {

  public static String minWindow(String s, String t) {
    if (s.length() == 0 || t.length() == 0) {
      return "";
    }
    if (s.length() == 1 && s.equals(t)) {
      return t;
    }
    if (s.length() == 1) {
      return "";
    }
    HashMap<Character, Integer> currCounts = new HashMap<>(t.length());
    HashMap<Character, Integer> requireCounts = new HashMap<>(t.length());

    // turn wheel is a proxy for s, storing the matching characters from t in s
    // and the location of the characters in s
    ArrayList<Object[]> turnWheel = new ArrayList<>();

    for (char c : t.toCharArray()) {
      requireCounts.put(c, requireCounts.getOrDefault(c, 0) + 1);
      currCounts.put(c, 0);
    }

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (requireCounts.containsKey(c)) {
        Object[] entry = new Object[2];
        entry[0] = c;
        entry[1] = i;
        turnWheel.add(entry);
      }
    }
    if (turnWheel.isEmpty()) {
      return "";
    }

    int left = 0, right = 0;
    boolean everSatisfied = false;
    boolean satisfied = false;
    int matchCount = 0, requiredMatchCount = requireCounts.size();

    int lbound = 0, rbound = s.length() - 1;

    currCounts.put((char) turnWheel.get(left)[0], 1);
    if (requireCounts.get(turnWheel.get(left)[0]) == 1) {
      matchCount = 1;
    }
    satisfied = matchCount == requiredMatchCount;

    while (left < turnWheel.size() && right < turnWheel.size()) {
      if (satisfied) {
        everSatisfied = true;
      }

      // if condition satisfied, minimize from left
      if (satisfied) {
        int actualLeft = (int) turnWheel.get(left)[1];
        int actualRight = (int) turnWheel.get(right)[1];

        if ((rbound - lbound + 1) > (actualRight - actualLeft + 1)) {
          lbound = actualLeft;
          rbound = actualRight;
        }
        if (requireCounts.containsKey(turnWheel.get(left)[0])) {
          currCounts.put((char) turnWheel.get(left)[0], currCounts.get(turnWheel.get(left)[0]) - 1);
          satisfied =
              requireCounts.get(turnWheel.get(left)[0]) <= currCounts.get(turnWheel.get(left)[0]);
        }
        left++;
        matchCount = satisfied ? requiredMatchCount : matchCount - 1;
      }

      // if condition not satisfied, maximize from right
      else {
        right++;
        if (right < turnWheel.size() && requireCounts.containsKey(turnWheel.get(right)[0])) {
          int c = currCounts.get(turnWheel.get(right)[0]) + 1;
          matchCount = (requireCounts.get(turnWheel.get(right)[0]) == c) ?
              matchCount + 1 : matchCount;

          currCounts.put((char) turnWheel.get(right)[0], c);
          satisfied = requiredMatchCount <= matchCount;
        }
      }
    }

    if (everSatisfied) {
      return s.substring(lbound, rbound + 1);
    }
    return "";
  }

  public static String minWindowArray(String s, String t) {
    if (s.length() == 0 || t.length() == 0) {
      return "";
    }
    if (s.length() == 1 && s.equals(t)) {
      return t;
    }
    if (s.length() == 1) {
      return "";
    }

    int[] currCounts = new int[256];
    int[] requireCounts = new int[256];
    int requiredMatchCount = 0;

    // turn wheel is a proxy for s, storing the matching characters from t in s
    // and the location of the characters in s
    ArrayList<Object[]> turnWheel = new ArrayList<>();

    for (char c : t.toCharArray()) {
      if (requireCounts[c] == 0) {
        requiredMatchCount++;
      }
      requireCounts[c] = requireCounts[c] + 1;
    }

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (requireCounts[c] > 0) {
        Object[] entry = new Object[2];
        entry[0] = c;
        entry[1] = i;
        turnWheel.add(entry);
      }
    }
    if (turnWheel.isEmpty()) {
      return "";
    }

    int left = 0, right = 0;
    boolean everSatisfied = false;
    boolean satisfied = false;
    int matchCount = 0;

    int lbound = 0, rbound = s.length() - 1;

    currCounts[(char) turnWheel.get(left)[0]] = 1;
    if (requireCounts[(char) turnWheel.get(left)[0]] == 1) {
      matchCount = 1;
    }
    satisfied = matchCount == requiredMatchCount;

    while (left < turnWheel.size() && right < turnWheel.size()) {
      if (satisfied) {
        everSatisfied = true;
      }

      // if condition satisfied, minimize from left
      if (satisfied) {
        int actualLeft = (int) turnWheel.get(left)[1];
        int actualRight = (int) turnWheel.get(right)[1];

        if ((rbound - lbound + 1) > (actualRight - actualLeft + 1)) {
          lbound = actualLeft;
          rbound = actualRight;
        }
        if (requireCounts[(char) turnWheel.get(left)[0]] > 0) {
          currCounts[(char) turnWheel.get(left)[0]] = currCounts[(char) turnWheel.get(left)[0]] - 1;
          satisfied =
              requireCounts[(char) turnWheel.get(left)[0]] <= currCounts[(char) turnWheel
                  .get(left)[0]];
        }
        left++;
        matchCount = satisfied ? requiredMatchCount : matchCount - 1;
      }

      // if condition not satisfied, maximize from right
      else {
        right++;
        if (right < turnWheel.size() && requireCounts[(char) turnWheel.get(right)[0]] > 0) {
          int c = currCounts[(char) turnWheel.get(right)[0]] + 1;
          matchCount = (requireCounts[(char) turnWheel.get(right)[0]] == c) ?
              matchCount + 1 : matchCount;

          currCounts[(char) turnWheel.get(right)[0]] = c;
          satisfied = requiredMatchCount <= matchCount;
        }
      }
    }

    if (everSatisfied) {
      return s.substring(lbound, rbound + 1);
    }
    return "";
  }

  public static void main(String[] args) {
    assertEquals("ABC", minWindow("ABC", "ABC"));
    assertEquals("ABC", minWindow("111ABC111111ABVC", "ABC"));
    assertEquals("A1B1C", minWindow("111A1B1C111111A888B888V111111C99A99B1222222aaaaaC", "ABC"));
    assertEquals("CYAYB", minWindow("BYBYCYCYAYBY", "ABC"));
    assertEquals("BANC", minWindow("ADOBECODEBANC", "ABC"));
    assertEquals("", minWindow("1234534523452345234523452345", "ABC"));
    assertEquals("", minWindow("1234534523452BCA345234523", "ABBC"));
    assertEquals("BCA34B", minWindow("A12345345C23452BCA34B523B4523", "ABBC"));
    assertEquals("A", minWindow("AB", "A"));

    assertEquals("ABC", minWindowArray("ABC", "ABC"));
    assertEquals("ABC", minWindowArray("111ABC111111ABVC", "ABC"));
    assertEquals("A1B1C",
        minWindowArray("111A1B1C111111A888B888V111111C99A99B1222222aaaaaC", "ABC"));
    assertEquals("CYAYB", minWindowArray("BYBYCYCYAYBY", "ABC"));
    assertEquals("BANC", minWindowArray("ADOBECODEBANC", "ABC"));
    assertEquals("", minWindowArray("1234534523452345234523452345", "ABC"));
    assertEquals("", minWindowArray("1234534523452BCA345234523", "ABBC"));
    assertEquals("BCA34B", minWindowArray("A12345345C23452BCA34B523B4523", "ABBC"));
    assertEquals("A", minWindowArray("AB", "A"));
  }
}
