package me.mourjo.y24.random.smoothiepub;

import java.util.ArrayList;
import java.util.List;

public record Tab(User user, List<Smoothie> smoothies) {

    public Tab(User user, List<Smoothie> smoothies) {
        this.user = user;
        this.smoothies = new ArrayList<>(smoothies);
    }

    public Tab(User user) {
        this(user, new ArrayList<>());
    }

    public void addSmoothie(Smoothie smoothie) {
        smoothies.add(smoothie);
    }

    private double grossDiscountPct() {
        if (smoothies.size() >= 4) {
            return 0.05;
        }
        return 0;
    }

    public double value() {
        double sum = 0;
        for (var smoothie : smoothies) {
            sum += smoothie.cost();
        }
        return sum - (sum * grossDiscountPct());
    }
}
