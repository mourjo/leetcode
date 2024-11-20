package me.mourjo.y22;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
https://leetcode.com/problems/maximum-average-subtree/
Given the root of a binary tree, find the maximum average value of any subtree of that tree.

(A subtree of a tree is any node of that tree plus all its descendants. The average value
of a tree is the sum of its values, divided by the number of nodes.)

Example 1:
5 -> 6,1
Input: [5,6,1]
Output: 6.00000
Explanation:
For the node with value = 5 we have an average of (5 + 6 + 1) / 3 = 4.
For the node with value = 6 we have an average of 6 / 1 = 6.
For the node with value = 1 we have an average of 1 / 1 = 1.
So the answer is 6 which is the maximum.

Note:
The number of nodes in the tree is between 1 and 5000.
Each node will have a value between 0 and 100000.
Answers will be accepted as correct if they are within 10^-5 of the correct answer.
 */
public class MaximumAvgSubtree {

  static double maxavg;

  public static double maximumAverageSubtree(TreeNode root) {
    if (root == null) {
      return 0;
    }
    if (root.left == null && root.right == null) {
      return root.val;
    }
    maxavg = Double.MIN_VALUE;
    dfs(root);
    return maxavg;
  }

  public static int[] dfs(TreeNode root) {

    if (root.left == null && root.right == null) {
      if (root.val > maxavg) {
        maxavg = root.val;
      }
      return new int[]{root.val, 1};
    }
    if (root.left != null && root.right == null) {
      int[] d = dfs(root.left);
      d[0] += root.val;
      d[1]++;
      if (((double) d[0]) / d[1] > maxavg) {
        maxavg = ((double) d[0]) / d[1];
      }
      return d;
    }
    if (root.right != null && root.left == null) {
      int[] d = dfs(root.right);
      d[0] += root.val;
      d[1]++;
      if (((double) d[0]) / d[1] > maxavg) {
        maxavg = ((double) d[0]) / d[1];
      }
      return d;
    }

    int[] dleft = dfs(root.left);
    int[] dright = dfs(root.right);

    dleft[0] += root.val + dright[0];
    dleft[1] += 1 + dright[1];
    if (((double) dleft[0]) / dleft[1] > maxavg) {
      maxavg = ((double) dleft[0]) / dleft[1];
    }
    return dleft;
  }

  public static void main(String[] args) {
    TreeNode root;

    root = new TreeNode(5);
    root.left = new TreeNode(6);
    root.right = new TreeNode(1);
    assertEquals(6d, maximumAverageSubtree(root));

    root = new TreeNode(2);
    root.right = new TreeNode(1);
    assertEquals(1.5d, maximumAverageSubtree(root));

    root = new TreeNode(2);
    root.right = new TreeNode(100);
    assertEquals(100, maximumAverageSubtree(root));

    root = new TreeNode(2);
    root.right = new TreeNode(1);
    root.right.right = new TreeNode(-1);
    assertEquals(2d / 3, maximumAverageSubtree(root));

    root = new TreeNode(10);
    root.right = new TreeNode(10);
    root.left = new TreeNode(10);
    assertEquals(10, maximumAverageSubtree(root));

    root = new TreeNode(10);
    root.right = new TreeNode(11);
    root.left = new TreeNode(9);
    root.left.left = new TreeNode(10);
    assertEquals(11, maximumAverageSubtree(root));
    root.left.left = new TreeNode(-10);
    assertEquals(11, maximumAverageSubtree(root));
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
  }
}
