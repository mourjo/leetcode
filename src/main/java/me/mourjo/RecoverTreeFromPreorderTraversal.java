package me.mourjo;
/*
https://leetcode.com/problems/recover-a-tree-from-preorder-traversal/
We run a preorder depth first search on the root of a binary tree.

At each node in this traversal, we output D dashes (where D is the depth of this node), then we
output the value of this node.  (If the depth of a node is D, the depth of its immediate child
is D+1.  The depth of the root node is 0.)

If a node has only one child, that child is guaranteed to be the left child.

Given the output S of this traversal, recover the tree and return its root.


Example 1:
1 -> 2,5
2 -> 3,4
5 -> 6,7
Input: "1-2--3--4-5--6--7"
Output: [1,2,5,3,4,6,7]

Example 2:
1 -> 2,5
2 -> 3
5 -> 6
3 -> 4
6 -> 7
Input: "1-2--3---4-5--6---7"
Output: [1,2,5,3,null,6,null,4,null,7]

Example 3:
1 -> 401
401 -> 349,88
349 -> 90
Input: "1-401--349---90--88"
Output: [1,401,null,349,88,90]


Note:

The number of nodes in the original tree is between 1 and 1000.
Each node will have a value between 1 and 10^9. */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Stack;

public class RecoverTreeFromPreorderTraversal {

  public static TreeNode recoverFromPreorder(String s) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode root = null, ptr = null;

    for (int i = 0; i < s.length(); i++) {
      int depth = 0;

      while (i < s.length() && s.charAt(i) == '-') {
        depth++;
        i++;
      }

      int d = 0;
      while (i < s.length() && s.charAt(i) != '-') {
        d = d * 10 + (s.charAt(i) - '0');
        i++;
      }

      if (root == null) {
        root = new TreeNode(d);
        stack.push(root);
      } else {
        while (depth < stack.size() - 1) {
          stack.pop();
        }
        ptr = stack.peek();
        TreeNode newNode = new TreeNode(d);
        stack.push(newNode);
        if (ptr.left == null) {
          ptr.left = newNode;
        } else {
          ptr.right = newNode;
        }
      }
    }
    return root;
  }

  public static void main(String[] args) {
    TreeNode node = recoverFromPreorder("1");
    assertEquals(node.val, 1);
    assertNull(node.left);
    assertNull(node.right);

    node = recoverFromPreorder("1-2--3");
    assertEquals(node.val, 1);
    assertEquals(node.left.val, 2);
    assertEquals(node.left.left.val, 3);
    assertNull(node.left.right);
    assertNull(node.left.left.right);
    assertNull(node.left.left.left);
    assertNull(node.right);

    node = recoverFromPreorder("1-2--3-4");
    assertEquals(node.val, 1);
    assertEquals(node.left.val, 2);
    assertEquals(node.left.left.val, 3);
    assertEquals(node.right.val, 4);
    assertNull(node.right.left);
    assertNull(node.right.right);
    assertNull(node.left.left.right);
    assertNull(node.left.left.left);

    node = recoverFromPreorder("1-2--3--4-5--6--7");
    assertEquals(node.val, 1);
    assertEquals(node.left.val, 2);
    assertEquals(node.left.left.val, 3);
    assertEquals(node.left.right.val, 4);
    assertEquals(node.right.val, 5);
    assertEquals(node.right.left.val, 6);
    assertEquals(node.right.right.val, 7);

    node = recoverFromPreorder("1-2--3---4-5--6---7");
    assertEquals(node.val, 1);
    assertEquals(node.left.val, 2);
    assertEquals(node.left.left.val, 3);
    assertEquals(node.left.left.left.val, 4);
    assertEquals(node.right.val, 5);
    assertEquals(node.right.left.val, 6);
    assertEquals(node.right.left.left.val, 7);

    node = recoverFromPreorder("1-401--349---90--88");
    assertEquals(node.val, 1);
    assertEquals(node.left.val, 401);
    assertEquals(node.left.left.val, 349);
    assertEquals(node.left.left.left.val, 90);
    assertEquals(node.left.right.val, 88);

  }

  public static class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
      this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }

    @Override
    public String toString() {
      return "" + val;
    }
  }
}
