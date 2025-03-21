package me.mourjo.y22;

/*
https://leetcode.com/problems/generate-random-point-in-a-circle/

Given the radius and x-y positions of the center of a circle, write a function randPoint which
generates a uniform random point in the circle.

Note:

input and output values are in floating-point.
radius and x-y position of the center of the circle is passed into the class constructor.
a point on the circumference of the circle is considered to be in the circle.
randPoint returns a size 2 array containing x-position and y-position of the random point, in that
order.
Example 1:

Input:
["Solution","randPoint","randPoint","randPoint"]
[[1,0,0],[],[],[]]
Output: [null,[-0.72939,-0.65505],[-0.78502,-0.28626],[-0.83119,-0.19803]]
Example 2:

Input:
["Solution","randPoint","randPoint","randPoint"]
[[10,5,-7.5],[],[],[]]
Output: [null,[11.52438,-8.33273],[2.46992,-16.21705],[11.13430,-12.42337]]
Explanation of Input Syntax:

The input is two lists: the subroutines called and their arguments. Solution's constructor has
three arguments, the radius, x-position of the center, and y-position of the center of the circle.
randPoint has no arguments. Arguments are always wrapped with a list, even if there aren't any.
 */

import java.util.concurrent.ThreadLocalRandom;

public class RandomPointInCircle {

    public static void main(String[] args) {
        Solution s = new Solution(1, 0, 0);
        double[] res = s.randPoint();
        System.out.println(res[0] + "," + res[1]);
        res = s.randPoint();
        System.out.println(res[0] + "," + res[1]);

        System.out.println("---");
        s = new Solution(10, 5, -7.5);
        res = s.randPoint();
        System.out.println(res[0] + "," + res[1]);
        res = s.randPoint();
        System.out.println(res[0] + "," + res[1]);

        System.out.println("---");

        s = new Solution(10, 5000, 7000);
        res = s.randPoint();
        System.out.println(res[0] + "," + res[1]);
        res = s.randPoint();
        System.out.println(res[0] + "," + res[1]);

        System.out.println("---");

        s = new Solution(0.01, -73839.1, -3289891.3);
        res = s.randPoint();
        System.out.println(res[0] + "," + res[1]);
        res = s.randPoint();
        System.out.println(res[0] + "," + res[1]);
        res = s.randPoint();
        System.out.println(res[0] + "," + res[1]);
        res = s.randPoint();
        System.out.println(res[0] + "," + res[1]);
        res = s.randPoint();
        System.out.println(res[0] + "," + res[1]);

    }

    static class Solution {

        double h, k, rad;
        ThreadLocalRandom r;

        public Solution(double radius, double x_center, double y_center) {
            h = x_center;
            k = y_center;
            rad = radius;
            r = ThreadLocalRandom.current();
        }

        public double[] randPoint() {
            double xpos, ypos;

            while (true) {
                if (r.nextBoolean()) {
                    xpos = h + r.nextDouble(0, rad);
                } else {
                    xpos = h - (r.nextDouble(0, rad));
                }

                if (r.nextBoolean()) {
                    ypos = k + r.nextDouble(0, rad);
                } else {
                    ypos = k - (r.nextDouble(0, rad));
                }

                if (((xpos - h) * (xpos - h) + ((ypos - k) * (ypos - k))) <= rad * rad) {
                    return new double[]{xpos, ypos};
                }
            }
        }
    }
}
