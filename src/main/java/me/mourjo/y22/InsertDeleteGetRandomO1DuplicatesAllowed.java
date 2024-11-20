package me.mourjo.y22;
/*
https://leetcode.com/problems/insert-delete-getrandom-o1-duplicates-allowed/
Design a data structure that supports all following operations in average O(1) time.

Note: Duplicate elements are allowed.
insert(val): Inserts an item val to the collection.
remove(val): Removes an item val from the collection if present.
getRandom: Returns a random element from current collection of elements.
The probability of each element being returned is linearly related to the
number of same value the collection contains.

Example:

// Init an empty collection.
RandomizedCollection collection = new RandomizedCollection();

// Inserts 1 to the collection. Returns true as the collection did not contain 1.
collection.insert(1);

// Inserts another 1 to the collection. Returns false as the collection contained 1. Collection now contains [1,1].
collection.insert(1);

// Inserts 2 to the collection, returns true. Collection now contains [1,1,2].
collection.insert(2);

// getRandom should return 1 with the probability 2/3, and returns 2 with the probability 1/3.
collection.getRandom();

// Removes 1 from the collection, returns true. Collection now contains [1,2].
collection.remove(1);

// getRandom should return 1 and 2 both equally likely.
collection.getRandom();

 * Your RandomizedCollection object will be instantiated and called as such:
 * RandomizedCollection obj = new RandomizedCollection();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();

 */


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import org.junit.jupiter.api.Test;

class RandomizedCollection {

  HashMap<Integer, HashSet<Integer>> map;
  ArrayList<Integer> list;
  Random r;
  int c;

  /**
   * Initialize your data structure here.
   */
  public RandomizedCollection() {
    r = new Random();
    map = new HashMap<>();
    list = new ArrayList<>();
    c = 0;
  }

  /**
   * Inserts a value to the collection. Returns true if the collection did not already contain the
   * specified element.
   */
  public boolean insert(int val) {

    if (c < list.size()) {
      list.set(c, val);
    } else {
      list.add(val);
    }

    HashSet<Integer> positions = map.get(val);
    if (positions != null) {
      positions.add(c++);
      return false;
    }
    positions = new HashSet<>();
    positions.add(c++);
    map.put(val, positions);
    return true;
  }

  /**
   * Removes a value from the collection. Returns true if the collection contained the specified
   * element.
   */
  public boolean remove(int val) {
    HashSet<Integer> positions = map.get(val);
    if (positions != null) {
      int idxToDelete = positions.iterator().next();
      positions.remove(idxToDelete);

      if (positions.isEmpty()) {
        map.remove(val);
      }

      if (c - 1 != idxToDelete) {
        // swap if the element being removed is not the last in the list
        int movedElem = list.get(c - 1);
        list.set(idxToDelete, movedElem);
        HashSet<Integer> movedElemPositions = map.get(movedElem);
        movedElemPositions.remove(c - 1);
        movedElemPositions.add(idxToDelete);
      }
      c--;
      return true;
    }
    return false;
  }

  /**
   * Get a random element from the collection.
   */
  public Integer getRandom() {
    if (c == 0) {
      return null;
    }
    int idx = r.nextInt(c);
    return list.get(idx);
  }

}


public class InsertDeleteGetRandomO1DuplicatesAllowed {

  @Test
  public static void main(String[] args) {
    RandomizedCollection r = new RandomizedCollection();
    assertTrue(r.insert(10));
    assertFalse(r.insert(10));
    assertTrue(r.insert(100));
    int x = r.getRandom();
    assertTrue(x == 10 || x == 100);
    assertTrue(r.remove(10));
    assertTrue(r.remove(10));
    assertEquals(100, r.getRandom());
    assertEquals(100, r.getRandom());
    assertTrue(r.remove(100));
    assertNull(r.getRandom());

    r = new RandomizedCollection();
    for (int i = 0; i < 100; i++) {
      if (i == 0) {
        assertTrue(r.insert(100));
      } else {
        assertFalse(r.insert(100));
      }
    }

    Integer y = r.getRandom();
    while (y != null) {
      assertEquals(100, y);
      assertTrue(r.remove(100));
      y = r.getRandom();
    }

    // ["RandomizedCollection","insert","insert","remove","insert","remove","getRandom"]
    //[[],[0],[1],[0],[2],[1],[]]
    r = new RandomizedCollection();
    assertTrue(r.insert(0));
    assertTrue(r.insert(1));
    assertTrue(r.remove(0));
    assertTrue(r.insert(2));
    assertTrue(r.remove(1));
    assertEquals(2, r.getRandom());

  }
}
