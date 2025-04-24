package me.mourjo.y22;

/*
Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).

Find the minimum element.

The array may contain duplicates.

Example 1:

Input: [1,3,5]
Output: 1
Example 2:

Input: [2,2,2,0,1]
Output: 0

 */

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinimumInRotatedSortedArrayII {

    public static int findMin(int[] nums) {
        int left = 0, right = nums.length - 1;

        while (left < right && right - left > 1) {
            if (nums[left] < nums[right]) {
                return nums[left];
            }

            int m = (left + right) / 2;
            if (m > 0 && m < nums.length - 1 && nums[m] < nums[m + 1] && nums[m] < nums[m - 1]) {
                return nums[m];
            }

            while (left < right && nums[left] == nums[right]) {
                left++;
            }

            if (nums[left] < nums[right]) {
                return nums[left];
            }

            if (m > 0 && m < nums.length - 1 && nums[m] < nums[m + 1] && nums[m] < nums[m - 1]) {
                return nums[m];
            }

            if (nums[m] > nums[right]) {
                left = m;
            } else if (nums[m] < nums[left]) {
                right = m;
            }

        }

        return Math.min(nums[left], nums[right]);
    }

    public static void main(String[] args) {
        assertEquals(1, findMin(new int[]{1, 3, 5}));
        assertEquals(0, findMin(new int[]{2, 2, 2, 0, 1}));
        assertEquals(1, findMin(
                new int[]{1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
                        5,
                        5}));
        assertEquals(1, findMin(
                new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
                        5,
                        5}));
        assertEquals(1, findMin(
                new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 2,
                        3,
                        4}));
        assertEquals(5, findMin(new int[]{5}));
        assertEquals(4, findMin(new int[]{4, 5}));
        assertEquals(4, findMin(new int[]{5, 4}));
        assertEquals(4, findMin(new int[]{4, 5, 6}));
        assertEquals(4, findMin(new int[]{6, 4, 5}));
        assertEquals(4, findMin(new int[]{5, 6, 4}));
        assertEquals(1, findMin(new int[]{3, 1, 1}));
        assertEquals(1, findMin(new int[]{1, 1, 1, 1, 3, 1, 1}));
        assertEquals(1, findMin(new int[]{1, 1, 1, 1, 1, 1, 1}));
    }
}
