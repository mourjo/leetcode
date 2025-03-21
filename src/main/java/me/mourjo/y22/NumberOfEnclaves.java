package me.mourjo.y22;

/* https://leetcode.com/problems/number-of-enclaves/
Given a 2D array A, each cell is 0 (representing sea) or 1 (representing land)
A move consists of walking from one land square 4-directionally to another land square,
or off the boundary of the grid.

Return the number of land squares in the grid for which we cannot walk off the boundary
of the grid in any number of moves.

Example 1:
Input: [[0,0,0,0],[1,0,1,0],[0,1,1,0],[0,0,0,0]]
Output: 3
Explanation:
There are three 1s that are enclosed by 0s, and one 1 that isn't enclosed because it's
on the boundary.


Example 2:
Input: [[0,1,1,0],[0,0,1,0],[0,0,1,0],[0,0,0,0]]
Output: 0
Explanation:
All 1s are either on the boundary or can reach the boundary.

Note:
1 <= A.length <= 500
1 <= A[i].length <= 500
0 <= A[i][j] <= 1
All rows have the same size.
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.Queue;


public class NumberOfEnclaves {

    // return number of locations reachable from this position
    public static int explore(int[][] a, boolean[][] marker, int r, int c) {
        Queue<Integer> rows = new LinkedList<>();
        Queue<Integer> cols = new LinkedList<>();
        int i, j;
        int count = 0;

        rows.add(r);
        cols.add(c);
        marker[r][c] = true;

        while (!rows.isEmpty()) {
            i = rows.remove();
            j = cols.remove();

            if (i - 1 >= 0) {
                // i-1, j
                if (!marker[i - 1][j] && a[i - 1][j] == 1) {
                    count++;
                    marker[i - 1][j] = true;
                    rows.add(i - 1);
                    cols.add(j);
                }
            }

            if (j - 1 >= 0) {
                // i, j-1
                if (!marker[i][j - 1] && a[i][j - 1] == 1) {
                    count++;
                    marker[i][j - 1] = true;
                    rows.add(i);
                    cols.add(j - 1);
                }
            }

            if (j + 1 < a[0].length) {
                // i, j+1
                if (!marker[i][j + 1] && a[i][j + 1] == 1) {
                    count++;
                    marker[i][j + 1] = true;
                    rows.add(i);
                    cols.add(j + 1);
                }
            }

            if (i + 1 < a.length) {
                // i+1, j
                if (!marker[i + 1][j] && a[i + 1][j] == 1) {
                    count++;
                    marker[i + 1][j] = true;
                    rows.add(i + 1);
                    cols.add(j);
                }
            }
        }

        return count;
    }

    public static int countOnes(int[][] a) {
        int c = 0;
        for (int[] row : a) {
            for (int item : row) {
                if (item == 1) {
                    c++;
                }
            }
        }
        return c;
    }

    public static int numEnclaves(int[][] a) {
        if (a.length == 0 || a[0].length == 0) {
            return 0;
        }
        int count = 0;
        boolean[][] marker = new boolean[a.length][a[0].length];
        int i, j;

        i = 0;
        for (j = 0; j < a[0].length; j++) {
            if (a[i][j] == 1 && !marker[i][j]) {
                count += explore(a, marker, i, j) + 1;
            }
        }

        j = 0;
        for (i = 0; i < a.length; i++) {
            if (a[i][j] == 1 && !marker[i][j]) {
                count += explore(a, marker, i, j) + 1;
            }
        }

        i = a.length - 1;
        for (j = 0; j < a[0].length; j++) {
            if (a[i][j] == 1 && !marker[i][j]) {
                count += explore(a, marker, i, j) + 1;
            }
        }

        j = a[0].length - 1;
        for (i = 0; i < a.length; i++) {
            if (a[i][j] == 1 && !marker[i][j]) {
                count += explore(a, marker, i, j) + 1;
            }
        }

        return countOnes(a) - count;
    }


    public static void main(String[] args) {
        assertEquals(3, numEnclaves(new int[][]
            {{0, 0, 0, 0}, {1, 0, 1, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}}));
        assertEquals(0, numEnclaves(new int[][]
            {{0, 1, 1, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 0, 0}}));

    }
}
