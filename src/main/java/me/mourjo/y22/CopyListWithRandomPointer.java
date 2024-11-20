package me.mourjo.y22;

/*
https://leetcode.com/problems/copy-list-with-random-pointer/

A linked list is given such that each node contains an additional random pointer which could point
to any node in the list or null.

Return a deep copy of the list.

The Linked List is represented in the input/output as a list of n nodes. Each node is represented
as a pair of [val, random_index] where:

val: an integer representing Node.val
random_index: the index of the node (range from 0 to n-1) where random pointer points to, or null
if it does not point to any node.


Example 1:
Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]

Example 2:
Input: head = [[1,1],[2,1]]
Output: [[1,1],[2,1]]

Example 3:
Input: head = [[3,null],[3,0],[3,null]]
Output: [[3,null],[3,0],[3,null]]

Example 4:
Input: head = []
Output: []
Explanation: Given linked list is empty (null pointer), so return null.

Constraints:
-10000 <= Node.val <= 10000
Node.random is null or pointing to a node in the linked list.
Number of Nodes will not exceed 1000.
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;

public class CopyListWithRandomPointer {

  public static Node copyRandomList(final Node head) {
    if (head == null) {
      return head;
    }
    Node h = head;
    Node newhead = null;
    Node ph = null;
    HashMap<Node, Node> map = new HashMap<>(1000);

    while (h != null) {
      if (ph != null) {
        ph.next = new Node(h.val);
        ph = ph.next;
      } else {
        ph = new Node(h.val);
      }
      if (newhead == null) {
        newhead = ph;
      }
      map.put(h, ph);
      h = h.next;

    }

    h = head;
    ph = newhead;
    while (h != null) {
      if (h.random != null && map.containsKey(h.random)) {
        ph.random = map.get(h.random);
      }

      h = h.next;
      ph = ph.next;
    }
    return newhead;
  }

  public static void main(String[] args) {
    Node head;
    int hashcode;
    head = new Node(8);

    head.next = new Node(9);
    head.random = head.next;
    assertEquals(head.toString(), copyRandomList(head).toString());
    Node cp = copyRandomList(head);
    while (head != null) {
      assertNotEquals(cp, head);
      assertEquals(head.val, cp.val);
      if (head.random != null) {
        assertNotEquals(head.random, cp.random);
      } else {
        assertNull(cp.random);
      }
      if (head.random != null) {
        assertNotEquals(head.random.val, cp.random.random);
      }
      cp = cp.next;
      head = head.next;
    }

    head = new Node(7);
    head.next = new Node(13);
    head.next.next = new Node(11);
    head.next.next.next = new Node(10);
    head.next.next.next.next = new Node(1);
    head.next.random = head;
    head.next.next.random = head.next.next.next.next;
    head.next.next.next.random = head.next.next;
    head.next.next.next.next.random = head;

    hashcode = head.next.random.hashCode();
    copyRandomList(head);
    assertEquals(head.next.random.val, copyRandomList(head).next.random.val);
    copyRandomList(head);
    cp = copyRandomList(head);
    assertEquals(hashcode, head.next.random.hashCode());
    while (head != null) {
      assertNotEquals(cp, head);
      assertEquals(head.val, cp.val);
      if (head.random != null) {
        assertNotEquals(head.random, cp.random);
      } else {
        assertNull(cp.random);
      }
      if (head.random != null) {
        assertNotEquals(head.random.val, cp.random.random);
      }
      cp = cp.next;
      head = head.next;
    }
  }

  static class Node {

    int val;
    Node next;
    Node random;

    public Node(int val) {
      this.val = val;
      this.next = null;
      this.random = null;
    }

    @Override
    public String toString() {
      return "{ val=" + val + ", next=" + next + ", rand=" + (random == null ? "" : random.val)
          + " }";
    }
  }
}
