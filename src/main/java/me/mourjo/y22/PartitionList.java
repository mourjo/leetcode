package me.mourjo.y22;

/*
https://leetcode.com/problems/partition-list/

Given a linked list and a value x, partition it such that all nodes
less than x come before nodes greater than or equal to x.

You should preserve the original relative order of the nodes in each of the two partitions.

Example:

Input: head = 1->4->3->2->5->2, x = 3
Output:       1->2->2->4->3->5

 */


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

class ListNode {

    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        return val + "->" + next;
    }
}


public class PartitionList {

    public static ListNode partition(ListNode head, int x) {

        ListNode p = head, less = null, high = null, pLess = null, pHigh = null;

        while (p != null) {
            if (p.val >= x) {
                if (high == null) {
                    high = new ListNode(p.val);
                    pHigh = high;
                } else {
                    high.next = new ListNode(p.val);
                    high = high.next;
                }
            } else {
                if (less == null) {
                    less = new ListNode(p.val);
                    pLess = less;
                } else {
                    less.next = new ListNode(p.val);
                    less = less.next;
                }
            }
            p = p.next;
        }

        if (pLess != null) {
            less.next = pHigh;
            return pLess;
        }

        return pHigh;
    }

    public static void main(String[] args) {
        ListNode h;
        h = new ListNode(1);
        ListNode p = h;
        for (int x : Arrays.asList(4, 3, 2, 5, 2)) {
            p.next = new ListNode(x);
            p = p.next;
        }

        assertEquals("1->2->2->4->3->5->null", partition(h, 3).toString());
        assertEquals("1->4->3->2->5->2->null", partition(h, 300).toString());
        assertEquals("1->4->3->2->5->2->null", partition(h, 0).toString());
        assertNull(partition(null, 3));
        assertEquals("100->null", partition(new ListNode(100), 3).toString());
        assertEquals("100->null", partition(new ListNode(100), 300).toString());
    }
}
