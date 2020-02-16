package me.mourjo;

import java.util.ArrayList;
import java.util.List;

public class Utilities {
    public static final String RED = "\033[31m";
    public static final String BLACK = "\033[0m";
    public static boolean check (Object actual, Object... expected) {
        for (Object e : expected)
            if (actual.equals(e)) {
                System.out.println(BLACK + "Passed: " + actual);
                return true;
            }

        System.out.print(RED + "*** Actual: " + actual +" Expected: ");
        for (Object e : expected) {
            System.out.print(e + ", ");
        }
        System.out.println(BLACK);
        System.out.println();
        return false;
    }

    public static <T> List<T> toList (T arr[]) {
        List<T> res = new ArrayList<>(arr.length);
        for (T item : arr)
            res.add(item);
        return res;
    }

    public static List<Integer> toIntList (int arr[]) {
        List<Integer> res = new ArrayList<>(arr.length);
        for (int item : arr)
            res.add(item);
        return res;
    }
}
