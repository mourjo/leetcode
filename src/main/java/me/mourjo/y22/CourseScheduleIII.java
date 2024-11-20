package me.mourjo.y22;

/*
https://leetcode.com/problems/course-schedule-iii/
There are n different online courses numbered from 1 to n. Each course has some
duration(course length) t and closed on dth day. A course should be taken continuously
for t days and must be finished before or on the dth day. You will start at the 1st day.

Given n online courses represented by pairs (t,d), your task is to find the maximal
number of courses that can be taken.

Example:

Input: [[100, 200], [200, 1300], [1000, 1250], [2000, 3200]]
Output: 3

Explanation:
There're totally 4 courses, but you can take 3 courses at most:
First, take the 1st course, it costs 100 days so you will finish it on the 100th day,
and ready to take the next course on the 101st day.

Second, take the 3rd course, it costs 1000 days so you will finish it on the 1100th day,
and ready to take the next course on the 1101st day.

Third, take the 2nd course, it costs 200 days so you will finish it on the 1300th day.
The 4th course cannot be taken now, since you will finish it on the 3300th day, which
exceeds the closed date.


Note:

The integer 1 <= d, t, n <= 10,000.
You can't take two courses simultaneously.
 */


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class CourseScheduleIII {

  public static int scheduleCourse(int[][] courses) {
    // sort on one dimension (end date)
    Arrays.sort(courses, new Comparator<int[]>() {
      @Override
      public int compare(int[] o1, int[] o2) {
        return o1[1] - o2[1];
      }
    });

    // running max on other dimension (duration)
    PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        return o2 - o1;
      }
    });

    int today = 0;
    for (int[] course : courses) {
      int duration = course[0];
      int finishBy = course[1];

      if (today + duration <= finishBy) {
        // I can take this course
        today += duration;
        pq.add(duration);
      } else {
        // I cannot take this course, but can I replace any of my courses with
        // a course that takes lesser time? Select the course costing me the max
        // days in duration. Then add the difference to today.
        if (!pq.isEmpty() && duration < pq.peek()) {
          today += duration - pq.remove();
          pq.add(duration);
        }
      }
    }
    return pq.size();
  }

  public static void main(String[] args) {

    assertEquals(3,
        scheduleCourse(new int[][]{
            {100, 200},
            {200, 1300},
            {1000, 1250},
            {2000, 3200}}));

    assertEquals(3,
        scheduleCourse(new int[][]{
            {5, 2000000},
            {5, 10},
            {5, 100}}));

    assertEquals(2,
        scheduleCourse(new int[][]{
            {5, 10},
            {1, 10},
            {7, 10}}));

    assertEquals(1,
        scheduleCourse(new int[][]{
            {5, 5},
            {10, 10},
            {100, 100}}));

    assertEquals(0,
        scheduleCourse(new int[][]{}));

    assertEquals(0,
        scheduleCourse(new int[][]{
            {10, 2},
            {100, 10},
            {11000, 100}}));

    assertEquals(18, scheduleCourse(new int[][]{
        {914, 9927},
        {333, 712},
        {163, 5455},
        {835, 5040},
        {905, 8433},
        {417, 8249},
        {921, 9553},
        {913, 7394},
        {303, 7525},
        {582, 8658},
        {86, 957},
        {40, 9152},
        {600, 6941},
        {466, 5775},
        {718, 8485},
        {34, 3903},
        {380, 9996},
        {316, 7755}
    }));

    assertEquals(15, scheduleCourse(new int[][]{
        {596, 4958},
        {969, 1919},
        {313, 6227},
        {14, 8197},
        {637, 3529},
        {191, 2008},
        {683, 3678},
        {811, 3901},
        {75, 9109},
        {910, 3060},
        {532, 4488},
        {263, 9183},
        {467, 6092},
        {258, 7176},
        {360, 9992},
        {557, 1438},
        {989, 3947},
        {580, 2679},
        {870, 3254}
    }));
  }


}
