package me.mourjo;

/*
https://leetcode.com/problems/last-substring-in-lexicographical-order/
Given a string s, return the last substring of s in lexicographical order.

Example 1:
Input: "abab"
Output: "bab"
Explanation: The substrings are ["a", "ab", "aba", "abab", "b", "ba", "bab"]. The lexicographically maximum substring is "bab".

Example 2:
Input: "leetcode"
Output: "tcode"
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LastLexicographicSubstringCombination {

  public static String lastSubstringSlower(String s) {
    char[] ch = s.toCharArray();
    int maxch = (char) 0;
    List<Integer> subs = new ArrayList<>();
    for (int c : ch) {
      if (c > maxch) {
        maxch = c;
      }
    }
    Character cc = null;
    for (int i = 0; i < ch.length; i++) {
      if (null != cc && cc == ch[i]) {
        continue;
      }
      if (ch[i] == maxch) {
        subs.add(i);
      }
      cc = ch[i];
    }

    Collections.sort(subs, new Comparator<Integer>() {
      public int compare(Integer i, Integer j) {
        while (i < ch.length && j < ch.length) {
          int r = Character.compare(ch[i], ch[j]);
          if (r != 0) {
            return r;
          }
          i++;
          j++;
        }
        if (i == ch.length && j == ch.length) {
          return 0;
        }
        if (i == ch.length) {
          return -1;
        }
        return 1;
      }
    });

    return s.substring(subs.get(subs.size() - 1));
  }

  public static int getBetter(String s, int current, int proposed) {
    if (current == proposed) {
      return current;
    }
    int i = current, j = proposed;
    int size = s.length();

    while (i < size && j < size) {
      int r = Character.compare(s.charAt(i), s.charAt(j));
      if (r < 0) {
        return proposed;
      }
      if (r > 0) {
        return current;
      }
      i++;
      j++;
    }

    if (i == size && j == size) {
      return current;
    }
    if (i == size) {
      return proposed;
    }
    return current;
  }

  public static String lastSubstringSlow(String s) {
    if (s.length() == 0) {
      return "";
    }
    if (s.length() == 1) {
      return s;
    }

    char maxch = s.charAt(0);
    int candidate = -1;

    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) >= maxch) {
        maxch = s.charAt(i);
        candidate = i;
      }
    }

    Character last = null;
    for (int i = 0; i < s.length(); i++) {
      if (null != last && s.charAt(i) == last) {
        continue;
      }
      if (s.charAt(i) == maxch) {
        candidate = getBetter(s, candidate, i);
      }
      last = s.charAt(i);
    }

    return s.substring(candidate);
  }

  public static String lastSubstring(String s) {
    if (s.length() <= 1) {
      return s;
    }

    char[] ch = s.toCharArray();
    int size = s.length();

    // find i such that s.substring(i) is the last (it will be a suffix always)
    int soln = 0; // pointer to starting idx of best known solution
    int cand = 1; // pointer to starting idx of candidate being evaluated
    int len = 0; // length of the substring

    while (cand + len < size) {
      if (ch[soln + len] == ch[cand + len]) {
        len++;
      } else if (ch[soln + len] > ch[cand + len]) {
        cand = cand + len + 1;
        len = 0;
      } else {
        soln = cand++;
        len = 0;
      }
    }

    // len(soln) is always >= len(cand), so no need to check for length exhaustion
    return s.substring(soln);
  }

  public static void main(String[] args) {
    assertEquals("bab", lastSubstring("abab"));
    assertEquals("d", lastSubstring("abcd")); // a, ab, abc, abcd, b, bc, bcd, c, cd, d
    assertEquals("dd", lastSubstring("abcdd"));
    assertEquals("e", lastSubstring("abcde"));
    assertEquals("dab", lastSubstring("abcdab"));
    assertEquals("dad", lastSubstring("abcdad"));
    assertEquals("ddad", lastSubstring("abcddad"));
    assertEquals("daabc", lastSubstring("daabc"));
    assertEquals("dab", lastSubstring("daabdab"));

    // d da dad dadd dadda daddad daddadd daddadda daddaddad
    // d dd ddad ddadd ddadda ddaddad
    // d da dad dadd dadda daddad
    // d dd dda ddad
    // d
    //
    //
    assertEquals("ddaddad", lastSubstring("daddaddad"));
    assertEquals("zddaddad", lastSubstring("dazddaddad"));
    assertEquals("zzzzzzzzzzzzzzzzzz", lastSubstring("zzzzzzzzzzzzzzzzzz"));
    assertEquals("zaza", lastSubstring("zaza"));
    assertEquals("zaza", lastSubstring("azaza"));
    assertEquals("zaza", lastSubstring("aazaza"));
    assertEquals("zzaza", lastSubstring("aazzaza"));
    assertEquals("zzzzaza", lastSubstring("aazzaaaazzzzaza"));

    String longstring = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    assertEquals(longstring, lastSubstring(longstring));


  }
}
