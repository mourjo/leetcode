package me.mourjo;

import java.util.HashMap;
import java.util.Map;

public class IntegerToRoman {

    /** https://leetcode.com/problems/integer-to-roman
     * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
     *
     * Symbol       Value
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * For example, two is written as II in Roman numeral, just two one's added together.
     * Twelve is written as, XII, which is simply X + II. The number twenty seven is written
     * as XXVII, which is XX + V + II.
     *
     * Roman numerals are usually written largest to smallest from left to right. However,
     * the numeral for four is not IIII. Instead, the number four is written as IV. Because
     * the one is before the five we subtract it making four. The same principle applies
     * to the number nine, which is written as IX. There are six instances where subtraction
     * is used:
     *
     * I can be placed before V (5) and X (10) to make 4 and 9.
     * X can be placed before L (50) and C (100) to make 40 and 90.
     * C can be placed before D (500) and M (1000) to make 400 and 900.
     * Given an integer, convert it to a roman numeral. Input is guaranteed to be within the
     * range from 1 to 3999.
     *
     * Example 1:
     *
     * Input: 3
     * Output: "III"
     * Example 2:
     *
     * Input: 4
     * Output: "IV"
     * Example 3:
     *
     * Input: 9
     * Output: "IX"
     * Example 4:
     *
     * Input: 58
     * Output: "LVIII"
     * Explanation: L = 50, V = 5, III = 3.
     * Example 5:
     *
     * Input: 1994
     * Output: "MCMXCIV"
     * Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
     */


    public static Map<Integer, Character> alpha = new HashMap<>();
    static {
        alpha.put(1,'I');
        alpha.put(5, 'V');
        alpha.put(10, 'X');
        alpha.put(50, 'L');
        alpha.put(100, 'C');
        alpha.put(500, 'D');
        alpha.put(1000, 'M');
    }

    public static void inferBlock(StringBuilder sb, int n, int lower, int upper) {

        if (n == lower) {
            sb.insert(0, alpha.get(n));
            return;
        }

        for (int f : alpha.keySet()) {
            if (upper == n + f) {
                sb.insert(0, alpha.get(upper));
                sb.insert(0, alpha.get(f));
                return;
            }
        }
        findNumeral(sb, n-lower);
        sb.insert(0, alpha.get(lower));
    }

    public static void findNumeral (StringBuilder sb, int n) {
        if (n == 0)
            return;

        else if (n >= 1000) {
            // we are filling up the string builder in reverse order
            sb.insert(0, "M");
            findNumeral(sb, n - 1000);
        }

        else if (n >= 500) {
            inferBlock(sb, n, 500, 1000);
        }

        else if (n >= 100) {
            inferBlock(sb, n, 100, 500);
        }

        else if (n >= 50) {
            inferBlock(sb, n, 50, 100);
        }

        else if (n >= 10) {
            inferBlock(sb, n, 10, 50);
        }

        else if (n >= 5) {
            inferBlock(sb, n, 5, 10);
        }

        else inferBlock(sb, n, 1, 5);
    }

    public static String intToRoman(int num) {

        StringBuilder result = new StringBuilder();

        int factor = 1;
        while (num > 0) {
            int d = num % 10;
            findNumeral(result,d*factor);
            num /= 10;
            factor *= 10;
        }

        return result.toString();
    }

    // taken from https://leetcode.com/problems/integer-to-roman/discuss/6274/Simple-Solution

    public static String intToRoman2(int num) {
        String M[] = {"", "M", "MM", "MMM"};
        String C[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String X[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String I[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return M[num/1000] + C[(num%1000)/100] + X[(num%100)/10] + I[num%10];
    }

    // taken from https://leetcode.com/problems/integer-to-roman/discuss/6310/My-java-solution-easy-to-understand
    public static String intToRoman3(int num) {
        int[] values = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] strs = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

        StringBuilder sb = new StringBuilder();

        for(int i=0;i<values.length;i++) {
            while(num >= values[i]) {
                num -= values[i];
                sb.append(strs[i]);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
//        for (int i=0; i < 100; i++)
//            System.out.println(i + " = " + intToRoman(i));
        Utilities.check(intToRoman(800), "DCCC");
        Utilities.check(intToRoman(24), "XXIV");
        Utilities.check(intToRoman(1994), "MCMXCIV");
    }
}
