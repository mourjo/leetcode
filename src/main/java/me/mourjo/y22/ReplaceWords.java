package me.mourjo.y22;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

/*
  In English, we have a concept called root, which can be followed by
  some other words to form another longer word - let's call this word successor.
  For example, the root an, followed by other, which can form another word another.

Now, given a dictionary consisting of many roots and a sentence. You need to replace
 all the successor in the sentence with the root forming it. If a successor has many
 roots can form it, replace it with the root with the shortest length.

You need to output the sentence after the replacement.
https://leetcode.com/problems/replace-words/



Example 1:

Input: dict = ["cat","bat","rat"], sentence = "the cattle was rattled by the battery"
Output: "the cat was rat by the bat"

   */

class Trie {

    boolean leaf;
    char c;
    List<Trie> next;

    Trie() {
        leaf = false;
        c = '*';
        next = new ArrayList<>();
    }

    Trie(char data) {
        leaf = false;
        c = data;
        next = new ArrayList<>();
    }

    void insert(char[] a, int offset) {
        if (offset >= a.length) {
            leaf = true;
            next = new ArrayList<>();
            return;
        }
        Optional<Trie> nxt = next.stream().filter(x -> x.c == a[offset]).findAny();
        if (nxt.isPresent()) {
            if (nxt.get().leaf) {
                return;
            }
            nxt.get().insert(a, offset + 1);
        } else {
            Trie root = new Trie(a[offset]);
            this.next.add(root);
            root.insert(a, offset + 1);
        }
    }

    void insert(char[] a) {
        insert(a, 0);
    }

    void insert(String a) {
        insert(a.toCharArray(), 0);
    }

    public String toString() {

        String s = "";
        s += c + (leaf ? "(leaf)" : "") + " -> {";
        s += next.stream().map(Trie::toString).collect(Collectors.joining(", "));
        s += "}";
        return s;
    }

    boolean contains(char[] s, int offset) {

        if (offset >= s.length) {
            return false;
        }

        if (c != s[offset] && c != '*') {
            return false;
        }

        if (offset == s.length - 1 & c != '*') {
            return leaf;
        }

        int n = c == '*' ? offset : offset + 1;

        return next.stream().anyMatch(x -> x.contains(s, n));
    }

    boolean contains(String s) {
        return contains(s.toCharArray(), 0);
    }

    int size() {
        int s = c == '*' ? 0 : 1;
        for (Trie t : next) {
            s += t.size();
        }
        return s;
    }
}

class Trie2 {

    boolean leaf;
    char c;
    Trie2[] next;

    Trie2() {
        leaf = false;
        c = '*';
        next = new Trie2[26];
    }

    Trie2(char data) {
        leaf = false;
        c = data;
        next = new Trie2[26];
    }

    void insert(char[] a, int offset) {
        if (offset >= a.length) {
            leaf = true;
            next = null;
            return;
        }

        Trie2 s = next[a[offset] - 'a'];
        if (s != null) {
            if (s.leaf) {
                return;
            }
            s.insert(a, offset + 1);
        } else {
            Trie2 root = new Trie2(a[offset]);
            this.next[a[offset] - 'a'] = root;
            root.insert(a, offset + 1);
        }
    }

    void insert(char[] a) {
        insert(a, 0);
    }

    void insert(String a) {
        insert(a.toCharArray(), 0);
    }

    boolean contains(char[] s, int offset) {

        if (offset >= s.length) {
            return false;
        }

        if (c != s[offset] && c != '*') {
            return false;
        }

        if (offset == s.length - 1 & c != '*') {
            return leaf;
        }

        int n = c == '*' ? offset : offset + 1;

        if (next != null) {
            for (Trie2 t : next) {
                if (t != null) {
                    if (t.contains(s, n)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean contains(String s) {
        return contains(s.toCharArray(), 0);
    }

    boolean contains(char a[]) {
        return contains(a, 0);
    }
}

public class ReplaceWords {


    public static String replaceWords(List<String> dict, String sentence) {
        Trie2 t = new Trie2();
        for (String w : dict) {
            t.insert(w);
        }

        String currentWord = "";
        String finalSentence = "";

        boolean matched = false;
        for (char c : sentence.toCharArray()) {
            if (c == ' ') {
                finalSentence += currentWord;
                finalSentence += ' ';
                currentWord = "";
                matched = false;
            } else {
                if (matched) {
                    continue;
                }
                if (t.contains(currentWord)) {
                    matched = true;
                } else {
                    currentWord += c;
                }
            }
        }

        return finalSentence + currentWord;
    }

    @Test
    public static void main(String[] args) {
        Trie2 t = new Trie2();
        t.insert("abcd");
        t.insert("abef");
        t.insert("abe");
        t.insert("abcde");
        // assertEquals(5, t.size());
        assertTrue(t.contains("abcd"));
        assertTrue(t.contains("abe"));
        assertFalse(t.contains("a"));
        assertFalse(t.contains("b"));
        assertFalse(t.contains("abcdefghijkl"));
        assertFalse(t.contains("yqiuwethieht83q74659834t"));

        t = new Trie2();
        t.insert("cat");
        t.insert("bat");
        t.insert("rat");
        //assertEquals(9, t.size());
        assertTrue(t.contains("bat"));
        assertTrue(t.contains("rat"));
        assertTrue(t.contains("cat"));
        assertFalse(t.contains("mat"));
        assertFalse(t.contains("r"));
        assertFalse(t.contains("rats"));

        t = new Trie2();
        t.insert("this");
        t.insert("those");
        t.insert("i");
        t.insert("is");
        t.insert("isa");
        //assertEquals(8, t.size());
        assertTrue(t.contains("i"));

        t = new Trie2();
        t.insert("this");
        t.insert("those");
        t.insert("isa");
        t.insert("i");
        t.insert("is");
        //assertEquals(8, t.size());
        assertTrue(t.contains("i"));

        t = new Trie2();
        t.insert("abc");
        t.insert("abcdefghi");
        t.insert("abcdefghi");
        assertTrue(t.contains("abc"));
        assertFalse(t.contains("abcd"));
        //assertEquals(3, t.size());

        t = new Trie2();
        t.insert("abcdefghi");
        t.insert("abcdefghi");
        t.insert("abc");
        assertTrue(t.contains("abc"));
        assertFalse(t.contains("abcd"));
        //assertEquals(3, t.size());

        assertEquals("the cat was rat by the bat",
            replaceWords(Arrays.asList("cat", "bat", "rat"),
                "the cattle was rattled by the battery"));

        assertEquals("",
            replaceWords(Arrays.asList("cat", "bat", "rat"),
                ""));

        assertEquals("this is it",
            replaceWords(Arrays.asList("cat", "bat", "rat"),
                "this is it"));

        assertEquals("this is it",
            replaceWords(Arrays.asList(),
                "this is it"));

        assertEquals("this is it",
            replaceWords(Arrays.asList("is"),
                "this is it"));

        assertEquals("th is it",
            replaceWords(Arrays.asList("th"),
                "this is it"));

        assertEquals("this is it",
            replaceWords(Arrays.asList("abcdefghijklmnopqrst", "isa"),
                "this is it"));

        assertEquals("this i i",
            replaceWords(Arrays.asList("this", "is", "it", "i"),
                "this is it"));

        assertEquals("th th th th it",
            replaceWords(
                Arrays.asList("th", "theeeeeeeeeee", "thiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"),
                "this theeeeeeeeeee thiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii thiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii it"));
    }

}
