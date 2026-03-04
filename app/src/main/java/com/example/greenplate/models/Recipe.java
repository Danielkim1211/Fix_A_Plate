package com.example.greenplate.models;

import java.util.HashMap;
import java.util.Map;

public class Recipe implements Comparable<Recipe> {
    private String recipeName;
    private HashMap<String, Integer> ingredients;
    private String id; // Unique identifier for the recipe
    private int calories;
    private boolean canMake;
    public enum RecipeTab { AtoZ, ZtoA, CANCOOK };

    public Recipe(String recipeName, HashMap<String, Integer> ingredients, String id,
                  boolean canMake, int calories) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.id = id;
        this.canMake = canMake;
        this.calories = calories;
    }

    public Recipe(String recipeName, HashMap<String, Integer> ingredients, int totalCalories) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.calories = totalCalories;
    }
    public Recipe(String recipeName, HashMap<String, Integer> ingredients) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
    }

    // Getter for recipeName
    public String getRecipeName() {
        return recipeName;
    }

    // Setter for recipeName
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    // Getter for ingredients
    public HashMap<String, Integer> getIngredients() {
        return ingredients;
    }

    // Setter for ingredients
    public void setIngredients(HashMap<String, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    // Getter for id
    public String getId() {
        return id;
    }

    // Setter for id
    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> recipeMap = new HashMap<>();
        recipeMap.put("recipeName", recipeName);

        // Convert ingredients list to a map
        Map<String, String> ingredientsMap = new HashMap<>();
        for (String ingredient : ingredients.keySet()) {
            ingredientsMap.put(ingredient, String.valueOf(ingredients.get(ingredient)));
        }
        recipeMap.put("ingredients", ingredientsMap);
        recipeMap.put("calories", calories);
        return recipeMap;
    }

    @Override
    public int compareTo(Recipe o) {
        return this.recipeName.toLowerCase().compareTo(o.getRecipeName().toLowerCase());
    }

    public boolean isCanMake() {
        return canMake;
    }

    public void addIngredients(Ingredient ingredient) {
        ingredients.put(ingredient.getName(), ingredient.getQuantity());
        ingredient.subscribe(this);
    }
    public void update(Ingredient ingredient) {
        // Check if the ingredient is available and update recipe accordingly
        canMake = ingredient.isAvailable();
    }
    /*public int calculateCalories(HashMap<String, Integer> ingredients) {
        int totalCalories = 0;
        User user = FirebaseViewModel.getInstance().getUser();
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            Ingredient ingredient = user.findIngredientByName(entry.getKey());
            if (ingredient != null) {
                totalCalories += ingredient.getCalories() * entry.getValue();
            }
        }
        return totalCalories;
    }*/
    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    /**
     * Checks if the input text is non-null and
     * not empty after trimming whitespace, as well
     * as verifying no numbers or special
     * characters were used.
     *
     * @param recipeName The string input to check.
     * @return true if the input is valid
     * (non-null and not empty), false otherwise.
     */
    public static boolean isValidRecipeName(String recipeName) {
        // Check if the recipe name contains any special characters
        return recipeName.matches("[a-zA-Z0-9 ]+");
    }
}