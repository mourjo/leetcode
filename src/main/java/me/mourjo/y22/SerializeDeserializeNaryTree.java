package me.mourjo.y22;

import java.util.*;
import java.util.stream.Collectors;

/*
Serialization is the process of converting a data structure or object into a sequence of bits so
that it can be stored in a file or memory buffer, or transmitted across a network connection link
to be reconstructed later in the same or another computer environment.

Design an algorithm to serialize and deserialize an N-ary tree. An N-ary tree is a rooted tree in
which each node has no more than N children. There is no restriction on how your serialization/
deserialization algorithm should work. You just need to ensure that an N-ary tree can be serialized
to a string and this string can be deserialized to the original tree structure.

For example, you may serialize the following 3-ary tree

1 -> 3,2,4
3 -> 5,6

as [1 [3[5 6] 2 4]]. Note that this is just an example, you do not necessarily need to follow this
format.

Or you can follow LeetCode's level order traversal serialization format, where each group of
children is separated by the null value.

1 -> 2,3,4,5
3 -> 6,7
4 -> 8
5 -> 9, 10
7 -> 11
8 -> 12
9 -> 13
11 -> 14

For example, the above tree may be serialized as
[1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14].

You do not necessarily need to follow the above suggested formats, there are many more different
formats that work so please be creative and come up with different approaches yourself.

Constraints:
The height of the n-ary tree is less than or equal to 1000
The total number of nodes is between [0, 10^4]
Do not use class member/global/static variables to store states. Your encode and decode algorithms should be stateless.
 */
class Node {

    public int val;
    public List<Node> children;

    public Node() {
    }

    public Node(int val) {
        this.val = val;
    }

    public Node(int val, List<Node> children) {
        this.val = val;
        this.children = children;
    }

    @Override
    public String toString() {
        String c = children == null ? "" :
                String.join(",", children.stream().map(Object::toString).collect(Collectors.toList()));
        return "{ val = " + val
                + ", children = [" + c + "]}";
    }
}

class Codec2 {

    private void appendIntBytes(StringBuilder sb, int x) {
        final int mask = 0xff; // last byte 1s all else 0s

        // int has 4 bytes
        // lowest byte first
        sb.append((char) (x & mask));
        sb.append((char) ((x >> 8) & mask));
        sb.append((char) ((x >> 8 >> 8) & mask));
        sb.append((char) ((x >> 8 >> 8 >> 8) & mask));
    }

    private List<Integer> readIntBytes(String s) {
        final int mask = 0xff; // last byte 1s all else 0s
        ArrayList<Integer> list = new ArrayList<>();
        char[] chars = s.toCharArray();
        int tmp = 0;

        for (int i = 0; i < chars.length; i++) {
            switch (i % 4) {
                case 0:
                    tmp = (((int) chars[i]) & mask);
                    break;

                case 1:
                    tmp |= (((int) chars[i]) << 8) & mask;
                    break;

                case 2:
                    tmp |= (((int) chars[i]) << 8 << 8) & mask;
                    break;

                case 3:
                    tmp |= (((int) chars[i]) << 8 << 8 << 8) & mask;
                    list.add(tmp);
                    tmp = 0;
                    break;
            }
        }
        return list;
    }

    // Encodes a tree to a single string.
    public String serialize(Node root) {

        // node_val_1, num_children, child_1, child_2
        // node_val_2, num_children, child_1, child_2

        if (root == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (root.children == null) {
            appendIntBytes(sb, root.val);
            appendIntBytes(sb, 0);
            return sb.toString();
        }

        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            Node node = q.remove();
            if (node.children != null) {
                appendIntBytes(sb, node.val);
                appendIntBytes(sb, node.children.size());
                for (int i = 0; i < node.children.size(); i++) {
                    appendIntBytes(sb, node.children.get(i).val);
                    q.add(node.children.get(i));
                }
            } else {
                appendIntBytes(sb, node.val);
                appendIntBytes(sb, 0);
            }
        }

        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public Node deserialize(String data) {
        if (data == null || "".equals(data)) {
            return null;
        }

        List<Integer> nums = readIntBytes(data);
        int i = 0;
        Node root = null, r = null;
        Queue<Node> processing = new LinkedList<>();

        while (i < nums.size()) {
            int node = nums.get(i);
            if (root == null) {
                root = new Node(node);
                r = root;
            } else {
                r = processing.remove();
            }

            int numChildren = nums.get(i + 1);
            if (numChildren > 0) {
                List<Node> children = new ArrayList<>(numChildren);
                for (int j = 1; j <= numChildren; j++) {
                    Node child = new Node(nums.get(i + 1 + j));
                    children.add(child);
                    processing.add(child);
                }
                r.children = children;
            }

            i += numChildren + 2;
        }
        return root;
    }
}

public class SerializeDeserializeNaryTree {


    public static void main(String[] args) {
        Codec2 c = new Codec2();

        Node t = new Node(1);
        System.out.println(c.deserialize(c.serialize(t)));
        t.children = Arrays.asList(new Node(3), new Node(2), new Node(4));
        t.children.get(0).children = Arrays.asList(new Node(5), new Node(6));
        System.out.println(c.deserialize(c.serialize(t)));

        // [1,null,3,2,4,null,5,6]
        t = new Node(1);
        t.children = Arrays.asList(new Node(2), new Node(3), new Node(4), new Node(5));
        t.children.get(1).children = Arrays.asList(new Node(6), new Node(7));
        t.children.get(2).children = List.of(new Node(8));
        t.children.get(2).children.get(0).children = List.of(new Node(12));
        t.children.get(3).children = Arrays.asList(new Node(9), new Node(10));
        t.children.get(3).children.get(0).children = List.of(new Node(13));
        t.children.get(1).children.get(1).children = List.of(new Node(11));
        t.children.get(1).children.get(1).children.get(0).children = List.of(new Node(14));
        System.out.println(c.deserialize(c.serialize(t)));

        t = new Node(1);
        t.children = Arrays.asList(new Node(2), new Node(2), new Node(2));
        t.children.get(0).children = List.of(new Node(3));
        t.children.get(1).children = List.of(new Node(3));
        t.children.get(2).children = List.of(new Node(3));
        t.children.get(0).children.get(0).children = Arrays.asList(new Node(4), new Node(5));
        t.children.get(1).children.get(0).children = Arrays.asList(new Node(6), new Node(7));
        t.children.get(2).children.get(0).children = Arrays.asList(new Node(8), new Node(9));

        System.out.println(c.deserialize(c.serialize(t)));
    }
}
