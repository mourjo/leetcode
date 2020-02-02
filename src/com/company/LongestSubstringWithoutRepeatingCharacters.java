package com.company;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestSubstringWithoutRepeatingCharacters {
    /**
     * https://leetcode.com/problems/longest-substring-without-repeating-characters/submissions/
     * Given a string, find the length of the longest substring without repeating characters.
     *
     * Example 1:
     *
     * Input: "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3.
     * Example 2:
     *
     * Input: "bbbbb"
     * Output: 1
     * Explanation: The answer is "b", with the length of 1.
     * Example 3:
     *
     * Input: "pwwkew"
     * Output: 3
     * Explanation: The answer is "wke", with the length of 3.
     *              Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
     */
    public static int lengthOfLongestSubstring(String s) {
        int longest = 0, start = 0;
        Set<Character> workingSet = new HashSet<>();

        for (int current = 0; current < s.length(); current++) {
            char c = s.charAt(current);
            while (workingSet.contains(c)) {
                workingSet.remove(s.charAt(start));
                start++;
            }

            workingSet.add(c);
            longest = current - start + 1 > longest ? current - start + 1 : longest;

        }
        return longest;
    }

    public static int lengthOfLongestSubstring2(String s) {
        int longest = 0, start = 0;
        Map<Character, Integer> idx = new HashMap<>(256);

        for (int current = 0; current < s.length(); current++) {

            char c = s.charAt(current);
            if (idx.containsKey(c) && idx.get(c) >= start) {
                start = idx.get(c) + 1;
            }
            idx.put(c, current);

            longest = longest < current - start + 1 ? current - start + 1 : longest;
        }

        return longest;
    }

    public static void main(String[] args) {
        assert (lengthOfLongestSubstring2("abcabcbb") == 3);
        assert (lengthOfLongestSubstring2("abcd") == 4);
        assert (lengthOfLongestSubstring2("abba") == 2);
        assert (lengthOfLongestSubstring2("abcdecx12345c") == 9);
        assert (lengthOfLongestSubstring2("abcabcbb") == 3);
        assert (lengthOfLongestSubstring2("pwwkew") == 3);
    }

}
