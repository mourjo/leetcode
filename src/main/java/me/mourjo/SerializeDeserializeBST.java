package me.mourjo;

/*
Serialization is the process of converting a data structure or object into a sequence of bits
so that it can be stored in a file or memory buffer, or transmitted across a network connection
link to be reconstructed later in the same or another computer environment.

Design an algorithm to serialize and deserialize a binary search tree. There is no restriction
on how your serialization/deserialization algorithm should work. You just need to ensure that
a binary search tree can be serialized to a string and this string can be deserialized to the
original tree structure.

The encoded string should be as compact as possible.

Note: Do not use class member/global/static variables to store states.
Your serialize and deserialize algorithms should be stateless.
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.Queue;

// Definition for a binary tree node.
class TreeNode {

  int val;
  TreeNode left;
  TreeNode right;

  TreeNode(int x) {
    val = x;
  }
}

class Codec {
  private static final int mask = 0xff;

  private static void appendByteInt(StringBuilder sb, int x) {
    // highest byte first
    sb.append((char) ((x >> 8 >> 8 >> 8) & mask));
    sb.append((char) ((x >> 8 >> 8) & mask));
    sb.append((char) ((x >> 8) & mask));
    sb.append((char) (x & mask));
  }

  private static int[] readByteInts(String x) {
    int[] res = new int[x.length() / 4];
    int count = 0, t = 0;

    for (int i = 0; i < x.length(); i++) {
      int c = x.charAt(i);
      if (i % 4 == 0) {
        t = c;
        continue;
      }
      t = (t << 8) | c;
      if (i % 4 == 3) {
        res[count++] = t;
        t = 0;
      }
    }
    return res;
  }

  // Encodes a tree to a single string.
  public String serialize(TreeNode root) {
    if (root == null) {
      return "";
    }
    // level order traversal
    StringBuilder s = new StringBuilder();
    Queue<TreeNode> q = new LinkedList<>();
    q.add(root);
    while (!q.isEmpty()) {
      TreeNode r = q.remove();
      appendByteInt(s, r.val);
      if (r.left != null) {
        q.add(r.left);
      }
      if (r.right != null) {
        q.add(r.right);
      }
    }
    return s.toString();
  }

  private void insert(TreeNode root, int val) {
    if (val <= root.val) {
      if (root.left == null) {
        root.left = new TreeNode(val);
      } else {
        insert(root.left, val);
      }
    } else {
      if (root.right == null) {
        root.right = new TreeNode(val);
      } else {
        insert(root.right, val);
      }
    }
  }

  // Decodes your encoded data to tree.
  public TreeNode deserialize(String data) {
    if ("".equals(data)) {
      return null;
    }
    int[] d = readByteInts(data);

    TreeNode root = null;
    for (int val : d) {
      if (root == null) {
        root = new TreeNode(val);
      } else {
        insert(root, val);
      }
    }
    return root;
  }
}

public class SerializeDeserializeBST {

  public static void main(String[] args) {
    Codec codec = new Codec();
    TreeNode root = new TreeNode(10);
    root.left = new TreeNode(1);
    root.right = new TreeNode(100);
    root.left.right = new TreeNode(5);
    root.right.left = new TreeNode(20);
    root.left.right.left = new TreeNode(3);
    root.left.right.right = new TreeNode(7);
    root.left.right.left.right = new TreeNode(4);

    TreeNode root2 = codec.deserialize(codec.serialize(root));
    assertTrue(checkTrees(root, root2, "root"));

    root2 = codec.deserialize(codec.serialize(null));
    assertNull(root2);

    root = new TreeNode(1);
    root2 = codec.deserialize(codec.serialize(root));
    assertEquals(1, root2.val);
    assertNull(root2.left);
    assertNull(root2.right);

    root.right = new TreeNode(100);
    root2 = codec.deserialize(codec.serialize(root));
    assertEquals(1, root2.val);
    assertNull(root2.left);
    assertEquals(100, root2.right.val);
    assertNull(root2.right.left);
    assertNull(root2.right.right);

    root.right.right = new TreeNode(1000);
    root2 = codec.deserialize(codec.serialize(root));
    assertEquals(1, root2.val);
    assertNull(root2.left);
    assertEquals(100, root2.right.val);
    assertNull(root2.right.left);
    assertEquals(1000, root2.right.right.val);
    assertNull(root2.right.left);
    assertNull(root2.right.right.left);
  }

  public static boolean checkTrees(TreeNode root, TreeNode rootCopy, String p) {
    if (root.val != rootCopy.val) {
      System.out.println(p);
      return false;
    }

    if (root.left != null) {
      if (rootCopy.left == null) {
        return false;
      }
      if (!checkTrees(root.left, rootCopy.left, ".left")) {
        return false;
      }
    }
    if (root.right != null) {
      if (rootCopy.right == null) {
        return false;
      }
      return checkTrees(root.right, rootCopy.right, ".right");
    }
    return true;
  }
}
