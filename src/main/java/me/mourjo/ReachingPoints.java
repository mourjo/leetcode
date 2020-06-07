package me.mourjo;

/*
https://leetcode.com/problems/reaching-points/
A move consists of taking a point (x, y) and transforming it to either (x, x+y) or (x+y, y).

Given a starting point (sx, sy) and a target point (tx, ty), return True if and only if a
sequence of moves exists to transform the point (sx, sy) to (tx, ty). Otherwise, return False.

Examples:
Input: sx = 1, sy = 1, tx = 3, ty = 5
Output: True
Explanation:
One series of moves that transforms the starting point to the target is:
(1, 1) -> (1, 2)
(1, 2) -> (3, 2)
(3, 2) -> (3, 5)

Input: sx = 1, sy = 1, tx = 2, ty = 2
Output: False

Input: sx = 1, sy = 1, tx = 1, ty = 1
Output: True

Note:

sx, sy, tx, ty will all be integers in the range [1, 10^9].
 */

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReachingPoints {

  public static boolean reachingPoints(int sx, int sy, int tx, int ty) {
    int ix = tx, iy = ty;

    while (ix >= sx && iy >= sy) {
      if (ix == iy) {
        return (ix == sx && iy == sy);
      }

      if (ix == sx && iy == sy) {
        return true;
      }

      if (ix > iy) {
        if (iy > sy) {
          ix = ix % iy;
        } else {
          return (ix - sx) % iy == 0;
        }
      } else {
        if (ix > sx) {
          iy = iy % ix;
        } else {
          return (iy - sy) % ix == 0;
        }
      }
    }
    return false;
  }

  public static void main(String[] args) {
    assertTrue(reachingPoints(1, 1, 3, 5));
    assertTrue(reachingPoints(7, 20, 27, 74));
    assertTrue(reachingPoints(7, 20, 67, 20));
    assertTrue(reachingPoints(7, 20, 87, 20));
    assertTrue(reachingPoints(7, 20, 34, 61));
    assertTrue(reachingPoints(7, 20, 61, 27));
    assertTrue(reachingPoints(7, 20, 61, 27));
    assertFalse(reachingPoints(7, 20, 34, 47));
    assertTrue(reachingPoints(21, 23, 86, 151));
    assertFalse(reachingPoints(1, 5, 999999997, 5));
  }
}
