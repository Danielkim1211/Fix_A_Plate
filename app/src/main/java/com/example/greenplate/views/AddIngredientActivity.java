package com.example.greenplate.views;


import com.example.greenplate.R;
import com.example.greenplate.models.Recipe;
import com.example.greenplate.viewmodels.FirebaseViewModel;
import com.example.greenplate.viewmodels.IngredientsViewModel;
import com.example.greenplate.viewmodels.RecipeViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddIngredientActivity extends AppCompatActivity {
    /**
     * Called when the activity is starting.
     * This method handles the initialization of the activity,
     * setting the content view from a layout resource defined
     * in R.layout.ingredient_page. It also
     * initializes each navigation button in the navigation
     * bar and sets onClickListeners to them, enabling navigation to
     * different parts of the application based on user interaction.
     *
     * @param savedInstanceState If the activity is being
     *                           re-initialized after previously
     *                           being shut down,
     *                           this Bundle contains the
     *                           data it most recently
     *                           supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     *                           This bundle can be used
     *                           to reconstruct the activity
     *                           to its previous state if necessary.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ingredient_page);
        // Initialize navigation buttons and set their onClickListeners.
        final ImageButton toHome = findViewById(R.id.toHomePage);
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddIngredientActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toInput = findViewById(R.id.toInputPage);
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddIngredientActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe.RecipeTab tab = RecipeViewModel.getRecipeTab();
                Intent intent = new Intent(AddIngredientActivity.this,
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
                Intent intent = new Intent(AddIngredientActivity.this,
                        IngredientsActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);
        toShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddIngredientActivity.this,
                        ShoppingActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toPersonalInfo = findViewById(R.id.toPersonalPage);
        toPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddIngredientActivity.this,
                        PersonalInfoActivity.class);
                startActivity(intent);
            }
        });


        final Button saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameEditText = findViewById(R.id.ingredient_name_enter);
                EditText quantityEditText = findViewById(R.id.ingredient_quantity_enter);
                EditText caloriesEditText = findViewById(R.id.ingredient_calories_enter);
                EditText expirationDateEditText = findViewById(R.id.ingredient_expiration_enter);

                // Extract the text from each EditText
                String name = nameEditText.getText().toString();
                String quantityStr = quantityEditText.getText().toString();
                String caloriesStr = caloriesEditText.getText().toString();
                String expirationDate = expirationDateEditText.getText().toString();

                // Convert quantity and calories from String to int
                int quantity = 0;
                int calories = 0;
                try {
                    quantity = Integer.parseInt(quantityStr);
                    calories = Integer.parseInt(caloriesStr);
                } catch (NumberFormatException e) {
                    // Handle the case where either quantity or calories isn't a valid integer
                    Toast.makeText(AddIngredientActivity.this, "Quantity and Calories "
                            + "must be valid numbers.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate the input (e.g., check if name is empty, quantity is positive, etc.)
                if (name.isEmpty() || quantity <= 0) {
                    Toast.makeText(AddIngredientActivity.this, "Please fill out the "
                            + "name and make sure quantity is positive.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                IngredientsViewModel.addIngredient(name, calories, quantity, expirationDate);

                nameEditText.setText("");
                quantityEditText.setText("");
                caloriesEditText.setText("");
                expirationDateEditText.setText("");

                Toast.makeText(AddIngredientActivity.this, "Ingredient saved!",
                        Toast.LENGTH_SHORT).show();
                RecipeViewModel.fetchRecipes(FirebaseViewModel.getInstance().getUser());
            }
        });



    }
}
