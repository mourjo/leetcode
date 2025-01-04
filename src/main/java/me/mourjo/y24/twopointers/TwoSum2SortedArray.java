package me.mourjo.y24.twopointers;

import org.junit.jupiter.api.Assertions;

/*
https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/description/

Given a 1-indexed array of integers numbers that is already sorted in non-decreasing order, find two numbers such that they add up to a specific target number. Let these two numbers be numbers[index1] and numbers[index2] where 1 <= index1 < index2 <= numbers.length.

Return the indices of the two numbers, index1 and index2, added by one as an integer array [index1, index2] of length 2.

The tests are generated such that there is exactly one solution. You may not use the same element twice.

Your solution must use only constant extra space.



Example 1:

Input: numbers = [2,7,11,15], target = 9
Output: [1,2]
Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We return [1, 2].
Example 2:

Input: numbers = [2,3,4], target = 6
Output: [1,3]
Explanation: The sum of 2 and 4 is 6. Therefore index1 = 1, index2 = 3. We return [1, 3].
Example 3:

Input: numbers = [-1,0], target = -1
Output: [1,2]
Explanation: The sum of -1 and 0 is -1. Therefore index1 = 1, index2 = 2. We return [1, 2].


Constraints:

2 <= numbers.length <= 3 * 104
-1000 <= numbers[i] <= 1000
numbers is sorted in non-decreasing order.
-1000 <= target <= 1000
The tests are generated such that there is exactly one solution.
 */
public class TwoSum2SortedArray {

    public static void main(String[] args) {
        var app = new TwoSum2SortedArray();
        Assertions.assertArrayEquals(new int[]{4, 5},
            app.twoSum(new int[]{1, 2, 3, 4, 4, 9, 56, 90}, 8));
        Assertions.assertArrayEquals(new int[]{1, 2}, app.twoSum(new int[]{-1, -1, 1, 1, 1}, -2));
        Assertions.assertArrayEquals(new int[]{1, 2}, app.twoSum(new int[]{2, 7, 11, 15}, 9));
        Assertions.assertArrayEquals(new int[]{1, 3}, app.twoSum(new int[]{2, 3, 4}, 6));
        Assertions.assertArrayEquals(new int[]{1, 2}, app.twoSum(new int[]{-1, 0}, -1));
        Assertions.assertArrayEquals(new int[]{2, 3}, app.twoSum(new int[]{1, 2, 3, 40, 50}, 5));
        Assertions.assertArrayEquals(new int[]{}, app.twoSum(new int[]{1, 2, 30, 40, 50}, 5));
        Assertions.assertArrayEquals(new int[]{2, 4}, app.twoSum(new int[]{1, 2, 30, 40, 50}, 42));
    }

    public int[] twoSum(int[] numbers, int target) {
        int p1 = 0, p2 = numbers.length - 1;
        while (p1 < p2) {
            int currentSum = numbers[p1] + numbers[p2];

            if (currentSum > target) {
                p2--;
            } else if (currentSum < target) {
                p1++;
            } else {
                return new int[]{p1 + 1, p2 + 1};
            }
        }
        return new int[]{};
    }

}
