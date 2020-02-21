package me.mourjo;

public class OnesAndZeros {
    /**
     * https://leetcode.com/problems/ones-and-zeroes/
     * In the computer world, use restricted resource you have to generate maximum benefit is what
     * we always want to pursue.
     *
     * For now, suppose you are a dominator of m 0s and n 1s respectively. On the other hand,
     * there is an array with strings consisting of only 0s and 1s.
     *
     * Now your task is to find the maximum number of strings that you can form with given m 0s and n 1s.
     * Each 0 and 1 can be used at most once.
     *
     * Note:
     *
     * The given numbers of 0s and 1s will both not exceed 100
     * The size of given string array won't exceed 600.
     *
     *
     * Example 1:
     *
     * Input: Array = {"10", "0001", "111001", "1", "0"}, m = 5, n = 3
     * Output: 4
     *
     * Explanation: This are totally 4 strings can be formed by the using of 5 0s and 3 1s, which are
     * "10","0001","1","0"
     *
     *
     * Example 2:
     *
     * Input: Array = {"10", "0", "1"}, m = 1, n = 1
     * Output: 2
     *
     * Explanation: You could form "10", but then you'd have nothing left. Better form "0" and "1".
     */

    public static int[] countOnesZeroes(String s){
        int[] c = new int[2];
        for (char ch : s.toCharArray())
            c[(ch - '0')]++;
        return c;
    }

    public static int helper(String[] strs, int i, int zeroes, int ones) {
        System.out.println(i +" " + zeroes +" " + ones);
        if (i >= strs.length) return 0;


        String s = strs[i];
        int[] onesAndZeroes = countOnesZeroes(s);


        int withoutS = helper(strs, i+1, zeroes, ones);

        if (zeroes-onesAndZeroes[0] < 0 || ones-onesAndZeroes[1] < 0) {

            return withoutS;
        }

        int withS = 1 + helper(strs, i+1, zeroes-onesAndZeroes[0], ones-onesAndZeroes[1]);
        int f = Math.max(withoutS, withS);


        return f;
    }

    public static int findMaxForm(String[] strs, int zeroes, int ones) {

        int table[][] = new int[zeroes+1][ones+1];

        for (String s : strs) {
            int[] counts = countOnesZeroes(s);
            for (int i = zeroes; i >= counts[0]; i--) {
                for (int j = ones; j >= counts[1]; j--) {
                    table[i][j] = Math.max(table[i][j], 1+table[i-counts[0]][j-counts[1]]);
                }
            }
        }
        return table[zeroes][ones];

    }

    public static void main(String[] args) {
        Utilities.check(findMaxForm(new String[]{"10", "0001", "111001", "1", "0"}, 5,3), 4);
        Utilities.check(findMaxForm(new String[]{"10", "0", "1"}, 1,1), 2);
        Utilities.check(findMaxForm(new String[]{"10", "0", "1"}, 2,1), 2);
        Utilities.check(findMaxForm(new String[]{"10", "0", "0"}, 3,1), 3);
        Utilities.check(findMaxForm(new String[]{"100", "01", "11"}, 2,1), 1);
        Utilities.check(findMaxForm(new String[]{"11", "1", "1111"}, 1,0), 0);
        Utilities.check(findMaxForm(new String[]{"10", "0", "1"}, 0,0), 0);
        Utilities.check(findMaxForm(new String[]{}, 2,1), 0);
        Utilities.check(findMaxForm(new String[]{"101","100","11","10", "0", "1"}, 1000,4), 4);

        Utilities.check(
                findMaxForm(new String[]{"11","11","0","0","10","1","1","0","11","1","0","111","11111000","0","11","000","1","1","0","00","1","101","001","000","0","00","0011","0","10000"},
                        90,
                        66),
                29);
    }
}
