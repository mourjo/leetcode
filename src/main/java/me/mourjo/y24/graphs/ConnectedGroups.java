package me.mourjo.y24.graphs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConnectedGroups {

    public static void main(String[] args) {
        var app = new ConnectedGroups();
        System.out.println(app.findCircleNum(new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}}));
        System.out.println(app.findCircleNum(new int[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}));
        System.out.println(app.findCircleNum(new int[][]{
            {1, 0, 0, 1}, {0, 1, 1, 0}, {0, 1, 1, 1}, {1, 0, 1, 1}
        }));
    }

    static Set<Integer> visited;

    public static int countGroups(List<String> relationships) {
        if (relationships.isEmpty()) {
            return 0;
        }

        int components = 0;
        visited = new HashSet<>();

        for (int i = 0; i < relationships.size(); i++) {
            if (visit(relationships, i)) {
                components++;
            }
        }

        return components;
    }

    static public boolean visit(List<String> relationships, int self) {
        if (visited.contains(self)) {
            return false;
        }
        visited.add(self);

        for (int neighbor = 0; neighbor < relationships.get(self).length(); neighbor++) {
            if (neighbor != self && relationships.get(self).charAt(neighbor) == '1') {
                visit(relationships, neighbor);
            }
        }

        return true;
    }

    static public boolean visit(int[][] matrix, int self) {
        if (visited.contains(self)) {
            return false;
        }
        visited.add(self);

        for (int neighbor = 0; neighbor < matrix.length; neighbor++) {
            if (neighbor != self && matrix[self][neighbor] == 1) {
                visit(matrix, neighbor);
            }
        }

        return true;
    }


    public int findCircleNum(int[][] matrix) {
        int components = 0;
        visited = new HashSet<>();
        for (int i = 0; i < matrix.length; i++) {
            if (visit(matrix, i)) {
                components++;
            }
        }

        return components;
    }


}
