package me.mourjo.y22;

/*
https://leetcode.com/problems/similar-string-groups/
Two strings X and Y are similar if we can swap two letters (in different positions) of X,
so that it equals Y. Also two strings X and Y are similar if they are equal.

For example, "tars" and "rats" are similar (swapping at positions 0 and 2), and "rats" and
"arts" are similar, but "star" is not similar to "tars", "rats", or "arts".

Together, these form two connected groups by similarity: {"tars", "rats", "arts"} and {"star"}.
Notice that "tars" and "arts" are in the same group even though they are not similar.  Formally,
each group is such that a word is in the group if and only if it is similar to at least one
other word in the group.

We are given a list A of strings.  Every string in A is an anagram of every other string in A.
How many groups are there?

Example 1:
Input: A = ["tars","rats","arts","star"]
Output: 2

Constraints:
1 <= A.length <= 2000
1 <= A[i].length <= 1000
A.length * A[i].length <= 20000
All words in A consist of lowercase letters only.
All words in A have the same length and are anagrams of each other.
The judging time limit has been increased for this question.
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimilarStrings {

    public static int numSimilarGroups(String[] A) {
        if (A.length <= 1) {
            return A.length;
        }
        UnionFind u = new UnionFind(A.length);
        for (int i = 0; i < A.length; i++) {
            similarity(A, u, i);
        }

        return u.clusterCount;
    }

    public static void similarity(String[] a, UnionFind union, int i) {
        String focus = a[i];
        for (int k = i + 1; k < a.length; k++) {
            String item = a[k];
            int c = 0;
            for (int j = 0; j < item.length() && c <= 2; j++) {
                if (item.charAt(j) != focus.charAt(j)) {
                    c++;
                }
            }
            if (c == 0 || c == 2) {
                union.union(k, i);
            }
        }
    }

    public static void main(String[] args) {
        assertEquals(0, numSimilarGroups(new String[]{}));
        assertEquals(1, numSimilarGroups(new String[]{"a"}));
        assertEquals(1, numSimilarGroups(new String[]{"a", "a"}));
        assertEquals(2, numSimilarGroups(new String[]{"abcdef", "fedcba", "abcdef"}));
        assertEquals(2, numSimilarGroups(new String[]{"abcd", "dcba", "abcd"}));
        assertEquals(1, numSimilarGroups(new String[]{"abc", "cba", "abc"}));
        assertEquals(2, numSimilarGroups(new String[]{"tars", "rats", "arts", "star"}));
        assertEquals(2, numSimilarGroups(new String[]{"tars", "rats", "arts", "star", "tsar"}));
        assertEquals(1,
            numSimilarGroups(new String[]{"abcd", "bacd", "badc", "bdac", "dbac", "dbca", "dcba"}));
        assertEquals(2,
            numSimilarGroups(new String[]{"abcd", "bacd", "badc", "bdac", "dbac", "dcba"}));
        assertEquals(3, numSimilarGroups(new String[]{"abcd", "bacd", "badc", "dbac", "dcba"}));
        assertEquals(2, numSimilarGroups(new String[]{"abcd", "bacd", "badc", "dcba"}));
        assertEquals(3, numSimilarGroups(new String[]{"abcd", "badc", "dcba"}));
        assertEquals(2, numSimilarGroups(new String[]{"abcd", "dcba"}));
    }

    static class UnionFind {

        int[] parent;
        int clusterCount;

        UnionFind(int size) {
            parent = new int[size];
            clusterCount = size;
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        void union(int i, int j) {
            if (i == j) {
                return;
            }
            int pi = find(i);
            int pj = find(j);

            if (pi != pj) {
                if (i < j) {
                    parent[pi] = pj;
                } else {
                    parent[pj] = pi;
                }
                clusterCount--;
            }
        }

        int find(int i) {
            while (i != parent[i]) {
                parent[i] = parent[parent[i]];
                i = parent[i];
            }
            return i;
        }
    }
}
