package me.mourjo.y24.arrays;

/*
https://leetcode.com/problems/group-anagrams/description/
Given an array of strings strs, group the
anagrams together. You can return the answer in any order.
Example 1:

Input: strs = ["eat","tea","tan","ate","nat","bat"]

Output: [["bat"],["nat","tan"],["ate","eat","tea"]]

Explanation:

There is no string in strs that can be rearranged to form "bat".
The strings "nat" and "tan" are anagrams as they can be rearranged to form each other.
The strings "ate", "eat", and "tea" are anagrams as they can be rearranged to form each other.
Example 2:

Input: strs = [""]

Output: [[""]]

Example 3:

Input: strs = ["a"]

Output: [["a"]]

Constraints:

1 <= strs.length <= 104
0 <= strs[i].length <= 100
strs[i] consists of lowercase English letters.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Assertions;

public class GroupAnagrams {

    public static void main(String[] args) {
        var app = new GroupAnagrams();
        System.out.println(app.groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"}));
        System.out.println(app.groupAnagrams(new String[]{"a"}));
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> anagramMap = new HashMap<>();
        for (String word : strs) {
            var charArray = word.toCharArray();
            Arrays.sort(charArray);
            var serializedStr = new String(charArray);

            anagramMap.putIfAbsent(serializedStr, new ArrayList<>());
            anagramMap.get(serializedStr).add(word);
        }

        var list = new ArrayList<List<String>>();

        for(String s : anagramMap.keySet()){
            List<String> candidates = anagramMap.get(s);
            list.add(candidates);
        }
        return list;
    }

}
