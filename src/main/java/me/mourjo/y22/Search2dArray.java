package me.mourjo.y22;

/* https://leetcode.com/problems/search-a-2d-matrix/
Write an efficient algorithm that searches for a value in an m x n matrix.
This matrix has the following properties:

Integers in each row are sorted from left to right.
The first integer of each row is greater than the last integer of the previous row.


Example 1:
Input: matrix = [
[1,   3,  5,  7],
[10, 11, 16, 20],
[23, 30, 34, 50]
]

target = 3
Output: true


Example 2:
Input: matrix = [
[1,   3,  5,  7],
[10, 11, 16, 20],
[23, 30, 34, 50] ]

target = 13
Output = false

 */

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Search2dArray {

    public static boolean binarySearch(int[] a, int k) {
        int low = 0;
        int high = a.length - 1;

        while (low < high) {
            int mid = (low + high) / 2;
            if (a[mid] == k) {
                return true;
            }
            if (k < a[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return a[low] == k;
    }

    public static boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0) {
            return false;
        }

        for (int[] row : matrix) {
            if (row.length == 0) {
                continue;
            }

            if (row[row.length - 1] == target) {
                return true;
            }
            if (row[0] == target) {
                return true;
            }

            if (target <= row[row.length - 1] && target >= row[0]) {
                return binarySearch(row, target);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        assertTrue(searchMatrix(new int[][]{
                {1, 3, 5, 7},
                {10, 11, 16, 20},
                {23, 30, 34, 50}},
            3));

        assertTrue(searchMatrix(new int[][]{
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 50}
        }, 1));

        assertFalse(searchMatrix(new int[][]{
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 50}
        }, 13));

        assertFalse(searchMatrix(new int[][]{}, 13));
        assertFalse(searchMatrix(new int[][]{{}, {}, {}}, 13));
        assertFalse(searchMatrix(new int[][]{{1}, {2}, {3}}, 13));
        assertTrue(searchMatrix(new int[][]{{1}, {2}, {3}}, 2));
        assertTrue(searchMatrix(new int[][]{{1, 2}, {}, {3, 10, 100, 10000}}, 2));
        assertTrue(searchMatrix(new int[][]{{1, 2}, {}, {3, 10, 100, 10000}}, 10));
        assertTrue(searchMatrix(new int[][]{{1, 2}, {}, {3, 10, 100, 10000}}, 100));
        assertFalse(searchMatrix(new int[][]{{1, 2}, {}, {3, 10, 100, 10000}}, 21));
    }

}
