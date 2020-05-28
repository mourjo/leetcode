package me.mourjo;

public class DivideTwoIntegers {

    /** https://leetcode.com/problems/divide-two-integers/
     * Given two integers dividend and divisor, divide two integers without using multiplication,
     * division and mod operator.
     *
     * Return the quotient after dividing dividend by divisor.
     *
     * The integer division should truncate toward zero.
     *
     * Example 1:
     *
     * Input: dividend = 10, divisor = 3
     * Output: 3
     * Example 2:
     *
     * Input: dividend = 7, divisor = -3
     * Output: -2
     * Note:
     *
     * Both dividend and divisor will be 32-bit signed integers.
     * The divisor will never be 0.
     * Assume we are dealing with an environment which could only store integers within the 32-bit
     * signed integer range: [−231,  231 − 1]. For the purpose of this problem, assume that your
     * function returns 231 − 1 when the division result overflows.
     */
    public static long divideLong(long dividend, long divisor) {
        int sign = dividend < 0 ? (divisor < 0 ? 1 : -1) : (divisor < 0 ? -1 : 1);

        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);

        long multiple = divisor, factorCount = 1;

        if (dividend < divisor) return 0;

        while (multiple+multiple <= dividend) {
            multiple <<= 1; // left shift by 1 is multiply by 2
            factorCount += factorCount;
        }

        if (multiple == dividend)
            return (sign * factorCount);

        return sign * (factorCount + divideLong(dividend-multiple, divisor)) ;
    }

    public static int divide(int dividend, int divisor) {
        long result = divideLong(dividend, divisor);
        if (result < Integer.MIN_VALUE)
            return Integer.MAX_VALUE;
        if (result > Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        return (int) result;
    }

    public static void main(String[] args) {
        // -2147483648
        //-1
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE);
        Utilities.check(divide(-2147483648, -1), 2147483647);
        Utilities.check(divide(20,2), 10);
        Utilities.check(divide(21,2), 10);
        Utilities.check(divide(35,3), 11);

        Utilities.check(divide(-20,2), -10);
        Utilities.check(divide(21,-2), -10);
        Utilities.check(divide(-35,-3), 11);
    }
}
