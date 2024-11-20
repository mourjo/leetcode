package me.mourjo.y24.arrays;


import java.util.HashSet;
import org.junit.jupiter.api.Assertions;

public class ContainsDuplicates {
// https://leetcode.com/problems/contains-duplicate/

    public static void main(String[] args) {
        var app = new ContainsDuplicates();
        Assertions.assertFalse(app.containsDuplicate(new int[]{1,2,3,4}));
        Assertions.assertTrue(app.containsDuplicate(new int[]{1,2,3,1}));
        Assertions.assertTrue(app.containsDuplicate(new int[]{1,2,3,3,1}));
    }

    public boolean containsDuplicate(int[] nums) {
        var set = new HashSet<Integer>();
        for (int n : nums) {
            if (set.contains(n)) {
                return true;
            }
            set.add(n);
        }

        return false;
    }
}
