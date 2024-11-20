package me.mourjo.y22;
/*
https://leetcode.com/problems/random-pick-with-weight/

Given an array w of positive integers, where w[i] describes the weight of index i(0-indexed),
write a function pickIndex which randomly picks an index in proportion to its weight.

For example, given an input list of values w = [2, 8], when we pick up a number out of it, the
chance is that 8 times out of 10 we should pick the number 1 as the answer since it's the second
element of the array (w[1] = 8).

Example 1:
Input
["Solution","pickIndex"]
[[[1]],[]]
Output [null,0]

Explanation
Solution solution = new Solution([1]);
solution.pickIndex(); // return 0. Since there is only one single element on the array the
only option is to return the first element.

Example 2:
Input
["Solution","pickIndex","pickIndex","pickIndex","pickIndex","pickIndex"]
[[[1,3]],[],[],[],[],[]]
Output
[null,1,1,1,1,0]

Explanation
Solution solution = new Solution([1, 3]);
solution.pickIndex(); // return 1. It's returning the second element (index = 1) that has
probability of 3/4.
solution.pickIndex(); // return 1
solution.pickIndex(); // return 1
solution.pickIndex(); // return 1
solution.pickIndex(); // return 0. It's returning the first element (index = 0) that has
probability of 1/4.

Since this is a randomization problem, multiple answers are allowed so the following outputs
can be considered correct :
[null,1,1,1,1,0]
[null,1,1,1,1,1]
[null,1,1,1,0,0]
[null,1,1,1,0,1]
[null,1,0,1,0,0]
......
and so on.

Constraints:
1 <= w.length <= 10000
1 <= w[i] <= 10^5
pickIndex will be called at most 10000 times.
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

public class RandomPickWithWeight {

  public static void main(String[] args) {
    Solution obj = new Solution(new int[]{2, 8});
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < 10000; i++) {
      res.append(obj.pickIndex()).append(",");
    }
    System.out.println(frequencies(res.toString()));

    obj = new Solution(new int[]{40, 1});
    res = new StringBuilder();
    for (int i = 0; i < 10000; i++) {
      res.append(obj.pickIndex()).append(",");
    }
    System.out.println(frequencies(res.toString()));

    obj = new Solution(new int[]{3, 3, 3});
    res = new StringBuilder();
    for (int i = 0; i < 10000; i++) {
      res.append(obj.pickIndex()).append(",");
    }
    System.out.println(frequencies(res.toString()));

    obj = new Solution(new int[]{100, 1, 1, 1, 1, 1, 1, 10});
    res = new StringBuilder();
    for (int i = 0; i < 10000; i++) {
      res.append(obj.pickIndex()).append(",");
    }
    System.out.println(frequencies(res.toString()));

    obj = new Solution(new int[]{188, 927, 949, 95, 151, 659, 405, 906, 481, 363, 728, 839});
    res = new StringBuilder();
    for (int i = 0; i < 6691 * 100; i++) {
      res.append(obj.pickIndex()).append(",");
    }
    System.out.println(frequencies(res.toString()));

  }

  public static Map<String, Integer> frequencies(String s) {
    Map<String, Integer> m = new TreeMap<>();
    for (String c : s.split(",")) {
      if (!c.isEmpty()) {
        m.putIfAbsent(c, 0);
        m.compute(c, (k, v) -> v + 1);
      }
    }
    return m;
  }

  static class SolutionAlt1 {

    int[] reset;
    Map<Integer, Integer> current;
    Random r;

    public SolutionAlt1(int[] w) {
      reset = w;
      current = new HashMap<>(w.length);
      for (int i = 0; i < w.length; i++) {
        current.put(i, w[i]);
      }
      r = new Random();
    }

    public int pickIndex() {
      if (current.isEmpty()) {
        for (int i = 0; i < reset.length; i++) {
          current.put(i, reset[i]);
        }
      }

      int res = 0;
      Iterator<Integer> it = current.keySet().iterator();
      for (int i = r.nextInt(current.size()); i >= 0; i--) {
        res = it.next();
      }

      current.compute(res, (key, oldV) -> oldV - 1);
      if (current.get(res) == 0) {
        current.remove(res);
      }

      return res;
    }
  }

  static class SolutionAlt2 {
    TreeSet<Integer> sums;
    HashMap<Integer, Integer> lookup;
    int total;
    Random r;

    public SolutionAlt2(int[] w) {
      int t = 0;
      sums = new TreeSet<>();
      lookup = new HashMap<>();
      for (int i = 0; i < w.length; i++) {
        t += w[i];
        sums.add(t);
        lookup.put(t, i);
      }
      total = t;
      r = new Random();
    }

    public int pickIndex() {
      int needle = r.nextInt(total) + 1;
      return lookup.get(sums.ceiling(needle));
    }
  }

  static class Solution {
    int[] sums;
    int total;
    Random r;

    public Solution(int[] w) {
      int t = 0;
      sums = new int[w.length];
      for (int i = 0; i < w.length; i++) {
        t += w[i];
        sums[i] = t;
      }
      total = t;
      r = new Random();
    }

    public int pickIndex() {
      int needle = r.nextInt(total) + 1;
      int low = 0, high = sums.length - 1;

      while (low < high) {
        int mid = low + (high - low) / 2;
        if (needle > sums[mid]) {
          low = mid + 1;
        } else {
          high = mid;
        }
      }
      return high;
    }
  }
}
