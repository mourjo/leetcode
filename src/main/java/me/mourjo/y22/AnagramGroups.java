package me.mourjo.y22;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AnagramGroups {

    /**
     * https://leetcode.com/problems/group-anagrams/
     * <p>
     * Given an array of strings, group anagrams together.
     * <p>
     * Example:
     * <p>
     * Input: ["eat", "tea", "tan", "ate", "nat", "bat"], Output: [ ["ate","eat","tea"],
     * ["nat","tan"], ["bat"] ] Note:
     * <p>
     * All inputs will be in lowercase. The order of your output does not matter.
     */

    public static String genKey2(String s) {
        // counting sort
        int[] counts = new int[26];
        char chars[] = s.toCharArray();
        for (char a : chars) {
            counts[(a - 'a')]++;
        }

        int total = 0;
        for (int i = 0; i < counts.length; i++) {
            counts[i] += total;
            total = counts[i];
        }

        char res[] = new char[chars.length];
        for (char a : chars) {
            res[counts[(a - 'a')] - 1] = a;
            counts[(a - 'a')]--;
        }

        StringBuilder sb = new StringBuilder(chars.length);
        for (char c : res) {
            sb.append(c);
        }

        return sb.toString();
    }

    public static String genKey3(String s) {
        int[] counts = new int[26];
        char chars[] = s.toCharArray();
        for (char a : chars) {
            counts[(a - 'a')]++;
        }

        StringBuilder sb = new StringBuilder(chars.length);

        for (int c : counts) {
            sb.append(c);
            sb.append('_');
        }
        return sb.toString();
    }

    // 26 letters, use prime number for each
    static final int[] hash = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61,
        67, 71, 73, 79, 83, 89, 97, 101, 107};

    public static long genKey4(String s) {
        long result = 1;
        for (char c : s.toCharArray()) {
            result *= hash[c - 'a'];
        }
        return result;
    }

    public static int genKey(String s) {
        int[] counts = new int[26];
        char chars[] = s.toCharArray();
        for (char a : chars) {
            counts[(a - 'a')]++;
        }

        return Arrays.hashCode(counts);
    }

    public static List<List<String>> groupAnagrams(String[] strs) {

        Map<Integer, List<String>> table = new HashMap<>();
        for (String s : strs) {
            int key = genKey(s);
            if (table.containsKey(key)) {
                table.get(key).add(s);
            } else {
                List<String> list = new LinkedList<>();
                list.add(s);
                table.put(key, list);
            }
        }

        List<List<String>> result = new LinkedList<>();
        result.addAll(table.values());

        return result;
    }

    public static void main(String[] args) {
        System.out.println(genKey("abza"));
        System.out.println(genKey("ashgdfioausdofiausdhfoaisdufh"));

        System.out.println("Expected:\n[[\"ate\",\"eat\",\"tea\"],[\"nat\",\"tan\"],[\"bat\"]]");
        System.out.println(
            "Actual:\n" + groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));
        System.out.println();

        System.out.println("Expected:\n[[\"abcd\"],[\"bcde\"]]");
        System.out.println("Actual:\n" + groupAnagrams(new String[]{"abcd", "bcde"}));
        System.out.println();

        System.out.println("Expected:\n[[\"abcd\",\"abcd\",\"abcd\",\"abdc\",\"acdb\"]]");
        System.out.println(
            "Actual:\n" + groupAnagrams(new String[]{"abcd", "abcd", "abcd", "abdc", "acdb"}));
        System.out.println();
    }
}
