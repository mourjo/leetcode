package me.mourjo.y24.arrays;

import org.junit.jupiter.api.Assertions;

/*
Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].

The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.

You must write an algorithm that runs in O(n) time and without using the division operation.



Example 1:

Input: nums = [1,2,3,4]
Output: [24,12,8,6]
Example 2:

Input: nums = [-1,1,0,-3,3]
Output: [0,0,9,0,0]


Constraints:

2 <= nums.length <= 105
-30 <= nums[i] <= 30
The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.


Follow up: Can you solve the problem in O(1) extra space complexity? (The output array does not count as extra space for space complexity analysis.)
 */
public class ProductOfArrayExceptSelf {

    public static void main(String[] args) {
        var app = new ProductOfArrayExceptSelf();
        Assertions.assertArrayEquals(new int[]{24, 12, 8, 6},
                app.productExceptSelf(new int[]{1, 2, 3, 4}));
        Assertions.assertArrayEquals(new int[]{0, 0, 9, 0, 0},
                app.productExceptSelf(new int[]{-1, 1, 0, -3, 3}));
        Assertions.assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0},
                app.productExceptSelf(new int[]{-1, 1, 0, -3, 3, 0}));
        Assertions.assertArrayEquals(new int[]{}, app.productExceptSelf(new int[]{}));
        Assertions.assertArrayEquals(new int[]{6, 3, 2}, app.productExceptSelf(new int[]{1, 2, 3}));
    }

    public int[] productExceptSelf(int[] nums) {
        int[] result = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            result[i] = 1;
        }

        int leftProd = 1, rightProd = 1;
        for (int i = 0; i < nums.length; i++) {
            // put partial left products in the correct index
            result[i] *= leftProd;
            leftProd *= nums[i];
        }

        for (int i = nums.length - 1; i >= 0; i--) {
            // put partial right products in the correct index
            result[i] *= rightProd;
            rightProd *= nums[i];
        }

        return result;
    }


    /*
    // this does not work becuase it uses division
    public int[] productExceptSelf(int[] nums) {
        int mul = 1;
        int mulWithoutZero = 1;

        int numZeros = 0;

        for (int n : nums) {
            mul *= n;
            if (n == 0) {
                numZeros++;
            } else {
                mulWithoutZero *= n;
            }
        }

        int[] result = new int[nums.length];

        for(int i = 0; i < result.length; i++) {
            if (nums[i] == 0) {
                if(numZeros > 1) {
                    result[i] = 0;
                } else {
                    result[i] = mulWithoutZero;
                }

            } else {
                result[i] = (mul / nums[i]);
            }
        }
        return result;
    }
     */
}
