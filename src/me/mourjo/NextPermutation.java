package me.mourjo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NextPermutation {

    /**
     * /https://leetcode.com/problems/next-permutation/
     * Implement next permutation, which rearranges numbers into the lexicographically next
     * greater permutation of numbers.
     *
     * If such arrangement is not possible, it must rearrange it as the lowest possible order
     * (ie, sorted in ascending order).
     *
     * The replacement must be in-place and use only constant extra memory.
     *
     * Here are some examples. Inputs are in the left-hand column and its corresponding
     * outputs are in the right-hand column.
     *
     * 1,2,3 → 1,3,2
     * 3,2,1 → 1,2,3
     * 1,1,5 → 1,5,1
     */

    public static void rev (int a[], int start, int end) {

        while (start < end) {
            int t = a[start];
            a[start] = a[end];
            a[end] = t;
            start++;
            end--;
        }
    }

    public static void nextPermutation(int[] a) {
        int pivot=-1;
        for (int i = a.length-2; i >= 0; i--) {
            if (a[i+1] > a[i]) {
                pivot = i;
                break;
            }
        }

        // do not sort
        // reverse because we know that all elements to the right of the pivot are in increasing order
        if (pivot == -1)
            rev(a, 0, a.length-1);
        else {
            int min = Integer.MAX_VALUE, minPos = pivot;
            for (int i = pivot+1; i < a.length; i++) {
                // note: push as much to the right as possible (take equal elements into account)
                if (a[pivot] < a[i] && a[i] <= min) {
                    min = a[i];
                    minPos = i;
                }
            }
            int t = a[pivot];
            a[pivot] = a[minPos];
            a[minPos] = t;

            // do not sort
            // reverse because we know that all elements to the right of the pivot are in increasing order
            rev(a, pivot+1, a.length-1);
        }
    }

    public static List<Integer> toList (int [] a) {
        List<Integer> list = new LinkedList<>();
        for (int x : a)
            list.add(x);
        return list;
    }

    public static void main(String[] args) {
        int n[] = new int[]{1,2,3};
        System.out.println("Input:" + toList(n));
        nextPermutation(n);
        System.out.println(toList(n) + "\n");

        n = new int[]{2,3,1,4};
        System.out.println("Input:" + toList(n));
        nextPermutation(n);
        System.out.println(toList(n) + "\n");

        n = new int[]{3,2,1};
        System.out.println("Input:" + toList(n));
        nextPermutation(n);
        System.out.println(toList(n) + "\n");

        n = new int[]{1,1,5};
        System.out.println("Input:" + toList(n));
        nextPermutation(n);
        System.out.println(toList(n) + "\n");

        n = new int[]{1,5,1};
        System.out.println("Input:" + toList(n));
        nextPermutation(n);
        System.out.println(toList(n) + "\n");

        n = new int[]{5,1,1};
        System.out.println("Input:" + toList(n));
        nextPermutation(n);
        System.out.println(toList(n) + "\n");

        //[2,3,1,3,3]
        n = new int[]{2,3,1,3,3};
        System.out.println("Input:" + toList(n));
        nextPermutation(n);
        System.out.println(toList(n) + "\n");
    }
}
