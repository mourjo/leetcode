package me.mourjo.y22;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KClosestElements {

    /**
     * https://leetcode.com/problems/find-k-closest-elements/ Given a sorted array, two integers k
     * and x, find the k closest elements to x in the array. The result should also be sorted in
     * ascending order. If there is a tie, the smaller elements are always preferred.
     * <p>
     * Example 1: Input: [1,2,3,4,5], k=4, x=3 Output: [1,2,3,4] Example 2: Input: [1,2,3,4,5], k=4,
     * x=-1 Output: [1,2,3,4] Note: The value k is positive and will always be smaller than the
     * length of the sorted array. Length of the given array is positive and will not exceed 10^4
     * Absolute value of elements in the array and x will not exceed 10^4
     */


    public static List<Integer> findClosestElements(int[] a, int k, int x) {
        if (a.length == 0) {
            return new ArrayList<>();
        }

        int low = 0, high = a.length - 1;
        if (x > a[a.length - 1]) {
            low = a.length - 1;
        } else if (x < a[0]) {
            high = 0;
        }

        // binary search when x does not fall outside the array
        else {
            while (low <= high) {
                int mid = (low + high) / 2;
                if (x > a[mid]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }

        // low is now either equal to x or just the next greatest value
        if (low > 0 && Math.abs(a[low - 1] - x) < Math.abs(a[low] - x)) {
            low--;
        }

        // low contains the closest element
        Stack<Integer> leftResult = new Stack<>();
        LinkedList<Integer> rightResult = new LinkedList<>();

        int left = low, right = low + 1;
        for (int i = 0; i < k; i++) {

            if (left < 0) {
                rightResult.add(a[right++]);
            } else if (right >= a.length) {
                leftResult.push(a[left--]);
            } else {
                if (Math.abs(a[left] - x) > Math.abs(a[right] - x)) {
                    rightResult.add(a[right++]);
                } else {
                    leftResult.push(a[left--]);
                }
            }
        }

        // this is in reverse order
        for (int leftItem : leftResult) {
            rightResult.addFirst(leftItem);
        }

        return rightResult;
    }


    @Test
    public static void main(String[] args) {
        assertEquals(Arrays.asList(1, 2, 3, 4),
                findClosestElements(new int[]{1, 2, 3, 4, 5}, 4, 3));

        assertEquals(Arrays.asList(1, 2, 3, 4),
                findClosestElements(new int[]{1, 2, 3, 4, 5}, 4, -1));

        assertEquals(Arrays.asList(11, 12),
                findClosestElements(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, 2, 500));

        assertEquals(List.of(),
                findClosestElements(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, 0, 500));

        assertEquals(Arrays.asList(6, 7, 8),
                findClosestElements(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, 3, 7));

        assertEquals(Arrays.asList(5, 6, 8),
                findClosestElements(new int[]{1, 2, 3, 4, 5, 6, 8, 9, 10, 11, 12}, 3, 7));

        assertEquals(Arrays.asList(1, 2, 4, 5000),
                findClosestElements(new int[]{1, 2, 4, 5000}, 4, -1));

        assertEquals(Arrays.asList(1, 1, 1, 1),
                findClosestElements(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        4,
                        2));

        assertEquals(Arrays.asList(1, 1, 1, 1),
                findClosestElements(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        4,
                        -2));

        assertEquals(Arrays.asList(-1, 1, 1, 1),
                findClosestElements(new int[]{-1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        4,
                        -2));

        assertEquals(Arrays.asList(3, 3, 4),
                findClosestElements(new int[]{0, 0, 1, 2, 3, 3, 4, 7, 7, 8}, 3, 5));

        assertEquals(Arrays.asList(4, 7, 7),
                findClosestElements(new int[]{0, 0, 1, 2, 3, 3, 4, 7, 7, 8}, 3, 6));

        assertEquals(Arrays.asList(4, 7, 7),
                findClosestElements(new int[]{0, 0, 1, 2, 3, 3, 4, 7, 7, 800}, 3, 50));

        assertEquals(Arrays.asList(7, 7, 55),
                findClosestElements(new int[]{0, 0, 1, 2, 3, 3, 4, 7, 7, 55}, 3, 50));

        assertEquals(Arrays.asList(7, 7, 50),
                findClosestElements(new int[]{0, 0, 1, 2, 3, 3, 4, 7, 7, 50}, 3, 50));

    }
}
