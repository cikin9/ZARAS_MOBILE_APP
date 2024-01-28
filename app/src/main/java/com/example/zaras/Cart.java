package com.example.zaras;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static List<MenuItem> items = new ArrayList<>();

    public static void clearCart() {
        items.clear();
    }

    public static void addToCart(MenuItem item) {
        items.add(item);
    }

    public static List<MenuItem> getItems() {
        return items;
    }
}
