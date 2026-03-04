package com.example.greenplate.viewmodels;

import androidx.annotation.NonNull;

import com.example.greenplate.models.Firebase;
import com.example.greenplate.models.Ingredient;
import com.example.greenplate.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class IngredientsViewModel {
    private static Firebase firebase = Firebase.getInstance();
    public static void addIngredient(String name, int calories,
                                     int quantity, String expirationDate) {
        Ingredient newIngredient;
        if (expirationDate.isEmpty()) {
            newIngredient = new Ingredient(name, calories, quantity, FirebaseViewModel.getInstance()
                    .getUser().getUserId());
        } else {
            newIngredient = new Ingredient(name, calories, quantity, expirationDate,
                    FirebaseViewModel.getInstance().getUser().getUserId());
        }
        for (Ingredient i : FirebaseViewModel.getInstance().getUser().getIngredients()) {
            if (i.getName().toLowerCase().equals(newIngredient.getName().toLowerCase())) {
                return;
            }
        }
        DatabaseReference ref = firebase.getDatabase().getReference()
                .child("pantry").push();
        ref.setValue(newIngredient.getMap());
        //System.out.println(ref.getKey());
        newIngredient.setId(ref.getKey());
        FirebaseViewModel.getInstance().getUser().addIngredient(newIngredient);
        firebase.getDatabase().getReference().child("users")
                .child(newIngredient.getUserId()).child("ingredientIds")
                .child(ref.getKey()).setValue(ref.getKey());
        return;
    }

    public static void fetchIngredients(User user) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        firebase.getDatabase().getReference().child("pantry").orderByChild("userId")
                .equalTo(user.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap<String, HashMap<String, String>> rawIngredients =
                                (HashMap<String, HashMap<String, String>>) snapshot.getValue();
                        if (rawIngredients != null) {
                            for (String key : (rawIngredients).keySet()) {
                                //System.out.println(rawIngredients);
                                ingredients.add(new Ingredient(rawIngredients.get(key).get("name"),
                                        Integer.valueOf(rawIngredients.get(key).get("calories")),
                                        Integer.valueOf(rawIngredients.get(key).get("quantity")),
                                        rawIngredients.get(key).get("expirationDate"),
                                        rawIngredients.get(key).get("userId"), key));
                            }
                            for (Ingredient ingredient : ingredients) {
                                user.addIngredient(ingredient);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public static void updateIngredient(Ingredient ingredient) {
        if (ingredient.getQuantity() <= 0) {
            FirebaseViewModel.getInstance().getUser().removeIngredient(ingredient.getName());
            firebase.getDatabase().getReference().child("pantry")
                    .child(ingredient.getId()).removeValue();
            firebase.getDatabase().getReference().child("users")
                    .child(ingredient.getUserId()).child("ingredientIds")
                    .child(ingredient.getId()).removeValue();
        } else {
            firebase.getDatabase().getReference().child("pantry")
                    .child(ingredient.getId()).setValue(ingredient.getMap());
        }
        RecipeViewModel.fetchRecipes(FirebaseViewModel.getInstance().getUser());
    }
}
