package me.mourjo.y22;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

/*
https://leetcode.com/problems/largest-component-size-by-common-factor/
Given a non-empty array of unique positive integers A, consider the following graph:

There are A.length nodes, labelled A[0] to A[A.length - 1];
There is an edge between A[i] and A[j] if and only if A[i] and A[j] share a common factor
greater than 1.
Return the size of the largest connected component in the graph.

Example 1:
Input: [4,6,15,35]
Output: 4

Example 2:
Input: [20,50,9,63]
Output: 2

Example 3:
Input: [2,3,6,7,4,12,21,39]
Output: 8

Note:
1 <= A.length <= 20000
1 <= A[i] <= 100000
 */
public class LargestComponentSizeByCommonFactor {

    public static int largestComponentSize(int[] node) {
        if (node == null || node.length == 0) {
            return 0;
        }
        if (node.length == 1) {
            return 1;
        }

        Map<Integer, Integer> factorToUnion = new HashMap<>();
        UnionFindCount u = new UnionFindCount(node.length);

        for (int i = 0; i < node.length; i++) {
            int val = node[i];
            for (int factor = 2; factor * factor <= val; factor++) {
                if (val % factor == 0) {
                    if (!factorToUnion.containsKey(factor)) {
                        factorToUnion.put(factor, i);
                    } else {
                        u.union(factorToUnion.get(factor), i);
                    }

                    int otherFactor = val / factor;
                    if (!factorToUnion.containsKey(otherFactor)) {
                        factorToUnion.put(otherFactor, i);
                    } else {
                        u.union(factorToUnion.get(otherFactor), i);
                    }
                }
            }
            if (!factorToUnion.containsKey(val)) {
                factorToUnion.put(val, i);
            } else {
                u.union(factorToUnion.get(val), i);
            }
        }
        return u.maxSize;
    }

    public static void main(String[] args) {
        assertEquals(4, largestComponentSize(new int[]{4, 6, 15, 35}));
        assertEquals(1, largestComponentSize(new int[]{73, 79, 83}));
        assertEquals(6, largestComponentSize(new int[]{73, 79, 83, 146, 158, 166}));
        assertEquals(3, largestComponentSize(new int[]{6059, 73, 79, 83}));

        assertEquals(2, largestComponentSize(new int[]{20, 50, 9, 63}));
        assertEquals(8, largestComponentSize(new int[]{2, 3, 6, 7, 4, 12, 21, 39}));
        assertEquals(2, largestComponentSize(new int[]{626, 939}));
        assertEquals(1, largestComponentSize(new int[]{229, 2, 3}));
        assertEquals(3, largestComponentSize(new int[]{2, 3, 6}));
        assertEquals(3, largestComponentSize(new int[]{2, 3, 5, 6, 25}));
        assertEquals(6, largestComponentSize(new int[]{2, 3, 5, 6, 25, 30}));
        assertEquals(6, largestComponentSize(new int[]{2, 3, 5, 6, 25, 30, 37}));
        assertEquals(6, largestComponentSize(new int[]{37, 2, 3, 5, 6, 25, 30}));
        assertEquals(8, largestComponentSize(new int[]{2, 3, 5, 6, 25, 30, 37, 74}));
        assertEquals(10, largestComponentSize(new int[]{2, 3, 5, 6, 25, 30, 37, 74, 111, 185}));
        assertEquals(10, largestComponentSize(new int[]{2, 3, 5, 6, 25, 30, 37, 74, 111, 185, 7}));
        assertEquals(10,
            largestComponentSize(new int[]{2, 3, 5, 6, 25, 30, 37, 74, 111, 185, 7, 49}));
        assertEquals(10,
            largestComponentSize(new int[]{2, 3, 5, 6, 25, 30, 37, 74, 111, 77, 185, 7, 49, 1771}));
        assertEquals(11,
            largestComponentSize(
                new int[]{2, 3, 5, 6, 25, 30, 4, 37, 74, 111, 77, 185, 7, 49, 1771}));

        assertEquals(3, largestComponentSize(new int[]{401, 802, 1203}));

        assertEquals(84, largestComponentSize(new int[]{
            2, 7, 522, 526, 535, 26, 944, 35, 519, 45, 48, 567, 266, 68, 74, 591, 81, 86, 602,
            93, 610, 621, 111, 114, 629, 641, 131, 651, 142, 659, 669, 161, 674, 163, 180,
            187, 190, 194, 195, 206, 207, 218, 737, 229, 240, 757, 770, 260, 778, 270, 272,
            785, 274, 290, 291, 292, 296, 810, 816, 314, 829, 833, 841, 349, 880, 369, 147,
            897, 387, 390, 905, 405, 406, 407, 414, 416, 417, 425, 938, 429, 432, 926, 959,
            960, 449, 963, 966, 929, 457, 463, 981, 985, 79, 487, 1000, 494, 508
        }));
    }

    static class UnionFindCount {

        // keep indexes in parent
        int[] parentOf;
        int[] size;
        int maxSize;

        UnionFindCount(int n) {
            parentOf = new int[n];
            size = new int[n];
            maxSize = 1;

            for (int i = 0; i < n; i++) {
                parentOf[i] = i;
                size[i] = 1;
            }
        }

        int findAncestor(int x) {
            while (x != parentOf[x]) {
                // short circuit
                parentOf[x] = parentOf[parentOf[x]];
                x = parentOf[x];
            }
            return x;
        }

        int union(int x, int y) {
            // make x the parent of y
            int ancestorX = findAncestor(x);
            int ancestorY = findAncestor(y);

            if (ancestorX != ancestorY) {
                parentOf[ancestorY] = ancestorX;
                size[ancestorX] += size[ancestorY];
                maxSize = Math.max(maxSize, size[ancestorX]);
            }
            return ancestorX;
        }
    }
}

