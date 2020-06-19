package me.mourjo;

/*
https://leetcode.com/problems/maximum-level-sum-of-a-binary-tree/

Given the root of a binary tree, the level of its root is 1, the level of its children is 2, and so on.

Return the smallest level X such that the sum of all the values of nodes at level X is maximal.

Input: [1,7,0,7,-8,null,null]
Output: 2
Explanation:
Level 1 sum = 1.
Level 2 sum = 7 + 0 = 7.
Level 3 sum = 7 + -8 = -1.
So we return the level with the maximum sum which is level 2.
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MaximumLevelSumBinaryTree {

  public static int maxLevelSumBFS(TreeNode root) {
    if (null == root) {
      return 0;
    }
    Queue<TreeNode> valQ = new LinkedList<>();
    valQ.add(root);

    int level = 0, maxLevel = -1, maxLevelSum = Integer.MIN_VALUE;

    while (!valQ.isEmpty()) {

      // at this point all elements of are of same level
      int currLevelSum = 0;
      int s = valQ.size();
      for (int i = 0; i < s; i++) {
        TreeNode p = valQ.remove();
        currLevelSum += p.val;
        if (p.left != null) {
          valQ.add(p.left);
        }

        if (p.right != null) {
          valQ.add(p.right);
        }
      }
      if (currLevelSum > maxLevelSum) {
        maxLevel = level;
        maxLevelSum = currLevelSum;
      }
      level++;

    }
    return maxLevel + 1;
  }

  public static int maxLevelSum(TreeNode root) {
    List<Integer> levelSum = new ArrayList<>(10000);

    int maxLevelSum = Integer.MIN_VALUE, maxLevel = -1;
    dfs(root, 0, levelSum);

    for (int i = 0; i < levelSum.size(); i++) {
      if (maxLevelSum < levelSum.get(i)) {
        maxLevel = i;
        maxLevelSum = levelSum.get(i);
      }
    }
    return maxLevel + 1;
  }

  public static void dfs(TreeNode t, int level, List<Integer> levelSum) {
    if (null == t) {
      return;
    }

    // levels increase incrementally by 1 (new level = largest existing level + 1)
    if (levelSum.size() == level) {
      levelSum.add(t.val);
    } else {
      levelSum.set(level, levelSum.get(level) + t.val);
    }

    dfs(t.left, level + 1, levelSum);
    dfs(t.right, level + 1, levelSum);
  }

  public static void main(String[] args) {
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(7);
    root.right = new TreeNode(0);
    root.left.left = new TreeNode(7);
    root.left.right = new TreeNode(-8);

    assertEquals(2, maxLevelSum(root));

    root.left.right.right = new TreeNode(100);
    assertEquals(4, maxLevelSum(root));

    root.right = new TreeNode(1000);
    assertEquals(2, maxLevelSum(root));

    root.val = 1000;
    assertEquals(2, maxLevelSum(root));

    root.val = 1100;
    assertEquals(1, maxLevelSum(root));

    root.right.left = new TreeNode(1000000);
    root.right.left.left = new TreeNode(1000000 / 2);
    root.right.left.right = new TreeNode(1000000 / 2);
    assertEquals(4, maxLevelSum(root));

    //assertEquals(0, maxLevelSum(null));
    assertEquals(1, maxLevelSum(new TreeNode(-1000)));

    root = new TreeNode(100);
    root.right = new TreeNode(5);
    root.left = new TreeNode(5);
    root.right.left = new TreeNode(25);
    root.right.right = new TreeNode(25);
    root.left.right = new TreeNode(25);
    root.left.left = new TreeNode(25);
    assertEquals(1, maxLevelSum(root));

    root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(3);
    root.left.left = new TreeNode(7);
    root.left.right = new TreeNode(8);
    root.right.right = new TreeNode(9);
    assertEquals(3, maxLevelSum(root));

    root.right.right.left = new TreeNode(10000);
    assertEquals(4, maxLevelSum(root));

    root.left.left.left = new TreeNode(-10000);
    assertEquals(3, maxLevelSum(root));

    root.right.right.left.val = -10000;
    root.left.left.left.val = 10000;
    assertEquals(3, maxLevelSum(root));

    root.right.val = 1000;
    assertEquals(2, maxLevelSum(root));

    root.val = 10000000;
    assertEquals(1, maxLevelSum(root));
  }

  static class TreeNode {

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

  static class Tuple {

    public int level, val;

    Tuple(int v, int l) {
      level = l;
      val = v;
    }
  }
}
