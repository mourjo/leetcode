package me.mourjo.y22;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/*
https://leetcode.com/problems/palindrome-permutation-ii/

Given a string s, return all the palindromic permutations (without duplicates) of it. Return an empty list if no palindromic permutation could be form.

Example 1:

Input: "aabb"
Output: ["abba", "baab"]
Example 2:

Input: "abc"
Output: []
 */
public class PalindromePermulations {

    public static List<String> generatePalindromes(String s) {
        if (s.length() == 0) {
            return new ArrayList<>();
        }
        if (s.length() == 1) {
            return Arrays.asList(s);
        }
        int[] frequencies = new int[256];
        char[] chars = s.toCharArray();
        for (char ch : chars) {
            frequencies[ch]++;
        }

        boolean oddLen = s.length() % 2 == 1;
        StringBuilder starting = new StringBuilder();

        if (!oddLen) {
            if (Arrays.stream(frequencies).anyMatch(v -> v % 2 != 0)) {
                return new ArrayList<>();
            }
        } else {
            int oddCount = 0;
            char oddity = 0;
            for (int i = 0; i < frequencies.length; i++) {
                if (frequencies[i] % 2 == 1) {
                    oddCount++;
                    oddity = (char) i;
                }
            }
            if (oddCount != 1) {
                return new ArrayList<>();
            }
            frequencies[oddity]--;
            starting.append(oddity);
        }
        List<String> result = new LinkedList<>();
        dfs(frequencies, result, starting, s.length());
        return result;
    }

    public static void dfs(int[] frequencies, List<String> result, StringBuilder candidate,
        int size) {
        if (candidate.length() == size) {
            result.add(candidate.toString());
            return;
        }
        if (candidate.length() > size) {
            return;
        }

        for (int i = 0; i < frequencies.length; i++) {
            char c = (char) i;
            if (frequencies[c] > 0) {
                frequencies[c] -= 2;
                candidate.append(c);
                candidate.insert(0, c);

                dfs(frequencies, result, candidate, size);

                candidate.deleteCharAt(0);
                candidate.deleteCharAt(candidate.length() - 1);
                frequencies[c] += 2;
            }
        }
    }


    public static void main(String[] args) {
        assertEquals(Arrays.asList(), generatePalindromes(""));
        assertEquals(Arrays.asList("a"), generatePalindromes("a"));
        assertEquals(Arrays.asList("aaa"), generatePalindromes("aaa"));
        assertEquals(new ArrayList<String>(), generatePalindromes("abc"));
        assertEquals(Arrays.asList("aba"), generatePalindromes("aab"));
        assertIterableEquals(
            Arrays.asList("abba", "baab").stream().sorted().collect(Collectors.toList()),
            generatePalindromes("aabb").stream().sorted().collect(Collectors.toList()));

        assertIterableEquals(
            Arrays.asList("abzba", "bazab").stream().sorted().collect(Collectors.toList()),
            generatePalindromes("aabbz").stream().sorted().collect(Collectors.toList()));

    }
}
