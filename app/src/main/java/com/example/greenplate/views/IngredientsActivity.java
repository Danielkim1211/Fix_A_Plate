package com.example.greenplate.views;


import com.example.greenplate.R;
import com.example.greenplate.models.Ingredient;
import com.example.greenplate.models.IngredientAdapter;
import com.example.greenplate.viewmodels.FirebaseViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageButton;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * IngredientsActivity is responsible for displaying
 * the ingredients page within the app,
 * allowing users to access and view various
 * ingredients needed for cooking. It also includes
 * navigation options to other primary features
 * of the app like the home page, input page,
 * recipe page, shopping list, and the
 * ingredients page itself for refresh purposes.
 * This activity serves as a central point
 * for ingredient management and navigation.
 */

public class IngredientsActivity extends AppCompatActivity {

    private ListView mListview;
    private ArrayList<Ingredient> mArrData = FirebaseViewModel.getInstance().getUser()
            .getIngredients();
    private IngredientAdapter ingredientAdapter;

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
        setContentView(R.layout.ingredient_page);


        mListview = (ListView) findViewById(R.id.ingredients_list);

        ingredientAdapter = new IngredientAdapter(mArrData, IngredientsActivity.this);
        mListview.setAdapter(ingredientAdapter);
        ingredientAdapter.notifyDataSetChanged();





        // Initialize navigation buttons and set their onClickListeners.
        final ImageButton toHome = findViewById(R.id.toHomePage);
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toInput = findViewById(R.id.toInputPage);
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this,
                        RecipeActivityAtoZ.class);
                startActivity(intent);
            }
        });
        final ImageButton toIngredients = findViewById(R.id.toIngredientsPage);
        toIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this,
                        IngredientsActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);
        toShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this,
                        ShoppingActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toPersonalInfo = findViewById(R.id.toPersonalPage);
        toPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this,
                        PersonalInfoActivity.class);
                startActivity(intent);
            }
        });

        final ImageButton toAddIngredientActivity = findViewById(R.id.to_add_ingredient_page);
        toAddIngredientActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this,
                        AddIngredientActivity.class);
                startActivity(intent);
            }
        });

    }
}
