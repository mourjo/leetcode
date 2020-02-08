package me.mourjo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LetterCombinationsT9Typing {

    /**
     * Given a string containing digits from 2-9 inclusive, return all possible letter
     * combinations that the number could represent.
     *
     * A mapping of digit to letters (just like on the telephone buttons) is given below.
     * Note that 1 does not map to any letters.
     *
     * Example:
     *
     * Input: "23"
     * Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
     * Note:
     *
     * Although the above answer is in lexicographical order, your answer could be in any
     * order you want.
     */

    static final char[][] mat = new char[][]
            {{},
                    {'a','b','c'}, //2
                    {'d','e', 'f'}, // 3
                    {'g','h','i'}, // 4
                    {'j','k','l'}, // 5
                    {'m','n','o'}, //6
                    {'p','q','r','s'}, // 7
                    {'t','u','v'}, // 8
                    {'w','x','y','z'}}; //9

    static public List<String> letterCombinations(String digits) {
        int n = digits.length();
        LinkedList<String> combinations = new LinkedList<>();

        if (digits.length() == 0)
            return combinations;

        combinations.add("");

        for (int i = 0; i < n; i++) {
            char[] c = mat[digits.charAt(i) - '0' - 1];

            // linkedlist adds to the end in O(1) time and removes from
            // the front in O(1) time
            while (combinations.peek().length() == i) {
                String entry = combinations.remove();
                for (int k = 0; k < c.length; k++)
                    combinations.add(entry + c[k]);
            }
        }
        return combinations;
    }

    public static void main(String[] args) {
        System.out.println(letterCombinations("87"));
        System.out.println(letterCombinations("324"));
    }
}
