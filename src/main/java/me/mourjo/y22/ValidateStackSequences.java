package me.mourjo.y22;
/*
https://leetcode.com/problems/validate-stack-sequences/
Given two sequences pushed and popped with distinct values, return true if and only if this
could have been the result of a sequence of push and pop operations on an initially empty stack.

Example 1:
Input: pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
Output: true
Explanation: We might do the following sequence:
push(1), push(2), push(3), push(4), pop() -> 4,
push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1


Example 2:
Input: pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
Output: false
Explanation: 1 cannot be popped before 2.


Note:
0 <= pushed.length == popped.length <= 1000
0 <= pushed[i], popped[i] < 1000
pushed is a permutation of popped.
pushed and popped have distinct values.
 */

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Stack;

public class ValidateStackSequences {

    public static boolean validateStackSequencesONSpace(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        int j = 0;
        for (int value : pushed) {
            stack.push(value);
            while (!stack.isEmpty() && stack.peek() == popped[j]) {
                stack.pop();
                j++;
            }
        }

        while (!stack.isEmpty() && stack.peek() == popped[j]) {
            stack.pop();
            j++;
        }

        return stack.isEmpty() && j == popped.length;
    }

    public static boolean validateStackSequences(int[] pushed, int[] popped) {
        int head = 0, j = 0;
        for (int x : pushed) {
            // everything before x is already processed, so use that space as the stack
            // by swapping and keeping the index of the top of the stack
            pushed[head++] = x;
            while (head > 0 && pushed[head - 1] == popped[j]) {
                head--; // tos
                j++;    // popped index
            }
        }
        return head == 0;
    }

    public static void main(String[] args) {
        assertTrue(validateStackSequences(new int[]{1, 2, 3, 4, 5}, new int[]{4, 5, 3, 2, 1}));
        assertFalse(validateStackSequences(new int[]{1, 2, 3, 4, 5}, new int[]{4, 3, 5, 1, 2}));
        assertTrue(validateStackSequences(new int[]{1, 0}, new int[]{1, 0}));
    }
}
