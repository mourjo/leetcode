package me.mourjo;

public class Utilities {
    public static final String RED = "\033[31m";
    public static final String BLACK = "\033[0m";
    public static void check (Object actual, Object... expected) {
        for (Object e : expected)
            if (actual.equals(e)) {
                System.out.println(BLACK + "Passed: " + actual);
                return;
            }

        System.out.print(RED + "*** Actual: " + actual +" Expected: ");
        for (Object e : expected) {
            System.out.print(e + ", ");
        }
        System.out.println(BLACK);
        System.out.println();
    }
}
