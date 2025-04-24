package me.mourjo.y22;
/*
https://leetcode.com/problems/search-suggestions-system/

Given an array of strings products and a string `searchWord`. We want to design a system
that suggests at most three product names from products after each character of searchWord
is typed. Suggested products should have common prefix with the searchWord. If there are more
than three products with a common prefix return the three lexicographically minimums products.

Return list of lists of the suggested products after each character of searchWord is typed.



Example 1:
Input: products = ["mobile","mouse","moneypot","monitor","mousepad"], searchWord = "mouse"
Output: [
      ["mobile","moneypot","monitor"],
      ["mobile","moneypot","monitor"],
      ["mouse","mousepad"],
      ["mouse","mousepad"],
      ["mouse","mousepad"]
]

Explanation: products sorted lexicographically = ["mobile","moneypot","monitor","mouse","mousepad"]
After typing m and mo all products match and we show user ["mobile","moneypot","monitor"]
After typing mou, mous and mouse the system suggests ["mouse","mousepad"]

Example 2:
Input: products = ["havana"], searchWord = "havana"
Output: [["havana"],["havana"],["havana"],["havana"],["havana"],["havana"]]

Example 3:
Input: products = ["bags","baggage","banner","box","cloths"], searchWord = "bags"
Output: [["baggage","bags","banner"],["baggage","bags","banner"],["baggage","bags"],["bags"]]

Example 4:
Input: products = ["havana"], searchWord = "tatiana"
Output: [[],[],[],[],[],[],[]]


Constraints:

1 <= products.length <= 1000
There are no repeated elements in products.
1 <= Σ products[i].length <= 2 * 10^4
All characters of products[i] are lower-case English letters.
1 <= searchWord.length <= 1000
All characters of searchWord are lower-case English letters.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchSuggestionsSystem {

    public static List<List<String>> suggestedProducts(String[] products, String searchWord) {
        TreeMap<String, Integer> tree = new TreeMap<>();
        for (String p : products) {
            tree.put(p, tree.getOrDefault(p, 0) + 1);
        }

        List<String> currentMatches, prevMatches = null;
        List<List<String>> res = new ArrayList<>();

        for (int i = 0; i < searchWord.length(); i++) {
            String prefix = searchWord.substring(0, i + 1);

            // caching
            if (prevMatches != null) {
                List<String> stillValidPrevs = prevMatches
                        .stream()
                        .filter(x -> x.startsWith(prefix))
                        .collect(Collectors.toList());

                if (prevMatches.size() < 3 || (prevMatches.size() == 3
                        && stillValidPrevs.size() == 3)) {
                    res.add(stillValidPrevs);
                    prevMatches = stillValidPrevs;
                    continue;
                }
            }

            currentMatches = new ArrayList<>();
            Entry<String, Integer> candidate = tree.ceilingEntry(prefix);

            while (currentMatches.size() < 3) {
                if (candidate == null || !candidate.getKey().startsWith(prefix)) {
                    break;
                }
                int occ = candidate.getValue();
                for (int j = 0; j < 3 && j < occ && currentMatches.size() < 3; j++) {
                    currentMatches.add(candidate.getKey());
                }
                candidate = tree.higherEntry(candidate.getKey());
            }
            res.add(currentMatches);
            prevMatches = currentMatches;
        }

        while (res.size() < searchWord.length()) {
            res.add(new ArrayList<>());
        }

        return res;
    }

    public static void main(String[] args) {
        assertEquals(Arrays.asList(
                        Arrays.asList("mobile", "moneypot", "monitor"),
                        Arrays.asList("mobile", "moneypot", "monitor"),
                        Arrays.asList("mouse", "mousepad"),
                        Arrays.asList("mouse", "mousepad"),
                        Arrays.asList("mouse", "mousepad")),
                suggestedProducts(new String[]{"mobile", "mouse", "moneypot", "monitor", "mousepad"},
                        "mouse"));

        assertEquals(Arrays.asList(
                        List.of("havana"),
                        List.of("havana"),
                        List.of("havana"),
                        List.of("havana"),
                        List.of("havana"),
                        List.of("havana")),
                suggestedProducts(new String[]{"havana"},
                        "havana"));

        assertEquals(Arrays.asList(
                        Arrays.asList("baggage", "bags", "banner"),
                        Arrays.asList("baggage", "bags", "banner"),
                        Arrays.asList("baggage", "bags"),
                        List.of("bags")),
                suggestedProducts(new String[]{"bags", "baggage", "banner", "box", "cloths"},
                        "bags"));
    }
}
