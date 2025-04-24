package me.mourjo.y22;
/*
https://leetcode.com/problems/largest-number/

Given a list of non negative integers, arrange them such that they form the largest number.

Example 1:

Input: [10,2]
Output: "210"
Example 2:

Input: [3,30,34,5,9]
Output: "9534330"
Note: The result may be very large, so you need to return a string instead of an integer.
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LargestNumberFromGivenIntegers {

    public static String largestNumber(int[] nums) {
        if (nums.length == 0) {
            return "";
        }

        List<String> list = new ArrayList<>(nums.length);
        int max = Integer.MIN_VALUE;
        for (int n : nums) {
            if (n > max) {
                max = n;
            }
            list.add("" + n);
        }
        if (max == 0) {
            return "0";
        }

        Collections.sort(list, new Comparator<String>() {
            public int compare(String a, String b) {
                return (a + b).compareTo(b + a);
            }
        }.reversed());

        return String.join("", list);
    }

    public static void main(String[] args) {
        assertEquals("12121", largestNumber(new int[]{121, 12}));
        assertEquals("11000", largestNumber(new int[]{1, 0, 0, 0, 1}));
        assertEquals("0", largestNumber(new int[]{0, 0, 0}));
        assertEquals("9534330", largestNumber(new int[]{3, 30, 34, 5, 9}));
        assertEquals("8888888808800", largestNumber(new int[]{8, 88, 808, 800, 8888}));
        assertEquals("4321", largestNumber(new int[]{1, 2, 3, 4}));
        assertEquals("99998790990012", largestNumber(new int[]{9, 99, 987, 909, 900, 12}));
        assertEquals("99998790990080012", largestNumber(new int[]{9, 99, 987, 909, 900, 12, 800}));
        assertEquals("99999888", largestNumber(new int[]{9, 9, 99, 8, 988}));
        assertEquals("999007900078", largestNumber(new int[]{9, 9, 90007, 8, 9007}));
        assertEquals("210", largestNumber(new int[]{10, 2}));

        assertEquals("9609938824824769735703560743981399",
                largestNumber(new int[]{824, 938, 1399, 5607, 6973, 5703, 9609, 4398, 8247}));

    }
}
