package me.mourjo.y24.arrays;

import org.junit.jupiter.api.Assertions;

public class ValidAnagram {

    /*
    Input: s = "anagram", t = "nagaram"
    Output: true

    Constraints:

1 <= s.length, t.length <= 5 * 104
s and t consist of lowercase English letters.
     */
    public static void main(String[] args) {
        var app = new ValidAnagram();
        Assertions.assertTrue(app.isAnagram("anagram", "anagram"));
        Assertions.assertTrue(app.isAnagram("anagram", "naagrma"));
        Assertions.assertFalse(app.isAnagram("anagram", "naaa"));
        Assertions.assertTrue(app.isAnagram("anagramz", "anagramz"));
        Assertions.assertTrue(app.isAnagram("anagramz", "anzagram"));
    }

    public boolean isAnagram(String s, String t) {
        int[] frequencies = new int[27];

        for (char c : s.toCharArray()) {
            frequencies[c-'a']++;
        }

        for (char c : t.toCharArray()) {
            frequencies[c-'a']--;
        }

        for (int c : frequencies) {
            if (c != 0) {
                return false;
            }
        }

        return true;
    }

}
