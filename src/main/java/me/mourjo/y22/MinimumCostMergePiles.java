package me.mourjo.y22;

/*
https://leetcode.com/problems/minimum-cost-to-merge-stones/

There are N piles of stones arranged in a row.  The i-th pile has stones[i] stones.

A move consists of merging exactly K consecutive piles into one pile, and the cost of this move is
equal to the total number of stones in these K piles.

Find the minimum cost to merge all piles of stones into one pile.  If it is impossible, return -1.

Example 1:
Input: stones = [3,2,4,1], K = 2
Output: 20
Explanation:
We start with [3, 2, 4, 1].
We merge [3, 2] for a cost of 5, and we are left with [5, 4, 1].
We merge [4, 1] for a cost of 5, and we are left with [5, 5].
We merge [5, 5] for a cost of 10, and we are left with [10].
The total cost was 20, and this is the minimum possible.

Example 2:
Input: stones = [3,2,4,1], K = 3
Output: -1
Explanation: After any merge operation, there are 2 piles left, and we can't merge anymore.
So the task is impossible.

Example 3:
Input: stones = [3,5,1,2,6], K = 3
Output: 25
Explanation:
We start with [3, 5, 1, 2, 6].
We merge [5, 1, 2] for a cost of 8, and we are left with [3, 8, 6].
We merge [3, 8, 6] for a cost of 17, and we are left with [17].
The total cost was 25, and this is the minimum possible.

Note:
1 <= stones.length <= 30
2 <= K <= 30
1 <= stones[i] <= 100
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;

public class MinimumCostMergePiles {
  public static int mergeStones(int[] stones, int k) {
    if (stones.length < k ) return 0;
    if (stones.length == k) return Arrays.stream(stones).sum();
    for (int i = stones.length; i != k; i=i-(k-1)) {
      if (i < k) return -1;
    }


    int cost = 0;
    LinkedList<Integer> list = new LinkedList<>();
    for(int s : stones) list.add(s);

    while (list.size()>k) {
      System.out.println(list);
      int minpile = Integer.MAX_VALUE, running = 0, minpileIdx = -1;
      for (int i = 0; i < k - 1; i++)
        running += list.get(i);

      for (int i = 0; i <= list.size() - k; i++) {

        running += list.get(i + k - 1);
        if (running < minpile) {
          minpile = running;
          minpileIdx = i;
        }
        running -= list.get(i);
      }
      System.out.println(minpileIdx);
      cost += minpile;
      list.set(minpileIdx, minpile);
      for (int i = minpileIdx+k-1 ; i >= minpileIdx+1; i--)
        list.remove(i);
    }
     System.out.println(list);
    return cost + list.stream().reduce(0, (a, b) -> a+b);
  }


  public static void main(String[] args) {
    assertEquals(40, mergeStones(new int[]{6,4,4,6}, 2));
    assertEquals(0, mergeStones(new int[]{1}, 2));
    assertEquals(25, mergeStones(new int[]{3,5,1,2,6}, 3));
    assertEquals(20, mergeStones(new int[]{3,2,4,1}, 2));





    System.out.println("---");


    assertEquals(-1, mergeStones(new int[]{1,1,30,2,40,200,20,40}, 4));

    // (1,1,30,2),40,200,20,40
    // 34,40,200,20,40
    //
    assertEquals(-1, mergeStones(new int[]{1,1,30,2,40,200,20,40}, 4));
    assertEquals(-1, mergeStones(new int[]{3,2,4,1}, 3));

    assertEquals(-1, mergeStones(new int[]{1,1,30,2,40,200,20,40,1}, 4));



    // 1,1,30,2,40,200,20,40,1    =>
    assertEquals(409, mergeStones(new int[]{1,1,30,2,40,200,20,40,1}, 5));


    // 5,2,300,4,1,(1,1,1),3 => 3
    // 5,2,300,4,(1,3,3)     => 7
    // (5,2,300),4,7         => 307
    // (307,4,7)             => 318
    assertEquals(634, mergeStones(new int[]{5,2,300,4,1,1,1,1,3}, 3));

    // (1,1),1,2,200,2      => 2
    // (2,1),2,200,2        => 3
    // (3,2),200,2          => 5
    // 5,(200,2)            => 202
    // 5,202                => 207
    assertEquals(419, mergeStones(new int[]{1,1,1,2,200,2}, 2));


  }
}
