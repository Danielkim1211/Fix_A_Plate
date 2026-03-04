package com.example.greenplate.views;

import com.example.greenplate.R;
import com.example.greenplate.models.ShoppingListAdapter;
import com.example.greenplate.models.ShoppingListItem;
import com.example.greenplate.viewmodels.FirebaseViewModel;
import com.example.greenplate.viewmodels.RecipeViewModel;
import com.example.greenplate.viewmodels.ShoppingListViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * ShoppingActivity provides a user interface
 * for displaying and managing a shopping list.
 * The user will be able to customize and get
 * grocery recommendations in this activity.
 * This activity includes navigation buttons
 * that allow the user to switch between different parts of the application,
 * such as the home page, input page, recipe
 * page, ingredients page, and the shopping list itself for easy navigation
 * and access to various functionalities
 * within the app.
 */

public class ShoppingActivity extends AppCompatActivity {
    private ListView mListview;
    private ArrayList<ShoppingListItem> mArrData = FirebaseViewModel.getInstance().getUser()
            .getShoppingList();
    private ShoppingListAdapter shoppingListAdapter;

    /**
     * Initializes the activity by inflating
     * its UI from the shopping_list layout,
     * setting up onClickListeners for
     * each button within the navigation bar to
     * enable users to navigate from one section of the
     * application to another. This setup facilitates easy
     * access to the application's features,
     * enhancing the user experience.
     *
     * @param savedInstanceState If the activity is being
     *                           re-initialized after previously
     *                           being shut down,
     *                           this Bundle contains the
     *                           data it most recently supplied
     *                           in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     *                           This bundle can be used
     *                           to recreate the activity
     *                           as it was prior to
     *                           being paused or stopped.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list);

        mListview = (ListView) findViewById(R.id.shopping_list_view);

        shoppingListAdapter = new ShoppingListAdapter(mArrData, ShoppingActivity.this);
        mListview.setAdapter(shoppingListAdapter);
        shoppingListAdapter.notifyDataSetChanged();

        final ImageButton toHome = findViewById(R.id.toHomePage);
        final ImageButton toInput = findViewById(R.id.toInputPage);
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        final ImageButton toIngredients = findViewById(R.id.toIngredientsPage);
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);
        final ImageButton toPersonalInfo = findViewById(R.id.toPersonalPage);
        final ImageButton toAddShopping = findViewById(R.id.to_add_shopping_page);
        final ImageButton buyItems = findViewById(R.id.buy_items_button);

        buyItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseViewModel.getInstance().getUser() == null) {
                    // User is null, handle this situation
                    Toast.makeText(ShoppingActivity.this,
                            "User data is not loaded yet. Please wait.",
                            Toast.LENGTH_SHORT).show();
                    // Consider returning, redirecting to a login screen, or retrying the data load
                    return;
                }

                // Check if the selectedItems list is null or empty
                if (ShoppingListViewModel.getSelectedItems() == null || ShoppingListViewModel
                        .getSelectedItems().isEmpty()) {
                    Toast.makeText(ShoppingActivity.this,
                            "No items selected to purchase.", Toast.LENGTH_SHORT).show();
                    return;
                }
                ShoppingListViewModel.purchaseItems();
                shoppingListAdapter.notifyDataSetChanged();
                shoppingListAdapter.uncheckAllCheckboxes();
                RecipeViewModel.fetchRecipes(FirebaseViewModel.getInstance().getUser());
                RecipeActivityAtoZ.refreshRecipes();
                RecipeActivityZtoA.refreshRecipes();
                RecipeActivityCanCook.refreshRecipes();
            }
        });

        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingActivity.this,
                        RecipeActivityAtoZ.class);
                startActivity(intent);
            }
        });
        toIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingActivity.this,
                        IngredientsActivity.class);
                startActivity(intent);
            }
        });
        toShopping.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingActivity.this,
                        ShoppingActivity.class);
                startActivity(intent);
            }
        });
        toPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingActivity.this,
                        PersonalInfoActivity.class);
                startActivity(intent);
            }
        });
        toAddShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingActivity.this,
                        AddShoppingActivity.class);
                startActivity(intent);
            }
        });

    }
}
