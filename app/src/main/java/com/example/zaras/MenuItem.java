package com.example.zaras;

import java.io.Serializable;

public class MenuItem implements Serializable {

    private String imageUrl;
    private String ingredients;
    private String name;
    private Double price;  // Change this to Double

    private int quantity;

    // Required default constructor for Firebase
    public MenuItem() {
        imageUrl = "";
        ingredients = "";
        name = "";
        price = 0.0;
    }

    public MenuItem(String imageUrl, String ingredients, String name, Object price) {
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
        this.name = name;

        // Check if price is a Double or a String and handle accordingly
        if (price instanceof Double) {
            this.price = (Double) price;
        } else if (price instanceof String) {
            try {
                this.price = Double.parseDouble((String) price);
            } catch (NumberFormatException e) {
                // Handle the case where parsing to double fails
                this.price = 0.0;
            }
        } else {
            // Handle other cases where price is neither Double nor String
            this.price = 0.0;
        }
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        // Format the price with two decimal places
        return String.format("%.2f", price);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nPrice per Item: RM " + getPrice() + "\nQuantity: " + quantity;
    }

    public double calculateTotalPrice() {
        return price * quantity;
    }
}
