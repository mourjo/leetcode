package me.mourjo.y22;

/*
https://leetcode.com/problems/random-pick-with-blacklist/

Given a blacklist B containing unique integers from [0, N), write a function to
return a uniform random integer from [0, N) which is NOT in B.

Optimize it such that it minimizes the call to system’s Math.random().

Note:
1 <= N <= 1000000000
0 <= B.length < min(100000, N)
[0, N) does NOT include N. See interval notation.


|---------+-----------------------------------+--------------|
| Example | Input                             | Output       |
|---------+-----------------------------------+--------------|
|       1 | ["Solution","pick","pick","pick"] | [null,0,0,0] |
|         | [[1,[]],[],[],[]]                 |              |
|---------+-----------------------------------+--------------|
|       2 | ["Solution","pick","pick","pick"] | [null,1,1,1] |
|         | [[2,[]],[],[],[]]                 |              |
|---------+-----------------------------------+--------------|
|       3 | ["Solution","pick","pick","pick"] | [null,0,0,2] |
|         | [[3,[1]],[],[],[]]                |              |
|---------+-----------------------------------+--------------|
|       4 | ["Solution","pick","pick","pick"] | [null,1,3,1] |
|         | [[4,[2]],[],[],[]]                |              |
|---------+-----------------------------------+--------------|


Explanation of Input Syntax:

The input is two lists: the subroutines called and their arguments. Solution's constructor has
two arguments, N and the blacklist B. pick has no arguments. Arguments are always wrapped with
a list, even if there aren't any.
Solution obj = new Solution(N, blacklist);
int p1 = obj.pick();
 */

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

class Solution {

  Random rand;
  int actualUpper;
  Map<Integer, Integer> table;

  public Solution(int N, int[] blacklist) {
    rand = new Random();
    actualUpper = N - blacklist.length;
    table = new HashMap<Integer, Integer>();
    Set<Integer> blackSet = new HashSet<>();
    Set<Integer> goodBeyond = new HashSet<>();

    for (int item : blacklist) {
      blackSet.add(item);
    }

    for (int i = actualUpper; i < N; i++) {
      if (!blackSet.contains(i)) {
        goodBeyond.add(i);
      }
    }

    Iterator<Integer> goodBeyondIterator = goodBeyond.iterator();
    for (int item : blackSet) {
      if (item < actualUpper) {
        table.put(item, goodBeyondIterator.next());
      }
    }
  }

  public int pick() {
    int i = rand.nextInt(actualUpper);
    return table.getOrDefault(i, i);
  }
}


public class RandomPickWithBlacklist {

  public static void main(String[] args) {
    Solution obj;
    obj = new Solution(4, new int[]{2});
    for (int i = 0; i < 100; i++) {
      int p = obj.pick();
      assertTrue(p != 2 && p >= 0 && p < 4);
    }

    obj = new Solution(4, new int[]{1, 2, 3});
    for (int i = 0; i < 100; i++) {
      int p = obj.pick();
      assertTrue(p == 0);
    }

    obj = new Solution(4, new int[]{0, 2, 3});
    for (int i = 0; i < 100; i++) {
      int p = obj.pick();
      assertTrue(p == 1);
    }

    obj = new Solution(4, new int[]{});
    for (int i = 0; i < 100; i++) {
      int p = obj.pick();
      assertTrue(p >= 0 && p < 4);
    }

    obj = new Solution(4, new int[]{2, 3});
    for (int i = 0; i < 100; i++) {
      int p = obj.pick();
      assertTrue(p == 0 || p == 1);
    }

    obj = new Solution(1000000000, new int[]{640145908});
    for (int i = 0; i < 100000000; i++) {
      int p = obj.pick();
      assertTrue(p != 640145908 && p >= 0 && p < 1000000000);
    }
  }
}
