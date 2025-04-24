package me.mourjo.y22;


import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReduceArraySizeToHalf {

    /* https://leetcode.com/problems/reduce-array-size-to-the-half/
  Given an array arr.  You can choose a set of integers and remove all the occurrences of these integers in the array.
  Return the minimum size of the set so that at least half of the integers of the array are removed.

  Example 1:
  Input: arr = [3,3,3,3,5,5,5,2,2,7]
  Output: 2
  Explanation: Choosing {3,7} will make the new array [5,5,5,2,2] which has size 5 (i.e equal to half of the size of the old array).
  Possible sets of size 2 are {3,5},{3,2},{5,2}.
  Choosing set {2,7} is not possible as it will make the new array [3,3,3,3,5,5,5] which has size greater than half of the size of the old array.

  Example 2:
  Input: arr = [7,7,7,7,7,7]
  Output: 1
  Explanation: The only possible set you can choose is {7}. This will make the new array empty.

  Example 3:
  Input: arr = [1,9]
  Output: 1

  Example 4:
  Input: arr = [1000,1000,3,7]
  Output: 1

  Example 5:
  Input: arr = [1,2,3,4,5,6,7,8,9,10]
  Output: 5

  Constraints:
  1 <= arr.length <= 10^5
  arr.length is even.
  1 <= arr[i] <= 10^5
     */
    public static int minSetSize(int[] arr) {
        Map<Integer, Integer> numToFreq = new HashMap<>();

        // +1 because we will not use the 0th index
        List<Integer>[] freqToNums = new List[arr.length + 1];

        for (int a : arr) {
            if (!numToFreq.containsKey(a)) {
                numToFreq.put(a, 1);
            } else {
                numToFreq.put(a, numToFreq.get(a) + 1);
            }
        }

        for (int n : numToFreq.keySet()) {
            int f = numToFreq.get(n);
            if (freqToNums[f] == null) {
                freqToNums[f] = new LinkedList<>();
            }
            freqToNums[f].add(n);
        }

        int currentSize = arr.length, c = 0;

        for (int i = arr.length; i >= 0; i--) {
            List<Integer> nums = freqToNums[i];
            if (nums != null) {
                for (int item : nums) {
                    c++;
                    currentSize -= numToFreq.get(item);
                    if (currentSize <= arr.length / 2) {
                        return c;
                    }
                }
            }
        }
        return arr.length;
    }

    @Test
    public static void main(String[] args) {
        assertEquals(2, minSetSize(new int[]{3, 3, 3, 3, 5, 5, 5, 2, 2, 7}));
        assertEquals(1, minSetSize(new int[]{7, 7, 7, 7, 7, 7, 7, 7}));
        assertEquals(1, minSetSize(new int[]{1, 9}));
        assertEquals(1, minSetSize(new int[]{1000, 1000, 3, 7}));
        assertEquals(5, minSetSize(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));
        assertEquals(0, minSetSize(new int[]{}));
        assertEquals(1, minSetSize(new int[]{3}));
        assertEquals(1, minSetSize(
                new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1}));
        assertEquals(1, minSetSize(new int[]{1, 1, 2, 2}));
        assertEquals(1, minSetSize(new int[]{1, 1, 1, 2, 2}));
        assertEquals(2, minSetSize(new int[]{1, 1, 2, 2, 3, 3}));
        assertEquals(5,
                minSetSize(new int[]{9, 77, 63, 22, 92, 9, 14, 54, 8, 38, 18, 19, 38, 68, 58, 19}));
    }
}
