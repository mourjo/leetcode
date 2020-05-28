package me.mourjo;

import java.util.*;

public class LongestPalindromicSubstring {
    /**
     * Given a string s, find the longest palindromic substring in s.
     * You may assume that the maximum length of s is 1000.
     *
     * Example 1:
     *
     * Input: "babad"
     * Output: "bab"
     * Note: "aba" is also a valid answer.
     * Example 2:
     *
     * Input: "cbbd"
     * Output: "bb"
     */
    static class Lim implements Comparable {
        int start, end;
        Lim (int s, int e) {
            start = s;
            end = e;
        }

        @Override
        public String toString() {
            return "Lim{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof Lim) {
                Lim other = (Lim) o;
                return  - ((end - start) - (other.end - other.start));
            }
            else return  -1;
        }
    }

    static Map<Integer,Map<Integer,Boolean>> memo;

    public static boolean isPalin(String s, int start, int end) {
        if (memo.containsKey(start) && memo.get(start).containsKey(end))
            return memo.get(start).get(end);

        boolean result = false;
        if (start <= end && start >= 0 && end < s.length()) {
            if (s.charAt(start) == s.charAt(end)) {
                result = isPalin(s, start + 1, end - 1);
            }
            else
                result = false;
        }
        else result = true;

        if (memo.containsKey(start)) {
            memo.get(start).put(end, result);
        }
        else {
            Map<Integer,Boolean> inner = new HashMap<>();
            inner.put(end, result);
            memo.put(start, inner);
        }
        return result;
    }


    public static String longestPalindrome1(String s) {
        memo = new HashMap<>(1000);
        Map<Character,List<Integer>> locations = new HashMap<>();
        List<Lim> ranges = new ArrayList<>(1000);
        int n = s.length();

        for (int i = 0; i < n; i++) {
            if (locations.containsKey(s.charAt(i))) {
                locations.get(s.charAt(i)).add(i);
            }
            else {
                List<Integer> x = new ArrayList<Integer>();
                x.add(i);
                locations.put(s.charAt(i), x);
            }
        }

        for (List<Integer> x : locations.values()) {
            if (x.size() > 1) {
                for (int k = 0; k < x.size()-1 ; k++) {
                    for (int m = k+1; m < x.size(); m++) {
                        ranges.add(new Lim(x.get(k),x.get(m)));
                    }
                }
            }
        }

        Collections.sort(ranges);

        memo.clear();

        for (Lim r : ranges) {
            if (isPalin(s, r.start, r.end))
                return s.substring(r.start, r.end+1);
        }

        return s.length() == 0 ? "" : s.substring(0, 1);
    }


    /// --- Implemented after reading leetcode soln: ---

    public static int grow (String s, int l, int r) {
        // Return distance from center when palindrome

        // when centre is an element, initial length = -1, because if there is a match,
        // then the distance from the centre is (-1 + 1) = 0 and there will always be a match
        //
        // when centre is between elements, initial length = 0, because if there is a match,
        // then the distance from the centre is (0 + 1) = 1
        int length = (r-l)-1;
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            length++;
            l--;
            r++;
        }
        return length;
    }

    public static String longestPalindrome (String s) {
        String palin = s.length() == 0 ? "" : s.substring(0,1);
        int maxDist = 0;

        for (int i = 0; i < s.length()-1; i++) {
            int l1 = grow(s, i, i); // centre is the ith element
            int l2 = grow(s, i, i+1); // centre is between the ith (i+1)th element

            int localStart = 0, localEnd = 0;
            if (l1 >= l2) {
                //centre ith element
                localStart = i - l1;
                localEnd = i + l1;

            } else if (l2 >= l1) {
                // centre between i and i+1
                localStart = i - l2 + 1;
                localEnd = i + l2;
            }

            if (localEnd - localStart + 1 > maxDist) {
                maxDist = localEnd - localStart + 1;
                palin = s.substring(localStart, localEnd+1);
            }
        }
        return palin;
    }

    public static void main(String[] args) {
        Utilities.check(longestPalindrome("abcda"), "a");
        Utilities.check(longestPalindrome("babadb"), "bab", "aba");
        Utilities.check(longestPalindrome("babad"), "bab", "aba");
        Utilities.check(longestPalindrome("cbbd"), "bb");
        Utilities.check(longestPalindrome("abc"), "a");
        Utilities.check(longestPalindrome("madam i madam 999"), "madam i madam");
        Utilities.check(longestPalindrome("ccc"), "ccc");
    }
}
