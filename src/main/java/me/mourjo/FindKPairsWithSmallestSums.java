package me.mourjo;

/* https://leetcode.com/problems/find-k-pairs-with-smallest-sums/submissions/
You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.

Define a pair (u,v) which consists of one element from the first array and one element from the second array.

Find the k pairs (u1,v1),(u2,v2) ...(uk,vk) with the smallest sums.

Example 1:
Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
Output: [[1,2],[1,4],[1,6]]
Explanation: The first 3 pairs are returned from the sequence:
             [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]


Example 2:
Input: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
Output: [1,1],[1,1]
Explanation: The first 2 pairs are returned from the sequence:
             [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]


Example 3:
Input: nums1 = [1,2], nums2 = [3], k = 3
Output: [1,3],[2,3]
Explanation: All possible pairs are returned from the sequence: [1,3],[2,3]



 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class FindKPairsWithSmallestSums {

  public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
    if (nums1.length == 0 || nums2.length == 0) {
      return new ArrayList<>();
    }

    ArrayList<List<Integer>> list = new ArrayList<>();

    PriorityQueue<int[]> pq = new PriorityQueue<>(k,
        new Comparator<int[]>() {
          public int compare(int[] a, int[] b) {
            // 0 index is location in nums1
            // 1 index is value in nums1
            // 2 index is location in nums2
            // 3 index is value in nums2
            return (a[1] + a[3]) - (b[1] + b[3]);
          }
        });

    // take first k elements of nums1 and
    // find all possible combinations to go with nums2[0]
    for (int i = 0; i < nums1.length && i < k; i++) {
      pq.add(new int[]{i, nums1[i], 0, nums2[0]});
    }

    // for each minimum combination (a,b) in the pq, pair the ith number in nums1
    // with j+1th number in nums2 (where i and j are the indexes of a and b
    // in nums1 and nums2 respectively)
    while (list.size() < Math.min(k, nums1.length * nums2.length)) {
      int[] minComb = pq.remove();
      list.add(Arrays.asList(minComb[1], minComb[3]));
      if (minComb[2] < nums2.length - 1) {
        pq.add(new int[]{minComb[0], minComb[1], minComb[2] + 1, nums2[minComb[2] + 1]});
      }
    }

    return list;
  }

  public static void main(String[] args) {
    assertEquals(
        Arrays.asList(Arrays.asList(1, 2), Arrays.asList(1, 4), Arrays.asList(1, 6)),
        kSmallestPairs(new int[]{1, 7, 11}, new int[]{2, 4, 6}, 3));

    assertEquals(
        Arrays.asList(Arrays.asList(1, 1), Arrays.asList(1, 1)),
        kSmallestPairs(new int[]{1, 1, 2}, new int[]{1, 2, 3}, 2));

    assertEquals(
        Arrays.asList(Arrays.asList(1, 3), Arrays.asList(2, 3)),
        kSmallestPairs(new int[]{1, 2}, new int[]{3}, 3));

    assertEquals(
        Arrays.asList(Arrays.asList(1, 10), Arrays.asList(1, 20), Arrays.asList(1, 30)),
        kSmallestPairs(new int[]{1}, new int[]{10, 20, 30, 40, 50}, 3));

    assertEquals(
        Arrays.asList(Arrays.asList(10, 1), Arrays.asList(20, 1), Arrays.asList(30, 1)),
        kSmallestPairs(new int[]{10, 20, 30, 40}, new int[]{1}, 3));

    assertEquals(
        Collections.singletonList(Arrays.asList(10, 1)),
        kSmallestPairs(new int[]{10}, new int[]{1}, 3000));

    assertEquals(
        Arrays.asList(
            Arrays.asList(1, 10),
            Arrays.asList(2, 10),
            Arrays.asList(3, 10),
            Arrays.asList(1, 20),
            Arrays.asList(2, 20),
            Arrays.asList(3, 20),
            Arrays.asList(1, 30),
            Arrays.asList(2, 30),
            Arrays.asList(3, 30)),
        kSmallestPairs(new int[]{1, 2, 3}, new int[]{10, 20, 30}, 500));

    assertEquals(
        Arrays.asList(
            Arrays.asList(1, 10),
            Arrays.asList(2, 10),
            Arrays.asList(3, 10),
            Arrays.asList(1, 20),
            Arrays.asList(2, 20)),
        kSmallestPairs(new int[]{1, 2, 3}, new int[]{10, 20, 30}, 5));

    assertEquals(
        Arrays.asList(
            Arrays.asList(1, 10),
            Arrays.asList(2, 10)),
        kSmallestPairs(new int[]{1, 2, 3}, new int[]{10, 20, 30}, 2));

    assertEquals(
        new ArrayList<List<Integer>>(),
        kSmallestPairs(new int[]{}, new int[]{}, 5));

    assertEquals(
        new ArrayList<List<Integer>>(),
        kSmallestPairs(new int[]{1, 2, 3}, new int[]{}, 5));

    assertEquals(
        new ArrayList<List<Integer>>(),
        kSmallestPairs(new int[]{}, new int[]{1, 2, 3}, 5));

    assertEquals(
        Arrays.asList(
            Arrays.asList(1, 1),
            Arrays.asList(1, 1),
            Arrays.asList(1, 2),
            Arrays.asList(1, 2),
            Arrays.asList(2, 1),
            Arrays.asList(1, 3),
            Arrays.asList(2, 2),
            Arrays.asList(1, 3),
            Arrays.asList(2, 3)
        ),
        kSmallestPairs(new int[]{1, 1, 2}, new int[]{1, 2, 3}, 10)
    );

    assertEquals(
        Arrays.asList(
            Arrays.asList(5, 6),  // 11
            Arrays.asList(7, 6),  // 13
            Arrays.asList(5, 8),  // 13
            Arrays.asList(7, 8),  // 15
            Arrays.asList(5, 10), // 15
            Arrays.asList(9, 6),  // 15
            Arrays.asList(7, 10), // 17
            Arrays.asList(9, 8),  // 17
            Arrays.asList(9, 10)  // 19
        ),
        kSmallestPairs(new int[]{5, 7, 9}, new int[]{6, 8, 10}, 9)
    );
  }
}
