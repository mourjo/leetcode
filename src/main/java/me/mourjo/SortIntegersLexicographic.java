package me.mourjo;

/*
https://leetcode.com/problems/lexicographical-numbers/

Given an integer n, return 1 - n in lexicographical order.

For example, given 13, return: [1,10,11,12,13,2,3,4,5,6,7,8,9].

Please optimize your algorithm to use less time and space. The input size may be as large as 5000000.
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SortIntegersLexicographic {

  public static List<Integer> lexicalOrder(int n) {
    List<Integer> list = new ArrayList<>(n);
    process(list, 1, 10, n);
    return list;
  }

  public static void process(List<Integer> result, int start, int end, int limit) {
    for (int i = start; i < end && i <= limit; i++) {
      result.add(i);
      if (i * 10 <= limit) {
        process(result, i * 10, (i + 1) * 10, limit);
      }
    }
  }

  public static void main(String[] args) {
    System.out.println(lexicalOrder(100));
    Random r = new Random();
    for (int n = 1; n < 500000; n += r.nextInt(10000)) {
      System.out.println(n);
      List<Integer> result = lexicalOrder(n);
      List<String> actual = result.stream().map(i -> Integer.toString(i))
          .collect(Collectors.toList());
      List<String> expected = IntStream.range(1, n + 1).mapToObj(Integer::toString).sorted()
          .collect(Collectors.toList());
      assertEquals(actual, expected);
    }
  }
}
