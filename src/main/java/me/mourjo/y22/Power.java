package me.mourjo.y22;

public class Power {
    /**
     * https://leetcode.com/problems/powx-n/
     * Implement pow(x, n), which calculates x raised to the power n (xn).
     *
     * Example 1:
     *
     * Input: 2.00000, 10
     * Output: 1024.00000
     * Example 2:
     *
     * Input: 2.10000, 3
     * Output: 9.26100
     * Example 3:
     *
     * Input: 2.00000, -2
     * Output: 0.25000
     * Explanation: 2-2 = 1/22 = 1/4 = 0.25
     * Note:
     *
     * -100.0 < x < 100.0
     * n is a 32-bit signed integer, within the range [−2^31, 2^31 − 1]
     *
     */

    public static double helper (double result, double base, long remaining, long done) {
        if (done == remaining)
            return result;
        if (done + done <= remaining)
            return helper(result*result, base, remaining, done*2);
        return result * helper(base, base, remaining-done, 1);

    }
    public static double myPow2(double x, int n) {
        if (n == 0.0)
            return 1.0;
        if (n == 1.0)
            return x;

        long m = n < 0 ? -(long)n : (long)n;
        double y = helper(x, x, m, 1);
        if (n < 0)
            return 1/y;
        else return y;
    }

    public static double myPowHelper (double x, long k) {
        if (k == 1) return x;
        if (k == 0) return 1.0;

        if (k % 2 == 0)
            return myPowHelper(x * x, (int)k / 2);
        else
            return x * myPowHelper(x * x, (int) k / 2);
    }

    public static double myPow(double x, int n) {
        if (n < 0)
            return (1.0/myPowHelper(x, -((long) n)));
        return myPowHelper(x,n);
    }

    public static void main(String[] args) {
        Utilities.check(myPow(2,2), 4.0);
        Utilities.check(myPow(1,20), 1.0);
        Utilities.check(myPow(12,4), 20736.0);
        Utilities.check(myPow(12,5), 248832.0);
        Utilities.check(myPow(12,6), 2985984.0);
        Utilities.check(myPow(100,-2), 0.0001);
        Utilities.check(myPow(2,-2), 0.25);
        Utilities.check(myPow(0.44528,0), 1.0);
        System.out.println();
        System.out.println();
        System.out.println(myPow(2.1,3) - 9.261 < 0.000001);
        System.out.println(myPow(0.00001, 2147483647));
        System.out.println(myPow(0.00001, -214748367));
        System.out.println(myPow(1.0000, -2147483648));
        System.out.println(myPow(2.0000, -2147483648));
    }
}
