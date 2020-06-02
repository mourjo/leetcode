package me.mourjo;

/*
https://leetcode.com/problems/encode-number/

Given a non-negative integer num, Return its encoding string.

The encoding is done by converting the integer to a string using a secret function
that you should deduce from the following table:

0 -> ""
1 -> "0"
2 -> "1"
3 -> "00"
4 -> "01"
5 -> "10"
6 -> "11"
7 -> "000"

Example 1:
Input: num = 23
Output: "1000"

Example 2:
Input: num = 107
Output: "101100"

 */

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EncodeNumber {

  public static String encodeOld(int num) {
    if (num == 0) {
      return "";
    }
    if (num == 1) {
      return "0";
    }
    if (num == 2) {
      return "1";
    }

    int upper = 3, lower = 0, p = 0;
    while (upper <= num) {
      lower = upper;
      upper = upper * 2 + 1;
      p++;
    }
    int d = num - lower;
    return String.format("%" + (p + 1) + "s", Integer.toBinaryString(d)).replace(' ', '0');
  }

  public static String encode(int num) {
    StringBuilder sb = new StringBuilder(Integer.toBinaryString(num + 1));
    sb.deleteCharAt(0);
    return sb.toString();
  }

  public static void main(String[] args) {
    assertEquals("1000", encode(23));
    assertEquals("", encode(0));
    assertEquals("0", encode(1));
    assertEquals("1", encode(2));
    assertEquals("00", encode(3));
    assertEquals("01", encode(4));
    assertEquals("10", encode(5));
    assertEquals("11", encode(6));
    assertEquals("000", encode(7));
    assertEquals("101100", encode(107));
  }
}
