package me.mourjo;

public class ContainerWithMostWater {

    /**
     * https://leetcode.com/problems/container-with-most-water/
     * Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai).
     * n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). Find two
     * lines, which together with x-axis forms a container, such that the container contains the most water.
     * That is find the max area bounded by the bars.
     *
     * Note: You may not slant the container and n is at least 2.
     *
     * The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of
     * water (blue section) the container can contain is 49.
     * Example:
     *
     * Input: [1,8,6,2,5,4,8,3,7]
     * Output: 49
     */

    static int findArea(int[] height, int left, int right) {
        int m = height[left] < height[right] ? height[left] : height[right];
        int area = m * (right - left);
        return area;
    }

    public static int maxArea(int[] height) {
        int maxArea = -1, left = 0, right = height.length-1;
        while (left < right) {
            int curr = findArea(height, left, right);
            if (curr > maxArea)
                maxArea = curr;

            if (height[left] < height[right])
                left++;
            else right--;
        }
        return maxArea;
    }

    public static void main(String[] args) {
        Utilities.check(maxArea(new int[]{1,8,6,2,5,4,8,3,7}), 49);
        Utilities.check(maxArea(new int[]{2,3,4,5,18,17,6}), 17);
    }

}
