package me.mourjo.y22;
/*
https://leetcode.com/problems/minimum-knight-moves/

In an infinite chess board with coordinates from -infinity to +infinity, you have a knight
at square [0, 0].

A knight has 8 possible moves it can make, as illustrated below. Each move is two squares
in a cardinal direction, then one square in an orthogonal direction.

Return the minimum number of steps needed to move the knight to the square [x, y].  It is
guaranteed the answer exists.

Example 1:
Input: x = 2, y = 1
Output: 1
Explanation: [0, 0] → [2, 1]

Example 2:
Input: x = 5, y = 5
Output: 4
Explanation: [0, 0] → [2, 1] → [4, 2] → [3, 4] → [5, 5]

Constraints:
|x| + |y| <= 300
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinimumKnightMoves {

    private static final int OFFSET = 4;

    public static void addIfNotSeen(int x, int y, int currLevel, boolean[][] seen,
                                    Queue<Integer> xq,
                                    Queue<Integer> yq, Queue<Integer> level) {
        if (x > 300 || x < -4 || y > 300 || y < -4) {
            return;
        }
        if (seen[OFFSET + x][OFFSET + y]) {
            return;
        }
        seen[OFFSET + x][OFFSET + y] = true;
        xq.add(x);
        yq.add(y);
        level.add(currLevel + 1);
    }

    public static int minKnightMovesSlow(int x, int y) {
        x = Math.abs(x);
        y = Math.abs(y);
        Queue<Integer> xq = new LinkedList<>();
        Queue<Integer> yq = new LinkedList<>();
        Queue<Integer> distance = new LinkedList<>();
        xq.add(0);
        yq.add(0);
        distance.add(0);
        boolean[][] seen = new boolean[300 + OFFSET + 1][300 + OFFSET + 1];
        seen[OFFSET][OFFSET] = true;

        while (!xq.isEmpty()) {
            int xc = xq.remove();
            int yc = yq.remove();
            int d = distance.remove();

            if (xc == x && yc == y) {
                return d;
            }

            addIfNotSeen(xc + 2, yc + 1, d, seen, xq, yq, distance);
            addIfNotSeen(xc + 2, yc - 1, d, seen, xq, yq, distance);
            addIfNotSeen(xc - 2, yc + 1, d, seen, xq, yq, distance);
            addIfNotSeen(xc - 2, yc - 1, d, seen, xq, yq, distance);

            addIfNotSeen(xc + 1, yc + 2, d, seen, xq, yq, distance);
            addIfNotSeen(xc + 1, yc - 2, d, seen, xq, yq, distance);
            addIfNotSeen(xc - 1, yc + 2, d, seen, xq, yq, distance);
            addIfNotSeen(xc - 1, yc - 2, d, seen, xq, yq, distance);
        }
        return -1;
    }

    public static int minKnightMoves(int x, int y) {
        return dfs(Math.abs(x), Math.abs(y), new HashMap<String, Integer>(310));
    }

    public static int dfs(int x, int y, Map<String, Integer> m) {
        if (x + y == 0) {
            return 0;
        }
        if (x + y == 2) {
            return 2;
        }
        String k = x + "_" + y;
        if (m.containsKey(k)) {
            return m.get(k);
        }
        int min = Math.min(dfs(Math.abs(x - 1), Math.abs(y - 2), m),
                dfs(Math.abs(x - 2), Math.abs(y - 1), m));
        m.put(k, min + 1);
        return min + 1;
    }

    public static void main(String[] args) {
        assertEquals(1, minKnightMoves(2, 1));
        assertEquals(4, minKnightMoves(5, 5));
        assertEquals(150, minKnightMoves(0, -300));
        assertEquals(2, minKnightMoves(1, 1));
        assertEquals(200, minKnightMoves(300, 300));
        assertEquals(200, minKnightMoves(300, -300));
        assertEquals(200, minKnightMoves(-300, -300));
        assertEquals(200, minKnightMoves(-300, 300));
    }
}
