package me.mourjo.y22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZigZagConversion {

    /**
     * https://leetcode.com/problems/zigzag-conversion/ The string "PAYPALISHIRING" is written in a
     * zigzag pattern on a given number of rows like this: (you may want to display this pattern in
     * a fixed font for better legibility)
     * <p>
     * P   A   H   N A P L S I I G Y   I   R And then read line by line: "PAHNAPLSIIGYIR"
     * <p>
     * Write the code that will take a string and make this conversion given a number of rows:
     * <p>
     * string convert(string s, int numRows); Example 1:
     * <p>
     * Input: s = "PAYPALISHIRING", numRows = 3 Output: "PAHNAPLSIIGYIR" Example 2:
     * <p>
     * Input: s = "PAYPALISHIRING", numRows = 4 Output: "PINALSIGYAHRPI" Explanation:
     * <p>
     * P     I    N A   L S  I G Y A   H R P     I
     */

    public static String convertOld(String s, int numRows) {

        if (numRows <= 1) {
            return s;
        }

        int lower = 0, d = -1, upper = numRows - 1, i = 0, j = 0;
        Map<Integer, List<Character>> buckets = new HashMap<>(numRows);

        for (int x = 0; x < numRows; x++) {
            buckets.put(x, new ArrayList<>(s.length() / numRows));
        }

        while (j < s.length()) {
            if (i == lower || i == upper) {
                d *= -1;
            }

            buckets.get(i).add(s.charAt(j));
            i += d;
            j++;
        }

        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < numRows; x++) {
            for (char c : buckets.get(x)) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static String convert(String s, int numRows) {

        if (numRows <= 1) {
            return s;
        }

        int lower = 0, d = -1, upper = numRows - 1, i = 0, j = 0;
        StringBuilder[] buckets = new StringBuilder[numRows];

        for (int x = 0; x < numRows; x++) {
            buckets[x] = new StringBuilder();
        }

        while (j < s.length()) {
            if (i == lower || i == upper) {
                d *= -1;
            }

            buckets[i].append(s.charAt(j));
            i += d;
            j++;
        }

        for (int x = 1; x < numRows; x++) {
            buckets[0].append(buckets[x]);
        }

        return buckets[0].toString();
    }

    public static void main(String[] args) {
        Utilities.check(convert("abcd", 1), "abcd");
        Utilities.check(convert("abcd", 2), "acbd");
        Utilities.check(convert("PAYPALISHIRING", 3), "PAHNAPLSIIGYIR");
        Utilities.check(convert("PAYPALISHIRING", 4), "PINALSIGYAHRPI");
    }
}
