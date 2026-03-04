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
    private static HashMap<String, Meal> localMealsById;
    private static final String DEFAULT_USER_ID = "local-hardcoded-user";
    private static final String DEFAULT_USER_NAME = "Demo User";
    private static final String DEFAULT_USER_EMAIL = "demo.user@fixaplate.local";

    /**
     * Constructs a new FirebaseViewModel and initializes the Firebase services.
     */
    private FirebaseViewModel() {
        firebase = Firebase.getInstance();
        user = createHardcodedUser();
        localMealsById = new HashMap<>();
    }

    public static FirebaseViewModel getInstance() {
        if (viewModel == null) {
            viewModel = new FirebaseViewModel();
        }
        return viewModel;
    }

    public static void loadUser() {
        // Demo mode: always use a hardcoded local user instead of Firebase-backed profile data.
        user = createHardcodedUser();
        localMealsById = new HashMap<>();
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
