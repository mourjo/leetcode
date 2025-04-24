package me.mourjo.y22;

public class RemoveNthNodeFromEndofList {

    static ListNode toList(int[] d) {
        if (d.length == 0) {
            return null;
        }
        ListNode head = new ListNode(d[0]);
        ListNode h = head;

        for (int i = 1; i < d.length; i++) {
            h.next = new ListNode(d[i]);
            h = h.next;
        }

        return head;
    }

    static public ListNode removeNthFromEnd(ListNode head, int n) {
        int size = 1, target = n + 2;
        ListNode p1 = head, p2 = head;

        for (; size < target && p1 != null; size++) {
            p1 = p1.next;
        }

        size--;

        if (p1 != null) {
            while (p1 != null) {
                p2 = p2.next;
                p1 = p1.next;
            }
            p2.next = p2.next.next;
        } else if (n == size) {
            head = head.next;
        } else {
            head.next = head.next.next;
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode l1 = toList(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        ListNode l2 = toList(new int[]{1});
        ListNode l3 = toList(new int[]{1, 2});

        System.out.println(removeNthFromEnd(l3, 2));
    }

    /**
     * https://leetcode.com/problems/remove-nth-node-from-end-of-list/ Given a linked list, remove
     * the n-th node from the end of list and return its head.
     * <p>
     * Example: Given linked list: 1->2->3->4->5, and n = 2. After removing the second node from the
     * end, the linked list becomes 1->2->3->5.
     * <p>
     * Note: Given n will always be valid.
     * <p>
     * Follow up: Could you do this in one pass?
     */
    private static class ListNode {

        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            if (next != null) {
                return val + " -> " + next;
            } else {
                return "" + val;
            }
        }
    }
}
