package com.example.greenplate.views;

import com.example.greenplate.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.greenplate.models.Recipe;
import com.example.greenplate.viewmodels.FirebaseViewModel;
import com.example.greenplate.viewmodels.RecipeViewModel;


/**
 * HomeActivity serves as the central navigation point for the application,
 * offering users
 * a dashboard from which they can access various features of the app.
 * This includes navigating
 * to pages for home, input, recipes, ingredients, and shopping.
 * Each feature is accessible
 * via dedicated buttons on the home page layout.
 */
public class HomeActivity extends AppCompatActivity {
    /**
     * Initializes the activity, inflates the user interface
     * from the home_page layout,
     * and sets up onClickListeners for navigation buttons to
     * enable users to navigate
     * to different parts of the application,
     * enhancing user experience.
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
        setContentView(R.layout.home_page);
        // Initialize buttons for navigating to different activities.
        final ImageButton toHome = findViewById(R.id.toHomePage);
        final ImageButton toInput = findViewById(R.id.toInputPage);
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        final ImageButton toIngredients = findViewById(R.id.toIngredientsPage);
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);
        final ImageButton toPersonalInfo = findViewById(R.id.toPersonalPage);

        FirebaseViewModel fvm = FirebaseViewModel.getInstance();

        // Set onClickListeners for each button to start
        // the corresponding activity.
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ShoppingListViewModel slvm = new ShoppingListViewModel();
                slvm.addShoppingListItem("Beef", 2);
                slvm.addShoppingListItem("Eggs", 12);
                slvm.addShoppingListItem("Apples", 3);
                slvm.addShoppingListItem("Bread", 1);
                slvm.addShoppingListItem("Avocados", 4);
                slvm.addShoppingListItem("Chicken", 2);*/
                Intent intent = new Intent(HomeActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //IngredientsViewModel.fetchIngredients(FirebaseViewModel.getInstance().getUser());
                Intent intent = new Intent(HomeActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe.RecipeTab tab = RecipeViewModel.getRecipeTab();
                Intent intent = new Intent(HomeActivity.this,
                        tab == Recipe.RecipeTab.AtoZ ? RecipeActivityAtoZ.class : tab
                                == Recipe.RecipeTab.ZtoA ? RecipeActivityZtoA.class
                                : RecipeActivityCanCook.class);
                startActivity(intent);
            }
        });
        toIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,
                        IngredientsActivity.class);
                startActivity(intent);
            }
        });
        toShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,
                        ShoppingActivity.class);
                startActivity(intent);
            }
        });
        toPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this,
                        PersonalInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}
