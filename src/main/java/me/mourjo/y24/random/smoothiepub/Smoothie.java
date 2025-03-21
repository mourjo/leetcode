package me.mourjo.y24.random.smoothiepub;

public record Smoothie(String name, double cost) {

    public Smoothie(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public Smoothie() {
        this("Orange Squash", 3);
    }
}
