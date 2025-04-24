package me.mourjo.y24.arrays;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.

Example 1:

Input: nums = [1,1,1,2,2,3], k = 2
Output: [1,2]
Example 2:

Input: nums = [1], k = 1
Output: [1]


Constraints:
1 <= nums.length <= 105
-104 <= nums[i] <= 104
k is in the range [1, the number of unique elements in the array].
It is guaranteed that the answer is unique.


Follow up: Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 */
public class TopKFrequent {

    public static void main(String[] args) {
        var app = new TopKFrequent();
        Assertions.assertArrayEquals(new int[]{1, 2},
                app.topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2));
        Assertions.assertArrayEquals(new int[]{1}, app.topKFrequent(new int[]{1}, 1));
        Assertions.assertArrayEquals(new int[]{4, 1},
                app.topKFrequent(new int[]{4, 3, 1, 1, 1, 2, 2, 3, 4, 4, 4, 1, 4, 4, 4, 4, 4, 1, 1, 1},
                        2));
    }

    public int[] topKFrequent(int[] nums, int k) {
        // this is random access - for frequency
        var numToFreq = new HashMap<Integer, Integer>();

        // this is sequential access - index == frequency (max frequency will be nums.length)
        // reverse traversal of this should give the top K elements
        List<Integer>[] freqsToNum = new ArrayList[nums.length + 1];

        for (int n : nums) {
            numToFreq.put(n, numToFreq.getOrDefault(n, 0) + 1);
        }

        for (int num : numToFreq.keySet()) {
            int freq = numToFreq.get(num);
            if (freqsToNum[freq] == null) {
                freqsToNum[freq] = new ArrayList<>();
            }
            freqsToNum[freq].add(num);
        }

        int[] result = new int[k];
        int count = 0;

        for (int i = freqsToNum.length - 1; i >= 0; i--) {
            if (freqsToNum[i] != null) {
                for (int item : freqsToNum[i]) {
                    result[count++] = item;
                    if (count == k) {
                        return result;
                    }
                }
            }
        }

        return result;
    }
}
