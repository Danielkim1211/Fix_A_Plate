package com.example.greenplate.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ingredient {
    private String name;
    private int calories;
    private int quantity;
    private String expirationDate = "";
    private String id;
    private String userId;
    private boolean available;
    private List<Recipe> subscribers;

    public Ingredient(String n, int c, int q, String uI) {
        name = n;
        calories = c;
        quantity = q;
        userId = uI;
    }

    public Ingredient(String n, int c, int q, String eD, String uI) {
        name = n;
        calories = c;
        quantity = q;
        expirationDate = eD;
        userId = uI;
    }

    public Ingredient(String n, int c, int q, String eD, String uI, String id) {
        name = n;
        calories = c;
        quantity = q;
        expirationDate = eD;
        userId = uI;
        this.id = id;
    }

    public Ingredient(String n) {
        this.name = n;
        this.available = false;
        this.subscribers = new ArrayList<>();
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCalories() {
        return calories;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getMap() {
        Map<String, String> info = new HashMap<>();
        info.put("name", name);
        info.put("quantity", Integer.toString(quantity));
        info.put("calories", Integer.toString(calories));
        info.put("expirationDate", expirationDate);
        info.put("userId", userId);
        return info;
    }

    public void increaseQuantity() {
        quantity++;
    }

    public int decreaseQuantity() {
        quantity--;
        return quantity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        return "You have " + quantity + " " + name + " and each are " + calories + " calories."
                + " " + id + " " + userId + " " + expirationDate;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setAvailable(boolean available) {
        this.available = available;
        notifySubscribers();
    }

    public void subscribe(Recipe recipe) {
        subscribers.add(recipe);
    }

    public void unsubscribe(Recipe recipe) {
        subscribers.remove(recipe);
    }

    private void notifySubscribers() {
        for (Recipe recipe: subscribers) {
            recipe.update(this);
        }
    }

    public boolean isAvailable() {
        return available;
    }

    public void setQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }
}
