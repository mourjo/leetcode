package me.mourjo.y22;

public class SearchInRotatedSortedArray {

    /**
     * https://leetcode.com/problems/search-in-rotated-sorted-array/ Suppose an array sorted in
     * ascending order is rotated at some pivot unknown to you beforehand.
     * <p>
     * (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
     * <p>
     * You are given a target value to search. If found in the array return its index, otherwise
     * return -1.
     * <p>
     * You may assume no duplicate exists in the array.
     * <p>
     * Your algorithm's runtime complexity must be in the order of O(log n).
     * <p>
     * Input: nums = [4,5,6,7,0,1,2], target = 0 Output: 4
     * <p>
     * Input: nums = [4,5,6,7,0,1,2], target = 3 Output: -1
     */

    static int translate(int i, int offset, int n) {
        return (i + offset) % n;
    }

    public static int search(int[] a, int target) {
        int n = a.length, low = 0, high = n - 1;
        // find the index of the smallest value using binary search.
        // Loop will terminate since mid < hi, and lo or hi will shrink by at least 1.
        // Proof by contradiction that mid < hi: if mid==hi, then lo==hi and loop would have been terminated.
        while (low < high) {
            int mid = (low + high) / 2;
            if (a[mid] > a[high]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        int offset = low;
        low = 0;
        high = n - 1;

        while (low <= high) {

            int mid = (low + high) / 2;
            int transMid = translate(mid, offset, n);

            int elem = a[transMid];
            if (elem == target) {
                return transMid;
            }

            if (target < elem) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Utilities.check(search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0), 4);
        Utilities.check(search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3), -1);
        Utilities.check(search(new int[]{4, 5, 6, 7, 0, 1, 2}, 7), 3);
        Utilities.check(search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0), 4);
        Utilities.check(search(new int[]{4, 5, 6, 7, 0, 1, 2}, 1), 5);
        Utilities.check(search(new int[]{4, 5, 6, 7, 0, 1, 2}, 4), 0);

        Utilities.check(search(new int[]{4, 1, 2}, 4), 0);
        Utilities.check(search(new int[]{0, 1, 2}, 4), -1);
        Utilities.check(search(new int[]{2, 1, 0}, 4), -1);

        Utilities.check(search(new int[]{2, 1, 0}, 2), 0);
        Utilities.check(search(new int[]{0, 1, 2}, 1), 1);
        Utilities.check(search(new int[]{0}, 4), -1);
        Utilities.check(search(new int[]{0}, 0), 0);
        Utilities.check(search(new int[]{0, 1}, 4), -1);
        Utilities.check(search(new int[]{0, 1}, 1), 1);
        Utilities.check(search(new int[]{1, 0}, 1), 0);
    }
}
