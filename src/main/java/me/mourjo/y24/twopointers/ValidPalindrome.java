package me.mourjo.y24.twopointers;

import org.junit.jupiter.api.Assertions;

/* A phrase is a palindrome if, after converting all uppercase letters into lowercase letters and
removing all non-alphanumeric characters, it reads the same forward and backward. Alphanumeric
characters include letters and numbers.

Given a string s, return true if it is a palindrome, or false otherwise.

Example 1:
Input: s = "A man, a plan, a canal: Panama"
Output: true
Explanation: "amanaplanacanalpanama" is a palindrome.

Example 2:
Input: s = "race a car"
Output: false
Explanation: "raceacar" is not a palindrome.

Example 3:
Input: s = " "
Output: true
Explanation: s is an empty string "" after removing non-alphanumeric characters.
Since an empty string reads the same forward and backward, it is a palindrome.

https://leetcode.com/problems/valid-palindrome/description/
 */
public class ValidPalindrome {

    public static void main(String[] args) {
        var app = new ValidPalindrome();

        Assertions.assertTrue(app.isPalindrome("A man, a plan, a canal: Panama"));
        Assertions.assertFalse(app.isPalindrome("race a car"));
        Assertions.assertTrue(app.isPalindrome("  "));
        Assertions.assertTrue(app.isPalindrome("madam"));
        Assertions.assertFalse(app.isPalindrome("the madam"));
    }

    public boolean isPalindrome(String s) {
        int p1 = 0, p2 = s.length() - 1;
        while (p1 < p2) {
            char ch1 = s.charAt(p1);
            char ch2 = s.charAt(p2);


            if (isAscii(ch1) && isAscii(ch2)) {
                if(lower(ch1) != lower(ch2)) {
                    return false;
                }

                p1++;
                p2--;
            } else if (!isAscii(ch1)) {
                p1++;
            } else if (!isAscii(ch2)) {
                p2--;
            } else {
                p1++;
                p2--;
            }
        }
        return true;
    }

    public char lower(char c) {
        if (c >= 'A' && c <= 'Z') {
            return Character.toLowerCase(c);
        }
        return c;
    }

    public boolean isAscii(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9');
    }
}
