package me.mourjo.y24.misc;

// https://leetcode.com/problems/course-schedule/description/
// There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.
//
// For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
// Return true if you can finish all courses. Otherwise, return false.
//
// Example 1:
// Input: numCourses = 2, prerequisites = [[1,0]]
// Output: true
// Explanation: There are a total of 2 courses to take.
// To take course 1 you should have finished course 0. So it is possible.

import org.junit.jupiter.api.Assertions;

import java.util.*;

///
// Example 2:
// Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
// Output: false
// Explanation: There are a total of 2 courses to take.
// To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.
//
// Constraints:
//
// 1 <= numCourses <= 2000
// 0 <= prerequisites.length <= 5000
// prerequisites[i].length == 2
// 0 <= ai, bi < numCourses
// All the pairs prerequisites[i] are unique.
public class CourseSchedule {

    public static void main(String[] args) {
        var app = new CourseSchedule();
        Assertions.assertFalse(app.canFinish(2, new int[][]{{1, 0}, {0, 1}}));
        Assertions.assertTrue(app.canFinish(2, new int[][]{{1, 0}}));
        Assertions.assertTrue(app.canFinish(2, new int[][]{{1, 0}}));
        Assertions.assertTrue(app.canFinish(2, new int[][]{{0, 1}}));
        Assertions.assertTrue(app.canFinish(5, new int[][]{{1, 4}, {2, 4}, {3, 1}, {3, 2}}));
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> dependencies = new HashMap<>();
        Set<Integer> completed = new HashSet<>();

        for (int node = 0; node < numCourses; node++) {
            dependencies.putIfAbsent(node, new ArrayList<>());
        }

        for (int[] prerequisite : prerequisites) {
            int dependency = prerequisite[0];
            int node = prerequisite[1];
            dependencies.get(node).add(dependency);
        }

        for (int node = 0; node < numCourses; node++) {
            if (!dfs(dependencies, new HashSet<>(), completed, node)) {
                return false;
            }
        }

        return true;
    }

    boolean dfs(Map<Integer, List<Integer>> dependencies, Set<Integer> inProgress,
                Set<Integer> completed, int node) {
        if (completed.contains(node)) {
            return true;
        }

        if (inProgress.contains(node)) {
            return false;
        }

        inProgress.add(node);

        for (int neighbor : dependencies.get(node)) {
            if (!dfs(dependencies, inProgress, completed, neighbor)) {
                return false;
            }
        }

        inProgress.remove(node);
        completed.add(node);
        return true;
    }
}
