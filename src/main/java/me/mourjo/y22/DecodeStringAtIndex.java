package me.mourjo.y22;

public class DecodeStringAtIndex {

    /**
     * https://leetcode.com/problems/decoded-string-at-index/
     * <p>
     * An encoded string S is given.  To find and write the decoded string to a tape, the encoded
     * string is read one character at a time and the following steps are taken:
     * <p>
     * If the character read is a letter, that letter is written onto the tape. If the character
     * read is a digit (say d), the entire current tape is repeatedly written d-1 more times in
     * total. Now for some encoded string S, and an index K, find and return the K-th letter (1
     * indexed) in the decoded string.
     * <p>
     * Example 1: Input: S = "leet2code3", K = 10 Output: "o" Explanation: The decoded string is
     * "leetleetcodeleetleetcodeleetleetcode". The 10th letter in the string is "o".
     * <p>
     * Example 2: Input: S = "ha22", K = 5 Output: "h" Explanation: The decoded string is
     * "hahahaha".  The 5th letter is "h". Example 3:
     * <p>
     * Input: S = "a2345678999999999999999", K = 1 Output: "a" Explanation: The decoded string is
     * "a" repeated 8301530446056247680 times.  The 1st letter is "a".
     * <p>
     * Note: 2 <= S.length <= 100 S will only contain lowercase letters and digits 2 through 9. S
     * starts with a letter. 1 <= K <= 10^9 The decoded string is guaranteed to have less than 2^63
     * letters.
     */
    static public String decodeAtIndex(String s, int idx) {

        long len = 0, k = idx;
        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                len *= ch - '0';
            } else {
                len++;
            }
        }

        for (int i = s.length() - 1; len > 0 && i >= 0; i--) {
            k = k % len;
            if (k == 0 && Character.isLetter(s.charAt(i))) {
                return "" + s.charAt(i);
            }
            if (Character.isDigit(s.charAt(i))) {
                len /= s.charAt(i) - '0';
            } else {
                len--;
            }
        }

        return "";
    }

    public static void main(String[] args) {
        Utilities.check(decodeAtIndex("leet2code3", 10), "o");
        Utilities.check(decodeAtIndex("ha22", 5), "h");
        Utilities.check(decodeAtIndex("a2345678999999999999999", 1), "a");
    }


}

