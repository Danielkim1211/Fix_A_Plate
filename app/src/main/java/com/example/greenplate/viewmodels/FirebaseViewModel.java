package com.example.greenplate.viewmodels;

import android.util.Log;
import com.example.greenplate.models.Meal;
import com.example.greenplate.views.InputActivity;
import com.example.greenplate.views.InputMonthlyActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.example.greenplate.models.Firebase;
import com.example.greenplate.models.User;
import androidx.lifecycle.ViewModel;
import java.util.HashMap;



/**
 * ViewModel class to handle Firebase authentication operations.
 * This class provides functionalities to check user authentication status
 * and retrieve FirebaseAuth instances for further authentication operations.
 */
public class FirebaseViewModel extends ViewModel {
    /**
     * Singleton instance of Firebase to access Firebase services.
     */
    private static Firebase firebase;
    private static User user;
    private static FirebaseViewModel viewModel;
    private static final String DEFAULT_USER_ID = "local-hardcoded-user";
    private static final String DEFAULT_USER_NAME = "Local User";
    private static final String DEFAULT_USER_EMAIL = "local.user@fixaplate.local";

    /**
     * Constructs a new FirebaseViewModel and initializes the Firebase services.
     */
    private FirebaseViewModel() {
        firebase = Firebase.getInstance();
        if (firebase.getAuth().getCurrentUser() != null) {
            loadUser();
        } else {
            user = createHardcodedUser();
        }
    }

    public static FirebaseViewModel getInstance() {
        if (viewModel == null) {
            viewModel = new FirebaseViewModel();
        }
        return viewModel;
    }

    public static void loadUser() {
        if (firebase.getAuth().getCurrentUser() == null) {
            user = createHardcodedUser();
            return;
        }

        String email = firebase.getAuth().getCurrentUser().getEmail();
        Dictionary<String, String> userInfo = new Hashtable<>();
        ArrayList<String> mealIds = new ArrayList();
        ArrayList<String> ingredientIds = new ArrayList<>();
        ArrayList<ShoppingListItem> shoppingListItems = new ArrayList<>();
        firebase.getDatabase().getReference().child("users").orderByChild("email")
                .equalTo(email).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            if (child.getKey().equals("mealIds")) {
                                for (String mealId : ((HashMap<String, String>) child.getValue())
                                        .values()) {
                                    mealIds.add(String.valueOf(mealId));
                                }
                            } else if (child.getKey().equals("shoppingList")) {
                                HashMap<String, HashMap<String, Object>> childValues
                                        = (HashMap<String, HashMap<String, Object>>)
                                        child.getValue();
                                System.out.println(childValues);
                                for (String shoppingListItemId : childValues.keySet()) {
                                    //System.out.println(shoppingListItemId);
                                    shoppingListItems.add(new ShoppingListItem(shoppingListItemId,
                                            (String) childValues.get(shoppingListItemId)
                                                    .get("name"), ((Long) childValues
                                            .get(shoppingListItemId).
                                            get("quantity")).intValue()));
                                }
                            } else {
                                userInfo.put(child.getKey(), child.getValue().toString());
                            }

                        }
                        user = new User(userInfo.get("name"),
                                new int[] {Integer.valueOf(userInfo.get("heightInInches")),
                                        Integer.valueOf(userInfo.get("weight"))},
                                userInfo.get("gender"),
                                userInfo.get("userId"), userInfo.get("email"), mealIds,
                                shoppingListItems);
                        IngredientsViewModel.fetchIngredients(user);
                        RecipeViewModel.fetchRecipes(user);
                        firebase.getDatabase().getReference().child("meals")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        HashMap<String, Integer> dateMeals = new HashMap<>();
                                        HashMap<String, HashMap<String, Object>> childValues =
                                                (HashMap<String, HashMap<String, Object>>)
                                                        dataSnapshot.getValue();
                                            System.out.println(childValues);
                                            for (String mealId : childValues.keySet()) {
                                                System.out.println(mealId);
                                                System.out.println(childValues.get(mealId));
                                                if (user.getMealIds().contains((String) childValues
                                                        .get(mealId).get("mealId"))) {
                                                    if (dateMeals.containsKey((String) childValues
                                                            .get(mealId).get("dateAdded"))) {
                                                        dateMeals.put((String) childValues
                                                                        .get(mealId)
                                                                        .get("dateAdded"),
                                                                ((Long) childValues.get(mealId)
                                                                        .get("calories")).intValue()
                                                                        + dateMeals.get((String)
                                                                        childValues.get(mealId)
                                                                        .get("dateAdded")));
                                                    } else {
                                                        dateMeals.put((String) childValues
                                                                        .get(mealId)
                                                                        .get("dateAdded"),
                                                                ((Long) childValues.get(mealId)
                                                                        .get("calories"))
                                                                        .intValue());
                                                    }
                                                }
                                            }
                                        System.out.println(dateMeals);
                                        user.setMeals(dateMeals);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                        System.out.println(dataSnapshot.getKey());
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        System.out.println(dataSnapshot.getKey());
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
                        System.out.println(dataSnapshot.getKey());
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        System.out.println(error);
                    }
                });

    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return true if a user is logged in, false otherwise.
     */
    public static boolean isUserLoggedIn() {
        return true;
    }

    /**
     * Retrieves the FirebaseAuth instance for managing user authentication.
     *
     * @return The FirebaseAuth instance.
     */
    public static FirebaseAuth getAuth() {
        return firebase.getAuth();
    }

    public User createUser(String userId, String name, String email) {
        user = new User(name, userId, email);
        return user;
    }

    public void addPersonalInformation(int weight, String gender, int heightInInches) {
        user.addPersonalInformation(heightInInches, weight, gender);
    }

    public String getPersonalInformation() {
        if (user != null && user.getHeightInInches() != 0 && !user.getGender().isEmpty() && user
                .getWeight() != 0) {
            return "" + user.getHeight() + " | " + user.getWeight() + "lbs | " + user.getGender();
        } else {
            return "Fill out personal information";
        }
    }

    public String[] getPersonalInformationArray() {
        if (user != null && user.getHeightInInches() != 0 && !user.getGender().isEmpty()
                && user.getWeight() != 0) {
            return new String[] {String.valueOf(user.getHeightInInches()),
                    String.valueOf(user.getWeight()), user.getGender()};
        } else {
            return null;
        }
    }

    public String getCalorieGoal() {
        if (user != null && user.getHeightInInches() != 0 && !user.getGender().isEmpty()
                && user.getWeight() != 0) {
            return "" + user.getDailyCalorieIntake();
        } else {
            return "Fill out personal information";
        }
    }

    public void addMealToUser(String mealId) {
        user.addMeal(mealId);
    }

    public User getUser() {
        if (user == null) {
            user = createHardcodedUser();
        }
        return user;
    }

    private static User createHardcodedUser() {
        return new User(DEFAULT_USER_NAME, DEFAULT_USER_ID, DEFAULT_USER_EMAIL);
    }

    public boolean saveOrUpdateMeal(Meal meal) {

    public boolean saveOrUpdateMeal(Meal meal) {
        if (meal != null && meal.getMealId() != null && !meal.getName().isEmpty()) {
            Meal existingMeal = localMealsById.get(meal.getMealId());
            if (existingMeal != null) {
                user.addCalories(existingMeal.getMealDateAdded(), -existingMeal.getCalories());
            } else {
                addMealToUser(meal.getMealId());
            }

            localMealsById.put(meal.getMealId(), meal);
            user.addCalories(meal.getMealDateAdded(), meal.getCalories());
            InputActivity.updateVisualization();
            InputMonthlyActivity.updateVisualization();
            Log.d("Meal Save", "Meal saved to local demo user");
            return true;
        }
        return false;
    }

    // Method to delete a meal from the database
    public void deleteMeal(String mealId) {
        if (mealId == null) {
            return;
        }

        Meal removedMeal = localMealsById.remove(mealId);
        user.getMealIds().remove(mealId);
        if (removedMeal != null) {
            user.addCalories(removedMeal.getMealDateAdded(), -removedMeal.getCalories());
            InputActivity.updateVisualization();
            InputMonthlyActivity.updateVisualization();
        }
    }
}
