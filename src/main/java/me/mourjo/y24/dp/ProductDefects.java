package me.mourjo.y24.dp;

import java.util.List;
import org.junit.jupiter.api.Assertions;

/*
https://www.youtube.com/watch?v=6X7Ha2PrDmM

Given an m x n binary matrix filled with 0's and 1's, find the largest square containing only 1's
and return its area.

Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
Output: 4

Input: matrix = [["0","1"],["1","0"]]
Output: 1

Input: matrix = [["0"]]
Output: 0

Constraints:
m == matrix.length
n == matrix[i].length
1 <= m, n <= 300
matrix[i][j] is '0' or '1'.
  */
public class ProductDefects {

    public static void main(String[] args) {
        var app = new ProductDefects();
        Assertions.assertEquals(4, app.maximalSquare(new char[][]{
            {'1','0','1','0','0'},
            {'1','0','1','1','1'},
            {'1','1','1','1','1'},
            {'1','0','0','1','0'},
        }));

        Assertions.assertEquals(1, app.maximalSquare(new char[][]{
            {'0','1'},
            {'1','0'},
        }));

        Assertions.assertEquals(0, app.maximalSquare(new char[][]{
            {'0'}
        }));
    }

    int[][] cache;

    public int maximalSquare(char[][] matrix) {
        cache = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < cache.length; i++) {
            for (int j = 0; j < cache[0].length; j++) {
                cache[i][j] = -1;
            }
        }

        maxSqAtPos(matrix, 0, 0);
        int maxLen = -1;
        for (int i = 0; i < cache.length; i++) {
            for (int j = 0; j < cache[0].length; j++) {
                if (cache[i][j] > maxLen) {
                    maxLen = cache[i][j];
                }

            }
        }

        return (int) Math.pow(maxLen,2);
    }

    int maxSqAtPos(char[][] matrix, int row, int col) {
        if (row >= matrix.length || col >= matrix[0].length) {
            return 0;
        }

        if (cache[row][col] != -1) {
            return cache[row][col];
        }

        int rightSq = maxSqAtPos(matrix, row, col+1);
        int downSq = maxSqAtPos(matrix, row+1, col);
        int diagSq = maxSqAtPos(matrix, row+1, col+1);

        if (matrix[row][col] == '0') {
            cache[row][col] = 0;
            return 0;
        }

        cache[row][col] = 1 + Math.min(rightSq, Math.min(downSq, diagSq));
        return cache[row][col];
    }

}
