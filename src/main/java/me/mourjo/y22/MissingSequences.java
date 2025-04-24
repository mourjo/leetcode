package me.mourjo.y22;

/*
Given a sorted integer array nums, where the range of elements are in the inclusive range [lower, upper], return its missing ranges.

Example:

Input: nums = [0, 1, 3, 50, 75], lower = 0 and upper = 99,
Output: ["2", "4->49", "51->74", "76->99"]
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MissingSequences {

    public static List<String> findMissingRanges(int[] nums, int lower, int upper) {
        if (nums.length == 0) {
            if (lower == upper) {
                return List.of("" + lower);
            }
            return List.of(lower + "->" + upper);
        }
        ArrayList<String> result = new ArrayList<>();

        if (nums[0] != lower) {
            if (nums[0] - lower == 1) {
                result.add("" + lower);
            } else {
                result.add(lower + "->" + (nums[0] - 1));
            }
        }

        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i + 1] == nums[i]) {
                continue;
            }

            if (nums[i + 1] != nums[i] + 1) {
                if (nums[i + 1] - nums[i] == 2) {
                    result.add("" + (nums[i] + 1));
                } else {
                    result.add((nums[i] + 1) + "->" + (nums[i + 1] - 1));
                }
            }
        }

        if (nums[nums.length - 1] != upper) {
            if (upper - nums[nums.length - 1] == 1) {
                result.add("" + upper);
            } else {
                result.add((nums[nums.length - 1] + 1) + "->" + upper);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        assertEquals(Arrays.asList("2", "4->49", "51->74", "76->99"),
                findMissingRanges(new int[]{0, 1, 3, 50, 75}, 0, 99));

        assertEquals(List.of("0->99"),
                findMissingRanges(new int[]{}, 0, 99));

        assertEquals(Arrays.asList("0->49", "51->99"),
                findMissingRanges(new int[]{50}, 0, 99));

        assertEquals(Arrays.asList("0->49", "52->99"),
                findMissingRanges(new int[]{50, 51}, 0, 99));

        assertEquals(Arrays.asList("0", "2->49", "51->98"),
                findMissingRanges(new int[]{1, 50, 99}, 0, 99));

        assertEquals(List.of("0"),
                findMissingRanges(new int[]{1}, 0, 1));

        assertEquals(List.of("0"),
                findMissingRanges(new int[]{1, 2, 3, 4}, 0, 4));

        assertEquals(List.of(),
                findMissingRanges(new int[]{0, 1, 2, 3, 4}, 0, 4));

        assertEquals(List.of("1"),
                findMissingRanges(new int[]{}, 1, 1));

        assertEquals(List.of(),
                findMissingRanges(new int[]{1}, 1, 1));

        assertEquals(List.of(),
                findMissingRanges(new int[]{1, 1, 1}, 1, 1));
    }
}
