package me.mourjo.y22;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Stack;

/*
https://leetcode.com/problems/asteroid-collision/

We are given an array asteroids of integers representing asteroids in a row.

For each asteroid, the absolute value represents its size, and the sign represents its direction
(positive meaning right, negative meaning left). Each asteroid moves at the same speed.

Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one
will explode. If both are the same size, both will explode. Two asteroids moving in the same
direction will never meet.

Example 1:
Input:
asteroids = [5, 10, -5]
Output: [5, 10]
Explanation:
The 10 and -5 collide resulting in 10.  The 5 and 10 never collide.

Example 2:
Input:
asteroids = [8, -8]
Output: []
Explanation:
The 8 and -8 collide exploding each other.

Example 3:
Input:
asteroids = [10, 2, -5]
Output: [10]
Explanation:
The 2 and -5 collide resulting in -5.  The 10 and -5 collide resulting in 10.

Example 4:
Input:
asteroids = [-2, -1, 1, 2]
Output: [-2, -1, 1, 2]
Explanation:
The -2 and -1 are moving left, while the 1 and 2 are moving right.
Asteroids moving the same direction never meet, so no asteroids will meet each other.

Note:
The length of asteroids will be at most 10000.
Each asteroid will be a non-zero integer in the range [-1000, 1000]..
 */
public class CollidingAsteroids {

  static public int[] asteroidCollision(int[] a) {
    if (a.length <= 1) {
      return a;
    }
    Stack<Integer> stack = new Stack<>();

    for (int ast : a) {
      if (!stack.isEmpty() && ast < 0 && stack.peek() > 0) {
        int astAbs = -ast;
        if (astAbs > stack.peek()) {
          boolean lost = false;
          while (!lost && !stack.isEmpty() && stack.peek() > 0) {
            int peekAbs = stack.peek();
            if (astAbs > peekAbs) {
              stack.pop();
            } else if (astAbs == peekAbs) {
              stack.pop();
              lost = true;
            } else {
              lost = true;
            }
          }

          if (!lost && (stack.isEmpty() || stack.peek() < 0)) {
            stack.push(ast);
          }

        } else {
          if (!stack.isEmpty() && astAbs == stack.peek()) {
            stack.pop();
          }
        }
      } else {
        stack.push(ast);
      }
    }

    int[] result = new int[stack.size()];
    int count = stack.size() - 1;
    while (!stack.isEmpty()) {
      result[count--] = stack.pop();
    }

    return result;
  }

  public static void main(String[] args) {
    assertArrayEquals(new int[]{5, 10}, asteroidCollision(new int[]{5, 10, -5}));
    assertArrayEquals(new int[]{}, asteroidCollision(new int[]{8, -8}));
    assertArrayEquals(new int[]{10}, asteroidCollision(new int[]{10, 2, -5}));
    assertArrayEquals(new int[]{-2, -1, 1, 2}, asteroidCollision(new int[]{-2, -1, 1, 2}));
    assertArrayEquals(new int[]{1, 2, 3, 4, 5}, asteroidCollision(new int[]{1, 2, 3, 4, 5}));
    assertArrayEquals(new int[]{-1, -2, -3, -4, -5},
        asteroidCollision(new int[]{-1, -2, -3, -4, -5}));
    assertArrayEquals(new int[]{-1, 2}, asteroidCollision(new int[]{-1, 2}));
    assertArrayEquals(new int[]{-2}, asteroidCollision(new int[]{1, -2}));
    assertArrayEquals(new int[]{-2, -4, 5}, asteroidCollision(new int[]{1, -2, 3, -4, 5}));
    assertArrayEquals(new int[]{-1, -3, -5}, asteroidCollision(new int[]{-1, 2, -3, 4, -5}));
    assertArrayEquals(new int[]{100}, asteroidCollision(new int[]{100, -1, 2, -3, 4, -5}));
    assertArrayEquals(new int[]{-1000}, asteroidCollision(new int[]{100, -1, 2, -3, 4, -5, -1000}));
    assertArrayEquals(new int[]{-2, -2}, asteroidCollision(new int[]{1, -1, -2, -2}));
  }
}
