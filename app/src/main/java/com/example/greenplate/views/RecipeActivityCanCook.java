package com.example.greenplate.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.greenplate.R;
import com.example.greenplate.models.Recipe;
import com.example.greenplate.models.RecipeListAdapter;
import com.example.greenplate.viewmodels.FirebaseViewModel;
import com.example.greenplate.viewmodels.RecipeViewModel;

import java.util.ArrayList;

/**
 * RecipeActivity serves as the primary interface
 * for displaying recipes to the user.
 * It includes navigation buttons to various
 * sections of the app, such as the home page,
 * input page, ingredients page, and shopping
 * list, allowing for easy access to these resources.
 * This activity is part of the application's
 * effort to organize and display cooking recipes in a user-friendly manner.
 */

public class RecipeActivityCanCook extends AppCompatActivity {
    private ListView mListview;
    private static RecipeListAdapter recipeListAdapter;

    /**
     * Initializes the activity by setting
     * the content view to the recipe page layout
     * and configuring onClickListeners
     * for navigation buttons. These listeners enable
     * users to navigate to different
     * parts of the application, such
     * as the home activity,
     * input activity, recipe activity
     * (refreshing the current view), ingredients activity,
     * and shopping activity, enhancing
     * the app's usability and navigation experience.
     *
     * @param savedInstanceState If the activity is being
     *                           re-initialized after
     *                           previously being shut down,
     *                           this Bundle contains
     *                           the data it most recently
     *                           supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_page_can_cook);
        RecipeViewModel.setTab(Recipe.RecipeTab.CANCOOK);
        ArrayList<Recipe> recipes = FirebaseViewModel.getInstance().getUser().getRecipes();
        ArrayList<Recipe> canMakeRecipes = new ArrayList<>();

        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).isCanMake()) {
                canMakeRecipes.add(recipes.get(i));
            }
        }

        mListview = (ListView) findViewById(R.id.recipe_list);
        recipeListAdapter = new RecipeListAdapter(canMakeRecipes, RecipeActivityCanCook.this);
        mListview.setAdapter(recipeListAdapter);
        recipeListAdapter.notifyDataSetChanged();

        final ImageButton toHome = findViewById(R.id.toHomePage);
        final ImageButton toInput = findViewById(R.id.toInputPage);
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        final ImageButton toIngredients = findViewById(R.id.toIngredientsPage);
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);
        final ImageButton toPersonalInfo = findViewById(R.id.toPersonalPage);

        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivityCanCook.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivityCanCook.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivityCanCook.this,
                        RecipeActivityCanCook.class);
                startActivity(intent);
            }
        });
        toIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivityCanCook.this,
                        IngredientsActivity.class);
                startActivity(intent);
            }
        });
        toShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivityCanCook.this,
                        ShoppingActivity.class);
                startActivity(intent);
            }
        });
        toPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivityCanCook.this,
                        PersonalInfoActivity.class);
                startActivity(intent);
            }
        });


        final ImageButton toAddRecipe = findViewById(R.id.to_add_recipe_page);
        toAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivityCanCook.this,
                        AddRecipeActivity.class);
                startActivity(intent);
            }
        });

        final ImageButton toRecipeVegetarian = findViewById(R.id.toeRecipeVegetarian);
        toRecipeVegetarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivityCanCook.this,
                        RecipeActivityZtoA.class);
                startActivity(intent);
            }
        });

        final ImageButton toEasyRecipe = findViewById(R.id.toeRecipeEasy);
        toEasyRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivityCanCook.this,
                        RecipeActivityAtoZ.class);
                startActivity(intent);
            }
        });



    }
    public void onClickItem(String item) {
        Intent intent = new Intent(RecipeActivityCanCook.this, ViewRecipeActivity.class);
        intent.putExtra("name", item);
        startActivity(intent);
    }
    public static void refreshRecipes() {
        if (recipeListAdapter != null) {
            recipeListAdapter.notifyDataSetChanged();
        }
    }
}
