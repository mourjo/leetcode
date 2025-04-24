package me.mourjo.y24.arrays;

import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/* Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.

You must write an algorithm that runs in O(n) time.



Example 1:

Input: nums = [100,4,200,1,3,2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
Example 2:

Input: nums = [0,3,7,2,5,8,4,6,0,1]
Output: 9


Constraints:

0 <= nums.length <= 105
-10^9 <= nums[i] <= 10^9

 */
public class LongestSubsequenceUnsortedArray {

    public static void main(String[] args) {
        var app = new LongestSubsequenceUnsortedArray();
        Assertions.assertEquals(4, app.longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));
        Assertions.assertEquals(9, app.longestConsecutive(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}));
        Assertions.assertEquals(9,
                app.longestConsecutive(new int[]{0, 3, 7, 2, 5, 8, 3, 4, 6, 0, 1, 5, 5, 5}));

    }

    // neet code's solution - even slower than mine
    public int longestConsecutive(int[] nums) {
        var set = new HashSet<Integer>();
        for (int n : nums) {
            set.add(n);
        }

        int longest = 0;
        for (int n : nums) {
            if (!set.contains(n - 1)) {
                // first in sequence
                int length = 1;
                while (set.contains(n + length)) {
                    length++;
                }
                if (longest < length) {
                    longest = length;
                }
            }
        }
        return longest;
    }


    public int longestConsecutive2(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        Map<Integer, Integer> counts = new HashMap<>();
        for (int n : nums) {
            counts.put(n, 1);
        }

        int maxSeqSize = 1;

        for (int n : nums) {
            if (!counts.containsKey(n)) {
                continue;
            }
            int currSeqSize = counts.get(n);
            int offest = 1;
            while (true) {
                if (counts.containsKey(n - offest)) {
                    currSeqSize += counts.get(n - offest);
                    counts.remove(n - offest);
                    offest++;
                } else {
                    break;
                }
            }
            counts.put(n, currSeqSize);
            if (currSeqSize > maxSeqSize) {
                maxSeqSize = currSeqSize;
            }
            if (maxSeqSize == nums.length) {
                return maxSeqSize;
            }
        }

        return maxSeqSize;
    }


}
