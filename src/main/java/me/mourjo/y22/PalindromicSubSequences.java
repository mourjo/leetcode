package me.mourjo.y22;

/*
https://leetcode.com/problems/count-different-palindromic-subsequences/

Given a string S, find the number of different non-empty palindromic subsequences in S,
and return that number modulo 10^9 + 7.

A subsequence of a string S is obtained by deleting 0 or more characters from S.

A sequence is palindromic if it is equal to the sequence reversed.

Two sequences A_1, A_2, ... and B_1, B_2, ... are different if there is some i for which A_i != B_i.

Example 1:

Input: S = 'bccb'
Output: 6
Explanation:
The 6 different non-empty palindromic subsequences are 'b', 'c', 'bb', 'cc', 'bcb', 'bccb'.
Note that 'bcb' is counted only once, even though it occurs twice.
Example 2:

Input: S = 'abcdabcdabcdabcdabcdabcdabcdabcddcbadcbadcbadcbadcbadcbadcbadcba'
Output: 104860361
Explanation:
There are 3104860382 different non-empty palindromic subsequences, which is 104860361
modulo 10^9 + 7.

Note:
The length of S will be in the range [1, 1000].
Each character S[i] will be in the set {'a', 'b', 'c', 'd'}.

 */

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PalindromicSubSequences {

    static int m = 1_000_000_007;

    public static int countPalindromicSubsequences(String s) {
        long[][][] perms = new long[4][s.length()][s.length()];

        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = i; j < s.length(); j++) {
                for (int k = 0; k < 4; k++) {
                    char c = (char) ('a' + k);

                    if (i == j && s.charAt(j) == c) {
                        perms[k][i][j] = 1;
                    } else if (i == j) {
                        perms[k][i][j] = 0;
                    } else {
                        if (s.charAt(i) != c) {
                            perms[k][i][j] = perms[k][i + 1][j];
                        } else if (s.charAt(j) != c) {
                            perms[k][i][j] = perms[k][i][j - 1];
                        } else {

                            if (j == i + 1) {
                                // aa -> a, aa (irrespective of k)
                                // ab -> a, b
                                perms[k][i][j] = 2;
                            } else {
                                perms[k][i][j] = perms[0][i + 1][j - 1];
                                perms[k][i][j] %= m;
                                perms[k][i][j] += perms[1][i + 1][j - 1];
                                perms[k][i][j] %= m;
                                perms[k][i][j] += perms[2][i + 1][j - 1];
                                perms[k][i][j] %= m;
                                perms[k][i][j] += perms[3][i + 1][j - 1];
                                perms[k][i][j] %= m;
                                perms[k][i][j] += 2;
                                perms[k][i][j] %= m;
                            }
                        }
                    }
                }
            }
        }
        long t = perms[0][0][s.length() - 1];
        t %= m;
        t += perms[1][0][s.length() - 1];
        t %= m;
        t += perms[2][0][s.length() - 1];
        t %= m;
        t += perms[3][0][s.length() - 1];
        t %= m;

        return (int) t;
    }

    public static void main(String[] args) {
        assertEquals(1, countPalindromicSubsequences("a"));
        assertEquals(6, countPalindromicSubsequences("bccb"));
        assertEquals(744991227, countPalindromicSubsequences(
                "bddaabdbbccdcdcbbdbddccbaaccabbcacbadbdadbccddccdbdbdbdabdbddcccadddaaddbcbcbabdcaccaacabdbdaccbaacc"));
        assertEquals(104860361, countPalindromicSubsequences(
                "abcdabcdabcdabcdabcdabcdabcdabcddcbadcbadcbadcbadcbadcbadcbadcba"));
    }
}
