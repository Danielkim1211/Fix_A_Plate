package com.example.greenplate.views;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.greenplate.R;
import com.example.greenplate.models.Recipe;
import com.example.greenplate.viewmodels.RecipeViewModel;
import com.example.greenplate.viewmodels.ShoppingListViewModel;

public class AddShoppingActivity extends AppCompatActivity {
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
        setContentView(R.layout.add_shopping_page);
        // Initialize navigation buttons and set their onClickListeners.
        final ImageButton toHome = findViewById(R.id.toHomePage);
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddShoppingActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toInput = findViewById(R.id.toInputPage);
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddShoppingActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe.RecipeTab tab = RecipeViewModel.getRecipeTab();
                Intent intent = new Intent(AddShoppingActivity.this,
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
                Intent intent = new Intent(AddShoppingActivity.this,
                        IngredientsActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);
        toShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddShoppingActivity.this,
                        ShoppingActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toPersonalInfo = findViewById(R.id.toPersonalPage);
        toPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddShoppingActivity.this,
                        PersonalInfoActivity.class);
                startActivity(intent);
            }
        });


        final Button saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameEditText = findViewById(R.id.shoppingList_name_enter);
                EditText quantityEditText = findViewById(R.id.shoppingList_quantity_enter);
                EditText caloriesEditText = findViewById(R.id.shoppingList_calories_enter);
                String name = nameEditText.getText().toString().trim();
                String quantityStr = quantityEditText.getText().toString().trim();
                String caloriesStr = caloriesEditText.getText().toString().trim();
                if (name.isEmpty() || quantityStr.isEmpty() || caloriesStr.isEmpty()) {
                    Toast.makeText(AddShoppingActivity.this, "All fields must be filled"
                            + " out.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int quantity;
                int itemCalories;
                try {
                    quantity = Integer.parseInt(quantityStr);
                    itemCalories = Integer.parseInt(caloriesStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(AddShoppingActivity.this, "Quantity and Calories "
                            + "must be valid numbers.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (quantity <= 0) {
                    Toast.makeText(AddShoppingActivity.this, "Quantity must be "
                            + "positive.", Toast.LENGTH_SHORT).show();
                    return;
                }
                ShoppingListViewModel.addShoppingListItem(name, quantity);
                nameEditText.setText("");
                quantityEditText.setText("");
                caloriesEditText.setText("");
                Toast.makeText(AddShoppingActivity.this, "Item saved to shopping "
                        + "list!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
