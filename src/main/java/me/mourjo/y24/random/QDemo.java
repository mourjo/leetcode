package me.mourjo.y24.random;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

class DroppingQ {
    private final Queue<Integer> items;
    int capacity;

    DroppingQ(int capacity) {
        this.items = new LinkedBlockingQueue<>();
        this.capacity = capacity;
    }

    void put(int item) {
        if (items.size() >= capacity) {
            items.remove();
        }
        items.add(item);
    }

    @Override
    public String toString() {
        return "DroppingQ{" +
                "items=" + items +
                ", capacity=" + capacity +
                '}';
    }

    int oldest() {
        return items.peek();
    }
}

public class QDemo {
    public static void main(String[] args) {
        var q = new DroppingQ(3);
        q.put(1);
        q.put(2);
        q.put(3);
        q.put(4);

        System.out.println(q);

        System.out.println(q.oldest());
    }
}
