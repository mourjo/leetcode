package me.mourjo.y24.random;

import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;

/*

Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.

Implement the LRUCache class:

LRUCache(int capacity) Initialize the LRU cache with positive size capacity.

int get(int key) Return the value of the key if the key exists, otherwise return -1.

void put(int key, int value) Update the value of the key if the key exists.
Otherwise, add the key-value pair to the cache. If the number of keys exceeds the capacity from this
operation, evict the least recently used key.

The functions get and put must each run in O(1) average time complexity.


Example 1:

Input
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
Output
[null, null, null, 1, null, -1, null, -1, 3, 4]

Explanation
LRUCache lRUCache = new LRUCache(2);
lRUCache.put(1, 1); // cache is {1=1}
lRUCache.put(2, 2); // cache is {1=1, 2=2}
lRUCache.get(1);    // return 1
lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
lRUCache.get(2);    // returns -1 (not found)
lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
lRUCache.get(1);    // return -1 (not found)
lRUCache.get(3);    // return 3
lRUCache.get(4);    // return 4
 */
public class LRUCache {


    final Node head;
    final Node tail;
    final Map<Integer, Node> kv;
    final int capacity;

    public LRUCache(int capacity) {
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
        kv = new HashMap<>(capacity);
        this.capacity = capacity;
    }

    public static void main(String[] args) {
        var cache = new LRUCache(2);
        cache.put(1, 1); // cache is {1=1}
        cache.put(2, 2); // cache is {1=1, 2=2}
        Assertions.assertEquals(1, cache.get(1));    // return 1
        cache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
        Assertions.assertEquals(-1, cache.get(2));    // returns -1 (not found)
        cache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
        Assertions.assertEquals(-1, cache.get(1));    // return -1 (not found)
        Assertions.assertEquals(3, cache.get(3));    // return 3
        Assertions.assertEquals(4, cache.get(4));    // return 4

    }

    public void print() {
        StringBuilder sb = new StringBuilder();
        Node p = head.next;
        while (p != null) {
            sb.append(p.key);
            sb.append(",");
            p = p.next;
        }
        System.out.println(sb);
        System.out.println(kv);
        System.out.println();
    }

    public int get(int key) {
        if (kv.containsKey(key)) {
            Node item = kv.get(key);
            markMostPopular(item);
            return item.value;
        }
        return -1;
    }

    public void put(int key, int value) {
        Node item;
        if (kv.containsKey(key)) {
            item = kv.get(key);
            item.value = value;
        } else {
            if (kv.size() == capacity) {
                evict();
            }
            item = new Node(key, value);
        }
        markMostPopular(item);
        kv.put(key, item);
    }

    private void evict() {
        Node nodeToEvict = tail.prev;
        nodeToEvict.prev.next = tail;
        tail.prev = nodeToEvict.prev;
        kv.remove(nodeToEvict.key);
    }

    private void markMostPopular(Node node) {

        // detach from the linked list
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }

        // fix tail if needed
        if (tail.prev == head) {
            // list was empty
            tail.prev = node;
        }

        if (node.next == tail) {
            // most popular one was the least popular before
            node.prev.next = tail;
        }

        // arrange the node's next node
        node.next = head.next;
        node.next.prev = node;

        // fix head always
        node.prev = head;
        head.next = node;
    }

    private static class Node {

        Node prev;
        Node next;
        int key;
        int value;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" + key + "}";
        }
    }
}
