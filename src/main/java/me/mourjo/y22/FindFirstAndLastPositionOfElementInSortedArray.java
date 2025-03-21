package me.mourjo.y22;

public class FindFirstAndLastPositionOfElementInSortedArray {

    /**
     * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/ Given
     * an array of integers nums sorted in ascending order, find the starting and ending position of
     * a given target value.
     * <p>
     * Your algorithm's runtime complexity must be in the order of O(log n).
     * <p>
     * If the target is not found in the array, return [-1, -1].
     * <p>
     * Example 1:
     * <p>
     * Input: nums = [5,7,7,8,8,10], target = 8 Output: [3,4] Example 2:
     * <p>
     * Input: nums = [5,7,7,8,8,10], target = 6 Output: [-1,-1]
     */

    public static int[] searchRange(int[] a, int target) {
        int n = a.length, high = n - 1, low = 0, location = -1, lowest, highest;
        // Idea : Find one occurrence
        // Divide the array into two : [{...left...} element {...right...}]
        // Keep going left binary search wise until low==high
        // Keep going right binary search wise until low==right

        while (low <= high) {
            int mid = (low + high) / 2;
            if (a[mid] == target) {
                location = mid;
                break;
            }
            if (target < a[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        if (location == -1) {
            return new int[]{-1, -1};
        }

        // go left
        low = 0;
        high = location - 1;
        lowest = location;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (a[mid] == target) {
                high = mid - 1;
                lowest = mid;
            } else if (target < a[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        // go right
        low = location + 1;
        high = n - 1;
        highest = location;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (a[mid] == target) {
                low = mid + 1;
                highest = mid;
            } else if (target < a[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return new int[]{lowest, highest};
    }

    public static void main(String[] args) {
        System.out.println("Expected: " + Utilities.toList(new Integer[]{3, 4}));
        System.out.println(
            "Actual: " + Utilities.toIntList(searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8)));
        System.out.println();

        System.out.println("Expected: " + Utilities.toList(new Integer[]{-1, -1}));
        System.out.println(
            "Actual: " + Utilities.toIntList(searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6)));
        System.out.println();

        System.out.println("Expected: " + Utilities.toList(new Integer[]{4, 16}));
        System.out.println("Actual: " + Utilities.toIntList(
            searchRange(new int[]{1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 7}, 5)));
        System.out.println();

        System.out.println("Expected: " + Utilities.toList(new Integer[]{2, 7}));
        System.out.println(
            "Actual: " + Utilities.toIntList(searchRange(new int[]{1, 2, 3, 3, 3, 3, 3, 3}, 3)));
        System.out.println();

        System.out.println("Expected: " + Utilities.toList(new Integer[]{0, 5}));
        System.out.println(
            "Actual: " + Utilities.toIntList(searchRange(new int[]{1, 1, 1, 1, 1, 1, 2}, 1)));
        System.out.println();

        System.out.println("Expected: " + Utilities.toList(new Integer[]{0, 0}));
        System.out.println("Actual: " + Utilities.toIntList(searchRange(new int[]{1}, 1)));
        System.out.println();

    }
}
