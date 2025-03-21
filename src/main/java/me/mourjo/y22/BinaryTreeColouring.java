package me.mourjo.y22;
/*
https://leetcode.com/problems/binary-tree-coloring-game/
Two players play a turn based game on a binary tree.  We are given the root of this binary tree,
and the number of nodes n in the tree.  n is odd, and each node has a distinct value from 1 to n.

Initially, the first player names a value x with 1 <= x <= n, and the second player names a value
y with 1 <= y <= n and y != x.  The first player colors the node with value x red, and the second
player colors the node with value y blue.

Then, the players take turns starting with the first player.  In each turn, that player chooses a
node of their color (red if player 1, blue if player 2) and colors an uncolored neighbor of the
chosen node (either the left child, right child, or parent of the chosen node.)

If (and only if) a player cannot choose such a node in this way, they must pass their turn.  If
both players pass their turn, the game ends, and the winner is the player that colored more nodes.

You are the second player.  If it is possible to choose such a y to ensure you win the game,
return true.  If it is not possible, return false.


Input: root = [1,2,3,4,5,6,7,8,9,10,11], n = 11, x = 3
Output: true
Explanation: The second player can choose the node with value 2.

Constraints:
root is the root of a binary tree with n nodes and distinct node values from 1 to n.
n is odd.
1 <= x <= n <= 100
  */

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinaryTreeColouring {

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

        public static boolean btreeGameWinningMove(TreeNode root, int n, int x) {
            TreeNode parent = findParent(root, null, x);
            if (parent == null) {
                // root
                int leftsize = countSubtree(root.left);
                int rightsize = countSubtree(root.right);
                return leftsize != rightsize;
            } else {
                TreeNode xnode = null;

                if (parent.left != null && parent.left.val == x) {
                    xnode = parent.left;
                } else if (parent.right != null && parent.right.val == x) {
                    xnode = parent.right;
                }

                int xleftsize = countSubtree(xnode.left);
                if (xleftsize > (n - xleftsize)) {
                    return true;
                }

                int xrightsize = countSubtree(xnode.right);
                if (xrightsize > (n - xrightsize)) {
                    return true;
                }

                return (xleftsize + xrightsize + 1 < (n - (xleftsize + xrightsize + 1)));
            }
        }

        public static int countSubtree(TreeNode me) {
            if (me == null) {
                return 0;
            }
            return 1 + countSubtree(me.left) + countSubtree(me.right);
        }

        public static TreeNode findParent(TreeNode me, TreeNode parent, int x) {
            if (me == null) {
                return null;
            }
            if (me.val == x) {
                return parent;
            }
            TreeNode p = findParent(me.left, me, x);
            if (p != null) {
                return p;
            }
            return findParent(me.right, me, x);
        }

        public static void main(String[] args) {
            TreeNode root = new TreeNode(1);
            root.left = new TreeNode(2);
            root.right = new TreeNode(3);
            root.left.left = new TreeNode(4);
            root.left.right = new TreeNode(5);
            root.right.left = new TreeNode(6);
            root.right.right = new TreeNode(7);
            root.left.left.left = new TreeNode(8);
            root.left.left.right = new TreeNode(9);
            root.left.right.left = new TreeNode(10);
            root.left.right.right = new TreeNode(11);

            assertTrue(btreeGameWinningMove(root, 11, 3));
            assertFalse(btreeGameWinningMove(root, 11, 2));
            assertTrue(btreeGameWinningMove(root, 11, 4));
            assertTrue(btreeGameWinningMove(root, 11, 1));
            assertTrue(btreeGameWinningMove(root, 11, 11));
            assertTrue(btreeGameWinningMove(root, 11, 7));

            root = new TreeNode(1);
            assertFalse(btreeGameWinningMove(root, 1, 1));

        }


    }
}
