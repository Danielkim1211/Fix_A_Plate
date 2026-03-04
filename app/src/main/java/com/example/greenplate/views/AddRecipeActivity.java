package com.example.greenplate.views;

import com.example.greenplate.R;
import com.example.greenplate.models.Recipe;
import com.example.greenplate.viewmodels.FirebaseViewModel;
import com.example.greenplate.viewmodels.RecipeViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AddRecipeActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_add_page);
        // Initialize navigation buttons and set their onClickListeners.
        final ImageButton toHome = findViewById(R.id.toHomePage);
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecipeActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toInput = findViewById(R.id.toInputPage);
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecipeActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe.RecipeTab tab = RecipeViewModel.getRecipeTab();
                Intent intent = new Intent(AddRecipeActivity.this,
                        tab == Recipe.RecipeTab.AtoZ ? RecipeActivityAtoZ.class : tab
                                == Recipe.RecipeTab.ZtoA ? RecipeActivityZtoA.class
                                : RecipeActivityCanCook.class);
                startActivity(intent);
            }
        });
        final ImageButton toIngredients = findViewById(R.id.toIngredientsPage);
        toIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecipeActivity.this,
                        IngredientsActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);
        toShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecipeActivity.this,
                        ShoppingActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toPersonalInfo = findViewById(R.id.toPersonalPage);
        toPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecipeActivity.this,
                        PersonalInfoActivity.class);
                startActivity(intent);
            }
        });
        // A HashMap to store the dish name and its corresponding ingredients and quantities
        final HashMap<String, List<Pair<String, Pair<Integer, Integer>>>>
                dishIngredientsMap = new HashMap<>();
        final ImageButton toAddIngredientPage = findViewById(R.id.to_add_ingredient_page);
        toAddIngredientPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the dish name and ingredients and quantities
                EditText dishNameEditText = findViewById(R.id.ingredient_name_enter);
                EditText ingredientEditText = findViewById(R.id.ingredient_quantity_enter);
                EditText quantityEditText = findViewById(R.id.ingredient_calories_enter);
                EditText calorieEditText = findViewById(R.id.ingredient_calories);
                String dishName = dishNameEditText.getText().toString();
                String ingredient = ingredientEditText.getText().toString();
                int quantity;
                int calories;
                try {
                    quantity = Integer.parseInt(quantityEditText.getText().toString());
                    calories = Integer.parseInt(calorieEditText.getText().toString());
                } catch (NumberFormatException e) {
                    // Handle exception for invalid number format
                    quantityEditText.setError("Please enter a valid number");
                    return;
                }
                // Check if dish name is not empty
                if (dishName.isEmpty()) {
                    dishNameEditText.setError("Please enter a dish name");
                    return;
                }
                // Check if ingredient is not empty
                if (ingredient.isEmpty()) {
                    ingredientEditText.setError("Please enter an ingredient");
                    return;
                }
                // Add the ingredient and quantity to the list for this dish name in the map
                List<Pair<String, Pair<Integer, Integer>>> ingredientsList =
                        dishIngredientsMap.getOrDefault(dishName, new ArrayList<>());
                ingredientsList.add(new Pair<>(ingredient, new Pair<>(quantity, calories)));
                dishIngredientsMap.put(dishName, ingredientsList);

                ingredientEditText.setText("");
                quantityEditText.setText("");
                calorieEditText.setText(""); // Clear calorie input field
                Toast.makeText(AddRecipeActivity.this, "Ingredient added",
                        Toast.LENGTH_SHORT).show();
            }
        });
        final Button saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText dishNameEditText = findViewById(R.id.ingredient_name_enter);
                String dishName = dishNameEditText.getText().toString().trim();
                if (dishName.isEmpty()) {
                    Toast.makeText(AddRecipeActivity.this, "Please enter a dish name",
                        Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Pair<String, Pair<Integer, Integer>>> ingredientsPairs =
                    dishIngredientsMap.get(dishName);
                if (ingredientsPairs == null || ingredientsPairs.isEmpty()) {
                    Toast.makeText(AddRecipeActivity.this,
                        "No ingredients added for this dish", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, Integer> ingredientsForThisDish = new HashMap<>();
                int totalCalories = 0;
                for (Pair<String, Pair<Integer, Integer>> pair : ingredientsPairs) {
                    ingredientsForThisDish.put(pair.first, pair.second.first);
                    totalCalories += pair.second.first * pair.second.second;
                    System.out.println("This is " + totalCalories); // Calculate total calories
                }

                Recipe newRecipe = new Recipe(dishName, ingredientsForThisDish, totalCalories);

                RecipeViewModel.addRecipe(newRecipe);
                RecipeViewModel.fetchRecipes(FirebaseViewModel.getInstance().getUser());

                dishNameEditText.setText("");
                dishIngredientsMap.remove(dishName);
                Toast.makeText(AddRecipeActivity.this, "Recipe saved successfully",
                        Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddRecipeActivity.this,
                            RecipeActivityAtoZ.class);
                    startActivity(intent);
            }
        });
    }
}
