package me.mourjo.y22;

public class JumpGame {

    /**
     * https://leetcode.com/problems/jump-game/
     * <p>
     * <p>
     * Given an array of non-negative integers, you are initially positioned at the first index of
     * the array.
     * <p>
     * Each element in the array represents your maximum jump length at that position.
     * <p>
     * Determine if you are able to reach the last index.
     * <p>
     * Example 1:
     * <p>
     * Input: [2,3,1,1,4] Output: true Explanation: Jump 1 step from index 0 to 1, then 3 steps to
     * the last index. Example 2:
     * <p>
     * Input: [3,2,1,0,4] Output: false Explanation: You will always arrive at index 3 no matter
     * what. Its maximum jump length is 0, which makes it impossible to reach the last index.
     */

    public static boolean canJump(int[] nums) {

        if (nums.length == 0) {
            return false;
        }
        int range = nums[0];
        for (int i = 0; i <= range; i++) {
            int n = i + nums[i];
            if (n > range) {
                range = n;
            }
            if (range >= nums.length) {
                return true;
            }
            if (i == nums.length - 1) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Utilities.check(canJump(new int[]{2, 3, 1, 1, 4}), true);
        Utilities.check(canJump(new int[]{3, 2, 1, 0, 4}), false);
        Utilities.check(canJump(new int[]{300, 2, 1, 0, 4}), true);
        Utilities.check(canJump(
            new int[]{2, 3, 1, 1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0}), false);
        Utilities.check(canJump(
            new int[]{2, 3, 1, 1, 4, 0, 0, 0, 10000000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0}), true);
        Utilities.check(canJump(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0}), false);
        Utilities.check(canJump(new int[]{0, 10000, 0, 0, 0, 0, 0, 0, 0}), false);
        Utilities.check(canJump(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1}), true);
        Utilities.check(canJump(new int[]{1, 1, 1, 1, 0, 1, 1, 1, 1}), false);
        Utilities.check(canJump(new int[]{1, 1, 1, 2, 0, 1, 1, 1, 1}), true);
        Utilities.check(canJump(new int[]{}), false);
        Utilities.check(canJump(new int[]{0}), true);
    }
}
