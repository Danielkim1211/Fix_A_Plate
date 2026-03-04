package com.example.greenplate.viewmodels;


import androidx.annotation.NonNull;

import com.example.greenplate.models.Firebase;
import com.example.greenplate.models.Recipe;
import com.example.greenplate.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeViewModel {
    private static Firebase firebase = Firebase.getInstance();
    private static Recipe.RecipeTab recipeTab = Recipe.RecipeTab.AtoZ;

    public static Recipe.RecipeTab getRecipeTab() {
        return recipeTab;
    }

    public static void addRecipe(Recipe recipe) {
        DatabaseReference recipesRef = firebase.getDatabase().getReference()
                .child("cookbook").push();
        recipesRef.setValue(recipe.toMap());
        recipe.setId(recipesRef.getKey());
    }

    public static void setTab(Recipe.RecipeTab newTab) {
        recipeTab = newTab;
    }

    public static void fetchRecipes(User user) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        firebase.getDatabase().getReference().child("cookbook")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap<String, HashMap<String, Object>> recipesMap =
                                (HashMap<String, HashMap<String, Object>>) snapshot.getValue();
                        if (recipes != null) {
                            //System.out.println(recipesMap);

                            for (String recipeId : recipesMap.keySet()) {
                                HashMap<String, Integer> ingredientMap = new HashMap<>();
                                for (Object fieldName : recipesMap.get(recipeId).keySet()) {
                                    if (fieldName.equals("ingredients")) {
                                        //System.out.println(((HashMap<String, Integer>)
                                        // recipesMap.get(recipeId).get(fieldName)).get("Bun"));
                                        for (String ingredientName : ((HashMap<String, String>)
                                                recipesMap.get(recipeId).get(fieldName)).keySet()) {
                                            ingredientMap.put(ingredientName,
                                                    Integer.valueOf(((HashMap<String, String>)
                                                            recipesMap.get(recipeId).get(fieldName))
                                                            .get(ingredientName)));
                                        }
                                    }
                                }
                                boolean canMake = true;
                                for (String ingredientName : ingredientMap.keySet()) {
                                    if (user.getIngredients().size() == 0) {
                                        canMake = false;
                                    }

                                    if (!FirebaseViewModel.getInstance().getUser()
                                            .checkForIngredientAndQuantity(ingredientName,
                                                    ingredientMap.get(ingredientName))) {
                                        canMake = false;
                                    }
                                }
                                recipes.add(new Recipe((String) recipesMap.get(recipeId)
                                        .get("recipeName"), ingredientMap, recipeId, canMake,
                                        ((Long) recipesMap.get(recipeId).get("calories"))
                                                .intValue()));
                            }
                            user.setRecipes(recipes);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}
