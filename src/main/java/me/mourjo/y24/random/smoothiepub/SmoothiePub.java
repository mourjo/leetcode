package me.mourjo.y24.random.smoothiepub;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SmoothiePub {

    private final Map<User, Tab> tabs;

    public SmoothiePub() {
        this.tabs = new HashMap<>();
    }

    public static void main(String[] args) {
        SmoothiePub pub = new SmoothiePub();
        User alice = new User("Alice");
        pub.newTab(alice);
        pub.orderSmoothie(alice, new Smoothie());
        pub.orderSmoothie(alice, new Smoothie());

        User bob = new User("Bob");
        pub.newTab(bob);
        pub.orderSmoothie(bob, new Smoothie());

        pub.clearTab(alice);
        System.out.println("Current tab: " + pub.showTabs());

        pub.orderSmoothie(bob, new Smoothie());
        pub.orderSmoothie(bob, new Smoothie());
        pub.orderSmoothie(bob, new Smoothie());

        pub.clearTab(bob);

        System.out.println("Current tab: " + pub.showTabs());

    }

    public void newTab(User user) {
        tabs.putIfAbsent(user, new Tab(user));
    }

    public void orderSmoothie(User user, Smoothie smoothie) {
        if (tabs.containsKey(user)) {
            newTab(user);
        }
        tabs.get(user).addSmoothie(smoothie);
    }

    public Collection<Tab> showTabs() {
        return tabs.values();
    }

    public void clearTab(User user) {
        if (tabs.containsKey(user)) {
            var tab = tabs.get(user);
            System.out.println(user + " has to pay " + tab.value());
            tabs.remove(user);
        } else {
            System.out.println("No tab for user " + user);
        }

    }
}
