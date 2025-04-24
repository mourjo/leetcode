package me.mourjo.y24.arrays;

import org.junit.jupiter.api.Assertions;

import java.util.LinkedList;
import java.util.List;

/**
 * Design an algorithm to encode a list of strings to a string. The encoded string is then sent over
 * the network and is decoded back to the original list of strings.
 * <p>
 * Please implement encode and decode
 * <p>
 * Example Input: ["lint","code","love","you"] Output: ["lint","code","love","you"] Explanation: One
 * possible encode method is: "lint:;code:;love:;you"
 * <p>
 * Example Input: ["we", "say", ":", "yes"] Output: ["we", "say", ":", "yes"] Explanation: One
 * possible encode method is: "we:;say:;:::;yes"
 * <p>
 * https://neetcode.io/problems/string-encode-and-decode
 */
public class EncodeAndDecodeStrings {

    final char DELIM = ':';

    public static void main(String[] args) {
        var app = new EncodeAndDecodeStrings();
        app.verify(List.of("this", "is", "a sentence:", "with", "a", "colon"));
        app.verify(List.of("lint", "code", "love", "you"));
        app.verify(List.of("lint", "co:de", "love:", ":you", ":::", ":3:3:", ":", "8"));
        app.verify(List.of("lint", "", "love:", "you"));
        app.verify(List.of("lint", "code", "love", "love"));
        app.verify(List.of("lint"));
        app.verify(List.of());
    }

    void verify(List<String> input) {
        Assertions.assertEquals(input, decode(encode(input)));
    }

    String encode(List<String> arr) {

        // Gets [this, is, a sentence:, with, a, colon]
        // Produces 4:this2:is11:a sentence:4:with1:a5:colon
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s.length());
            sb.append(DELIM);
            sb.append(s);
        }
        return sb.toString();
    }

    List<String> decode(String str) {
        // Gets 4:this2:is11:a sentence:4:with1:a5:colon
        // Produces [this, is, a sentence:, with, a, colon]

        List<String> result = new LinkedList<>();

        for (int i = 0; i < str.length(); i++) {
            // reading number
            StringBuilder len = new StringBuilder();
            while (str.charAt(i) != DELIM) {
                len.append(str.charAt(i));
                i++;
            }
            int wordLen = Integer.parseInt(len.toString());

            // reading delimiter
            i++;

            // reading word
            StringBuilder word = new StringBuilder();
            for (int j = 0; j < wordLen; j++) {
                word.append(str.charAt(i + j));
            }
            i += wordLen - 1; // move pointer to the end of the current word
            result.add(word.toString());
        }

        return result;
    }



/*
    // this won't work because we don't know if the delimiter is not used in the rest
    // of the string. think when the character we are delimiting by appears in the middle of
    // the string

    String encode(List<String> arr) {
        StringBuilder sb = new StringBuilder();
        char d = 0;

        for (String s : arr) {
            for (char c : s.toCharArray()) {
                if (d == c) {
                    d++;
                }
            }
        }

        sb.append(d);

        for (String s : arr) {
            for (char c : s.toCharArray()) {
                sb.append(c);
            }
            sb.append(d);
        }
        return sb.toString();
    }

    List<String> decode(String str) {
        List<String> result = new LinkedList<>();
        StringBuilder current = new StringBuilder();
        char d = str.charAt(0);

        for (int i = 1; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == d) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(ch);
            }
        }
        return result;
    }
     */
}
