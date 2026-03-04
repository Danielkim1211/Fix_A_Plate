package com.example.greenplate.models;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Represents a user in the application with basic user information.
 * This class is part of the models package used to encapsulate user data.
 */
public class User {
    /**
     * The name of the user. This field stores the user's name for
     * identification and personalization purposes.
     */
    private String name;
    private int weight = 0;
    private String gender = "";
    private int heightInInches = 0;
    private String userId;
    private String email;
    private ArrayList<String> mealIds = new ArrayList<>();
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private ArrayList<ShoppingListItem> shoppingList = new ArrayList<>();
    private HashMap<String, Integer> dailyCalories = new HashMap<>();


    /**
     * Constructs a new User instance with the specified name.
     *
     * @param name The name of the user to be set during object creation.
     * @param email The email of the user
     * @param gender The gender of the user
     * @param id The id of the user
     * @param weight The weight of the user
     * @param heightInInches The height of the user in inches
     */
    public User(String name, int weight, String gender, int heightInInches, String id,
                String email) {
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.heightInInches = heightInInches;
        userId = id;
        this.email = email;
    }

    public User(String name, int weight, String gender, int heightInInches, String id,
                String email, ArrayList<String> meals) {
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.heightInInches = heightInInches;
        userId = id;
        this.email = email;
        mealIds = new ArrayList<>();
        for (String meal : meals) {
            mealIds.add(meal);
        }
    }

    public User(String name, int[] heightweight, String gender, String id,
                String email, ArrayList<String> meals,
                ArrayList<ShoppingListItem> shoppingListItems) {
        this.name = name;
        this.gender = gender;
        this.weight = heightweight[1];
        this.heightInInches = heightweight[0];
        userId = id;
        this.email = email;
        mealIds = new ArrayList<>();
        for (String meal : meals) {
            mealIds.add(meal);
        }
        shoppingList = shoppingListItems;
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String id, String email) {
        this.name = name;
        userId = id;
        this.email = email;
    }

    public String getHeight() {
        int feet = heightInInches / 12;
        int inches = heightInInches -  ((heightInInches / 12) * 12);
        return "" + feet + "' " + inches + "\"";
    }

    public int getDailyCalorieIntake() {
        int calories = 0;
        // For Men:
        // BMR=(4.536×weight in pounds)+(15.88×height in inches)+5
        // For women:
        // BMR=(4.536×weight in pounds)+(15.88×height in inches)−161
        if (gender.equals("Male")) {
            calories = (int) (weight * 4.536 + 15.88 * heightInInches + 5);
        } else {
            calories = (int) (weight * 4.536 + 15.88 * heightInInches - 161);
        }

        return calories;
    }

    public void addPersonalInformation(int h, int w, String g) {
        heightInInches = h;
        weight = w;
        gender = g;
    }

    public Map<String, Object> getUserMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("weight", weight);
        result.put("gender", gender);
        result.put("heightInInches", heightInInches);
        result.put("userId", userId);
        result.put("email", email);
        result.put("mealIds", mealIds);
        return result;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeightInInches() {
        return heightInInches;
    }

    public String getGender() {
        return gender;
    }

    public String getUserId() {
        return userId;
    }

    public void addMeal(String mealId) {
        mealIds.add(mealId);
    }

    public void addIngredient(Ingredient i) {
        ingredients.add(i);
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void removeIngredient(String name) {
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
                ingredients.remove(i);
            }
        }
    }

    public int getQuantityOfIngredient(String ingredientName) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().toLowerCase().equals(ingredientName.toLowerCase())) {
                return ingredient.getQuantity();
            }
        }
        return 0;
    }

    public Ingredient findIngredient(String ingredientName) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().toLowerCase().equals(ingredientName.toLowerCase())) {
                return ingredient;
            }
        }
        return null;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public boolean checkForIngredientAndQuantity(String ingredientName, int quantityNeeded) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().toLowerCase().equals(ingredientName.toLowerCase())) {
                if (getQuantityOfIngredient(ingredientName) >= quantityNeeded) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<ShoppingListItem> getShoppingList() {
        return shoppingList;
    }
    public void setShoppingList(ArrayList<ShoppingListItem> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public void addShoppingListItem(ShoppingListItem item) {
        this.shoppingList.add(item);
    }
    public void removeShoppingListItem(String itemId) {
        shoppingList.removeIf(item -> item.getId().equals(itemId));
    }


    public void setMeals(HashMap<String, Integer> meals) {
        this.dailyCalories = meals;
    }

    public ArrayList<String> getMealIds() {
        return mealIds;
    }

    public int getCaloriesForDay(String day) {
        if (dailyCalories.keySet().contains(day)) {
            return dailyCalories.get(day);
        } else {
            return 0;
        }
    }

    public HashMap<Integer, Integer> getCaloriesForMonth(String month, String year) {
        HashMap<Integer, Integer> caloriesForMonth = new HashMap<>();
        for (String key : dailyCalories.keySet()) {
            if (key.startsWith(year) && key.contains("-" + month + "-")) {
                caloriesForMonth.put(Integer.valueOf(key.split("-")[2]), dailyCalories.get(key));
            }
        }
        return caloriesForMonth;
    }

    public void addCalories(String date, int calories) {
        if (dailyCalories.keySet().contains(date)) {
            dailyCalories.put(date, dailyCalories.get(date) + calories);
        } else {
            dailyCalories.put(date, calories);
        }
    }
    public Ingredient findIngredientByName(String ingredientName) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equalsIgnoreCase(ingredientName)) {
                return ingredient;
            }
        }
        return null;
    }
    public HashMap<String, Integer> getShoppingListQuantities() {
        HashMap<String, Integer> quantities = new HashMap<>();
        for (ShoppingListItem item : shoppingList) {
            quantities.put(item.getName(), item.getQuantity());
        }
        return quantities;
    }
}
