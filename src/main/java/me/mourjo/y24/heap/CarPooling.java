package me.mourjo.y24.heap;

import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
There is a car with capacity empty seats. The vehicle only drives east (i.e., it cannot turn around and drive west).

You are given the integer capacity and an array trips where trips[i] = [numPassengersi, fromi, toi] indicates
that the ith trip has numPassengersi passengers and the locations to pick them up and drop them off are from_i and to_i
respectively. The locations are given as the number of kilometers due east from the car's initial location.

Return true if it is possible to pick up and drop off all passengers for all the given trips, or false otherwise.


Example 1:

Input: trips = [[2,1,5],[3,3,7]], capacity = 4
Output: false
Example 2:

Input: trips = [[2,1,5],[3,3,7]], capacity = 5
Output: true


Constraints:

1 <= trips.length <= 1000
trips[i].length == 3
1 <= numPassengersi <= 100
0 <= fromi < toi <= 1000
1 <= capacity <= 105
 */
public class CarPooling {
    public static void main(String[] args) {
        var app = new CarPooling();
        Assertions.assertFalse(
                app.carPooling(
                        new int[][]{{2, 1, 5}, {3, 3, 7}},
                        4
                )
        );

        Assertions.assertTrue(
                app.carPooling(
                        new int[][]{{2, 1, 5}, {3, 3, 7}},
                        5
                )
        );

        Assertions.assertTrue(
                app.carPooling(
                        new int[][]{{3, 2, 8}, {4, 4, 6}, {10, 8, 9}},
                        11
                )
        );

        Assertions.assertFalse(
                app.carPooling(
                        new int[][]{

                                {3, 6, 9}, {10, 2, 3}, {1, 6, 8}, {2, 1, 6}, {9, 3, 9}
                        },
                        12
                )
        );

        Assertions.assertFalse(
                app.carPooling(
                        new int[][]{

                                {1, 1, 4}, {9, 4, 9}, {9, 1, 9}, {2, 3, 5}, {4, 1, 5}, {10, 4, 5}
                        },
                        33
                )
        );
    }

    public boolean carPooling(int[][] trips, int capacity) {
        int[] deltaAtPositions = new int[1002];
        for (var trip : trips) {
            int starting = trip[1];
            int ending = trip[2];
            int passengerCount = trip[0];

            deltaAtPositions[starting] += passengerCount;
            deltaAtPositions[ending] -= passengerCount;
        }

        int runningCapacity = 0;
        for (int delta : deltaAtPositions) {
            runningCapacity += delta;
            if (runningCapacity > capacity) {
                // at any point in time, capacity should not be breached
                return false;
            }
        }
        return true;

    }

    public boolean carPoolingWithSortedPQ(int[][] tripArray, int capacity) {
        var trips = Arrays.stream(tripArray)
                .map(trip -> new Trip(trip[1], trip[2], trip[0]))
                .sorted((o1, o2) -> Integer.compare(o1.startingPoint, o2.startingPoint))
                .toList();

        int currentPassengers = 0;
        var q = new PriorityQueue<Trip>();
        for (Trip trip : trips) {
            currentPassengers += trip.numPassengers();

            while (!q.isEmpty() && q.peek().endingPoint() <= trip.startingPoint()) {
                var removed = q.remove();
                currentPassengers -= removed.numPassengers();

            }

            if (currentPassengers > capacity) {
                return false;
            }
            q.add(trip);
        }

        return true;
    }

    record Trip(int startingPoint, int endingPoint, int numPassengers) implements Comparable<Trip> {
        @Override
        public int compareTo(Trip o) {
            return Integer.compare(this.endingPoint, o.endingPoint);
        }
    }
}
