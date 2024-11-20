package me.mourjo.y22;


/*
We are given S, a length n string of characters from the set {'D', 'I'}.
(These letters stand for "decreasing" and "increasing".)

A valid permutation is a permutation P[0], P[1], ..., P[n] of integers {0, 1, ..., n},
 such that for all i:  If S[i] == 'D', then P[i] > P[i+1], and; If S[i] == 'I',
  then P[i] < P[i+1].

How many valid permutations are there?
Since the answer may be large, return your answer modulo 10^9 + 7.


Example 1:  Input: "DID"
Output: 5

Explanation:
The 5 valid permutations of (0, 1, 2, 3) are:
(1, 0, 3, 2)
(2, 0, 3, 1)
(2, 1, 3, 0)
(3, 0, 2, 1)
(3, 1, 2, 0)

Note:  1 <= S.length <= 200 S consists only of characters from the set {'D', 'I'}.
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidPermulationsDISeq {
  public static int numPermsDISequence(String S) {


    return  -1;
  }

  public static void main(String[] args) {

    // D 0,1
    // 1, 0
    assertEquals(1, numPermsDISequence("D"));

    // DI   0 1 2
    // 1 0 2
    // 2 1 0
    assertEquals(2, numPermsDISequence("DI"));

    // D I D           0 1 2 3
    // 3 1 2 0
    // 3 0 2 1
    // 2 0 3 1
    // 2 1 3 0
    // 1 0 3 2

    assertEquals(5, numPermsDISequence("DID"));
  }
}
