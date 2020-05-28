package me.mourjo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MultiplyStrings {
    /** https://leetcode.com/problems/multiply-strings/
     * Given two non-negative integers num1 and num2 represented as strings, return the product of
     * num1 and num2, also represented as a string.
     *
     * Example 1:
     *
     * Input: num1 = "2", num2 = "3"
     * Output: "6"
     * Example 2:
     *
     * Input: num1 = "123", num2 = "456"
     * Output: "56088"
     * Note:
     *
     * The length of both num1 and num2 is < 110.
     * Both num1 and num2 contain only digits 0-9.
     * Both num1 and num2 do not contain any leading zero, except the number 0 itself.
     * You must not use any built-in BigInteger library or convert the inputs to integer directly.
     */

    public static String multiplyFirstTry(String num1, String num2) {
        int x[] = new int[num1.length()];
        int y[] = new int[num2.length()];
        List<Integer> result = new ArrayList<>();

        for (int i=0; i < num1.length(); i++)
            x[i] = num1.charAt(num1.length()-1-i) - '0';

        for (int i=0; i < num2.length(); i++)
            y[i] = num2.charAt(num2.length()-1-i) - '0';

        int maxIdx = -1;

        for (int i=0; i < y.length; i++) {
            for (int j = 0; j < x.length; j++) {
                if (maxIdx < j+i) {
                    result.add(y[i] * x[j]);
                    maxIdx++;
                }
                else
                    result.set(j+i, result.get(j+i) + (y[i]*x[j]));
            }
        }
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        boolean zero = true;
        for (int elem : result) {
            int p = carry + elem;
            if (zero && (p%10 != 0)) zero = false;
            sb.append(p%10);
            carry = p/10;
        }
        while (carry != 0) {
            sb.append(carry % 10);
            if (zero && (carry%10 != 0)) zero = false;
            carry = carry/10;
        }
        if (zero) return "0";
        return sb.reverse().toString();
    }

    public static String multiply(String a, String b) {
        int[] res = new int [a.length() + b.length()];
        // input is reversed
        for (int i=a.length()-1; i >= 0; i--) {
            for (int j=b.length()-1; j >= 0; j--) {
                // sum will be at position i+j+1
                // carry will be at i+j
                // we are going in reverse direction so the result will be in the order of the string
                int raw = (a.charAt(i) - '0') * (b.charAt(j) - '0');
                int sum = raw + res[i+j+1];
                res[i+j+1] = sum%10;
                res[i+j] += sum/10;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < res.length; i++) {
            if (sb.length() == 0 && res[i] == 0)
                continue;
            else
                sb.append(res[i]);
        }
        if (sb.length() == 0) return "0";
        return sb.toString();
    }

    public static void main(String[] args) {
        Utilities.check(multiply("123456789", "0"), "0");
        Utilities.check(multiply("123456789", "723"), "89259258447");
        Utilities.check(multiply("123123123", "14"), "1723723722");
        Utilities.check(multiply("99999", "99999"), "9999800001");
        Utilities.check(multiply("2", "3"), "6");
        Utilities.check(multiply("12", "3"), "36");
        Utilities.check(multiply("10", "10"), "100");
        Utilities.check(multiply("12", "12"), "144");
        Utilities.check(multiply("123", "456"), "" + (123L * 456L));
        Utilities.check(multiply("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111", "1"),
                      "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        Utilities.check(multiply("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111", "0"),
                "0");

        System.out.println();
        multiply("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111",
                "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        System.out.println();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            int x = Math.abs(r.nextInt(100000));
            int y = Math.abs(r.nextInt(100000));
            System.out.println("Multiplying " + x + " and " + y);
            if (!Utilities.check(multiply(""+x, ""+y), ""+((long)x * (long)y)))
                break;

        }
    }
}
