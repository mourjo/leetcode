package me.mourjo.y24.arrays;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;

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


    public int longestConsecutive(int[] nums) {
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
        }

        return maxSeqSize;
    }


}
