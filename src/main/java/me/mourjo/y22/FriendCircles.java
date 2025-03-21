package me.mourjo.y22;


/*
https://leetcode.com/problems/friend-circles/
There are N students in a class. Some of them are friends, while some are not.
Their friendship is transitive in nature. For example, if A is a direct friend of B,
and B is a direct friend of C, then A is an indirect friend of C. And we defined a friend
circle is a group of students who are direct or indirect friends.

Given a N*N matrix M representing the friend relationship between students in the class.
If M[i][j] = 1, then the ith and jth students are direct friends with each other, otherwise
not. And you have to output the total number of friend circles among all the students.

Input:
[[1,1,0],
 [1,1,0],
 [0,0,1]]
Output: 2
Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
The 2nd student himself is in a friend circle. So return 2.


Input:
[[1,1,0],
 [1,1,1],
 [0,1,1]]
Output: 1
Explanation:The 0th and 1st students are direct friends, the 1st and 2nd students are
direct friends, so the 0th and 2nd students are indirect friends. All of them are in
the same friend circle, so return 1.

Note:
N is in range [1,200].
M[i][i] = 1 for all students.
If M[i][j] = 1, then M[j][i] = 1.
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.HashSet;
import java.util.Stack;

public class FriendCircles {

    public static int findCircleNumOld(int[][] M) {
        HashSet<Integer>[] friends = new HashSet[M.length];

        for (int i = 0; i < friends.length; i++) {
            friends[i] = new HashSet<>();
            friends[i].add(i);
        }

        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[i].length; j++) {
                if (i == j || friends[i] == friends[j]) {
                    continue;
                }

                if (friends[i].size() == M[i].length || friends[j].size() == M[i].length) {
                    return 1;
                }

                if (M[i][j] == 1 || friends[i].contains(j) || friends[j].contains(i)) {
                    // merge
                    friends[i].addAll(friends[j]);
                    friends[j] = friends[i];
                }
            }
        }

        HashSet<HashSet<Integer>> circles = new HashSet<>();
        Collections.addAll(circles, friends);
        return circles.size();
    }

    static void traverse(int[][] M, int me, boolean[] seen) {
        Stack<Integer> friendships = new Stack<>();

        seen[me] = true;

        for (int potentialFriend = 0; potentialFriend < M[me].length; potentialFriend++) {
            if (me == potentialFriend) {
                continue;
            }

            if (M[me][potentialFriend] == 1 && !seen[potentialFriend]) {
                seen[potentialFriend] = true;
                friendships.push(potentialFriend);
            }
        }

        while (!friendships.isEmpty()) {
            int friend = friendships.pop();

            for (int transitiveFriend = 0; transitiveFriend < M[friend].length;
                transitiveFriend++) {
                if (transitiveFriend == friend) {
                    continue;
                }

                if (M[friend][transitiveFriend] == 1 && !seen[transitiveFriend]) {
                    seen[transitiveFriend] = true;
                    friendships.push(transitiveFriend);
                }
            }
        }
    }

    public static int findCircleNumNonRec(int[][] M) {
        boolean[] seen = new boolean[M.length];
        int circles = 0;
        for (int i = 0; i < M.length; i++) {
            if (!seen[i]) {
                circles++;
                traverse(M, i, seen);
            }
        }
        return circles;
    }

    public static void walk(int[][] M, int i, boolean[] seen) {
        seen[i] = true;

        for (int j = 0; j < M[i].length; j++) {
            if (i != j && M[i][j] == 1 && !seen[j]) {
                walk(M, j, seen);
            }
        }
    }


    public static int findCircleNum(int[][] M) {
        boolean[] seen = new boolean[M.length];
        int circles = 0;
        for (int i = 0; i < M.length; i++) {
            if (!seen[i]) {
                circles++;
                walk(M, i, seen);
            }
        }
        return circles;
    }

    public static void main(String[] args) {
        assertEquals(2,
            findCircleNum(new int[][]
                {
                    {1, 1, 0},
                    {1, 1, 0},
                    {0, 0, 1}
                }));

        assertEquals(1,
            findCircleNum(new int[][]
                {
                    {1, 1, 0},
                    {1, 1, 1},
                    {0, 1, 1}
                }));

        assertEquals(1, findCircleNum(new int[][]{

            {1, 0, 1, 0, 0},
            {0, 1, 0, 1, 1},
            {1, 0, 1, 0, 1},
            {0, 1, 0, 1, 0},
            {0, 1, 1, 0, 1}
        }));

        assertEquals(2, findCircleNum(new int[][]{

            {1, 0, 1, 1, 0},
            {0, 1, 0, 0, 1},
            {1, 0, 1, 0, 0},
            {1, 0, 0, 1, 0},
            {0, 1, 0, 0, 1}
        }));

        assertEquals(0, findCircleNum(new int[][]{}));

        assertEquals(4, findCircleNum(new int[][]{
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
        }));

        assertEquals(3, findCircleNum(
            new int[][]{
                {1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1},
                {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1}
            }));
    }
}
