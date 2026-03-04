package com.example.greenplate;

import static com.example.greenplate.models.Recipe.isValidRecipeName;
import static com.example.greenplate.views.LoginActivity.checkInput;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.greenplate.models.Meal;
import com.example.greenplate.models.ShoppingListItem;
import com.example.greenplate.models.SortingStrategy;
import com.example.greenplate.models.User;
import com.example.greenplate.models.Recipe;
import com.example.greenplate.models.Ingredient;
import com.example.greenplate.viewmodels.ShoppingListViewModel;
import com.example.greenplate.viewmodels.SortByName;
import com.example.greenplate.viewmodels.SortByReverseName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testNull() {
        assertEquals(checkInput(null), false);
    }

    @Test
    public void testStrongPassword() {
        assertEquals(checkInput("Test123!"), true);
    }

    @Test
    public void testWeakPassword() {
        assertEquals(checkInput("Hi"), true);
    }

    @Test
    public void testSpacesString() {
        assertEquals(checkInput("   "), false);
    }

    @Test
    public void testEmptyString() {
        assertEquals(checkInput(""), false);
    }

    @Test
    public void testNullEmail() {
        assertEquals(checkInput(null), false);
    }

    @Test
    public void testUserCreation() {
        User user = new User("Test User", 150, "Male", 70, "test-user", "test@gmail.com");
        assertNotNull(user);
    }

    @Test
    public void testMaleCalorieCount() {
        User user = new User("Test User", 120, "Male", 68, "test-user", "test@gmail.com");
        assertEquals(user.getDailyCalorieIntake(), 1629);
    }

    @Test
    public void testFemaleCalorieCount() {
        User user = new User("Test User", 110, "Female", 64, "test-user", "test@gmail.com");
        assertEquals(user.getDailyCalorieIntake(), 1354);
    }

    @Test
    public void testHeight() {
        User user = new User("Test User", 110, "Female", 64, "test-user", "test@gmail.com");
        assertEquals(user.getHeight(), "5' 4" + "\"");
    }

    @Test
    public void testMealMap() {
        Meal meal = new Meal("testID", "testName", 500, "03-12-2024");
        HashMap<String, Object> result = new HashMap<>();
        result.put("mealId", "testID");
        result.put("name", "testName");
        result.put("calories", 500);
        result.put("dateAdded", "03-12-2024");
        assertEquals(meal.toMap(), result);
    }

    @Test
    public void testMaleCalorieCount2() {
        //testing if a male and female of the same height and weight output different calories
        User user = new User("Test User", 346, "Male", 72, "test-user", "test@gmail.com");
        assertEquals(user.getDailyCalorieIntake(), 2717);
    }

    @Test
    public void testFemaleCalorieCount2() {
        //testing if a male and female of the same height and weight output different calories
        User user = new User("Test User", 346, "Female", 72, "test-user", "test@gmail.com");
        assertEquals(user.getDailyCalorieIntake(), 2551);
    }

    @Test
    public void testMealEquality() {
        Meal meal1 = new Meal("123", "Dummy Meal", 500, "2024-03-12");
        Meal meal2 = new Meal("123", "Dummy Meal", 500, "2024-03-12");
        assertEquals(meal1, meal2);
    }

    @Test
    public void testMealConstructor() {
        Meal meal = new Meal("123", "Test Meal", 350, "2024-03-15");
        assertEquals("123", meal.getMealId());
        assertEquals("Test Meal", meal.getName());
        assertEquals(350, meal.getCalories());
        //assertEquals("2024-03-15", meal.dateAdded);
    }

    public void updateUserPersonalInformation_updatesInfoCorrectly() {
        User user = new User("Jane Doe", "user123", "jane.doe@example.com");
        user.addPersonalInformation(65, 130, "Female");

        assertEquals(65, user.getHeightInInches());
        assertEquals(130, user.getWeight());
        assertEquals("Female", user.getGender());
    }

    public void getUserMap_outputsCorrectMap() {
        User user = new User("Jane Doe", 130, "Female", 65, "user123", "jane.doe@example.com");
        Map<String, Object> userMap = user.getUserMap();

        assertEquals("Jane Doe", userMap.get("name"));
        assertEquals(130, userMap.get("weight"));
        assertEquals("Female", userMap.get("gender"));
        assertEquals(65, userMap.get("heightInInches"));
        assertEquals("user123", userMap.get("userId"));
        assertEquals("jane.doe@example.com", userMap.get("email"));
        // Assuming mealIds is an empty list initially
        assertTrue(((ArrayList) userMap.get("mealIds")).isEmpty());
    }


    // SPRINT 3 UNIT TESTS

    // AUSTIN
    @Test
    public void decreaseIngredientQuantityWithReturn() {
        Ingredient potatoes = new Ingredient("Potatoes", 120, 2, "94h98vb3-bvgevfi2-ef34g");
        assertEquals(potatoes.decreaseQuantity(), 1);
    }

    @Test
    public void increaseIngredientQuantity() {
        Ingredient potatoes = new Ingredient("Potatoes", 120, 2, "94h98vb3-bvgevfi2-ef34g");
        potatoes.increaseQuantity();
        assertEquals(potatoes.getQuantity(), 3);
    }

    // ANYA

    @Test
    public void testValidIngredientName() {
        assertEquals(checkInput("Tomato"), true);
    }

    @Test
    public void testInvalidIngredientNameWithWhitespace() {
        assertEquals(checkInput("   "), false);
    }

    @Test
    public void testValidQuantity() {
        assertEquals(checkQuantity("2"), true);
    }

    @Test
    public void testInvalidQuantityWithNonNumeric() {
        assertEquals(checkQuantity("abc"), false);
    }

    @Test
    public void testValidDate() {
        assertEquals(checkDateFormat("2024-03-12"), true);
    }

    @Test
    public void testInvalidDateWithWrongFormat() {
        assertEquals(checkDateFormat("12-03-2024"), false);
    }

    public static boolean checkQuantity(String quantity) {
        try {
            int qty = Integer.parseInt(quantity);
            // Quantity must be a positive integer
            return qty > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkDateFormat(String date) {
        // Check if the date matches the expected format (YYYY-MM-DD)
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }


    // SUBHA
    @Test
    public void testRecipeConstructor() {
        HashMap<String, Integer> ingredients = new HashMap<>();
        ingredients.put("Bread", 2);
        ingredients.put("cheese", 1);
        Recipe recipe = new Recipe("Grilled cheese", ingredients);
        assertEquals("Grilled cheese", recipe.getRecipeName());
        assertEquals(ingredients, recipe.getIngredients());
    }

    @Test
    public void testIngredientConstructor() {
        Ingredient ingredient = new Ingredient("Milk", 50, 4, "2023-06-30", "subha", "milk");
        assertEquals("Milk", ingredient.getName());
        assertEquals(50, ingredient.getCalories());
        assertEquals(4, ingredient.getQuantity());
        assertEquals("subha", ingredient.getUserId());
        assertEquals("milk", ingredient.getId());
    }

    // NATHAN
    @Test
    public void testSorting() {
        Recipe[] testList = {new Recipe("B", null), new Recipe("A", null), new Recipe("C", null)};
        SortingStrategy testSort = new SortByName();
        Recipe[] sortedList = testSort.sortRecipes(testList);
        assertEquals("A", sortedList[0].getRecipeName());
        assertEquals("B", sortedList[1].getRecipeName());
        assertEquals("C", sortedList[2].getRecipeName());
    }

    @Test
    public void testReverseSorting() {
        Recipe[] testList = {new Recipe("A", null), new Recipe("B", null), new Recipe("C", null)};
        SortingStrategy testSort = new SortByReverseName();
        Recipe[] sortedList = testSort.sortRecipes(testList);
        assertEquals("C", sortedList[0].getRecipeName());
        assertEquals("B", sortedList[1].getRecipeName());
        assertEquals("A", sortedList[2].getRecipeName());
    }

    // DANIEL
    @Test
    public void testRecipeToMapIncludesAllIngredients() {
        HashMap<String, Integer> ingredients = new HashMap<>();
        ingredients.put("Salt", 1);
        ingredients.put("Pepper", 2);
        Recipe recipe = new Recipe("Seasoning Mix", ingredients);

        Map<String, Object> recipeMap = recipe.toMap();

        assertEquals("Seasoning Mix", recipeMap.get("recipeName"));
        Map<String, String> ingredientsMap = (Map<String, String>) recipeMap.get("ingredients");
        assertNotNull(ingredientsMap);
        assertEquals("1", ingredientsMap.get("Salt"));
        assertEquals("2", ingredientsMap.get("Pepper"));
    }

    @Test
    public void testAddingIngredientUpdatesRecipe() {
        HashMap<String, Integer> ingredients = new HashMap<>();
        ingredients.put("Sugar", 1);
        Recipe recipe = new Recipe("Sweet Mix", ingredients);

        recipe.getIngredients().put("Cinnamon", 1);
        assertEquals(2, recipe.getIngredients().size());
        assertTrue(recipe.getIngredients().containsKey("Sugar"));
        assertTrue(recipe.getIngredients().containsValue(new Integer(1)));
    }

    // KUSHAL
    @Test
    public void testIngredient() {
        Ingredient ingredient = new Ingredient("Tomato", 25, 3, "2024-12-31", "user123", "ingredient123");
        assertEquals("Tomato", ingredient.getName());
        assertEquals(25, ingredient.getCalories());
        assertEquals(3, ingredient.getQuantity());
        assertEquals("user123", ingredient.getUserId());
        assertEquals("ingredient123", ingredient.getId());
    }

    @Test
    public void testRecipe() {
        HashMap<String, Integer> ingredients = new HashMap<>();
        ingredients.put("Ingredient1", 2);
        ingredients.put("Ingredient2", 3);
        Recipe recipe = new Recipe("TestRecipe", ingredients);
        assertEquals("TestRecipe", recipe.getRecipeName());
        assertEquals(ingredients, recipe.getIngredients());
    }

    // SPRINT 4

    // NATHAN - SPRINT 4
    @Test
    public void testObserverSetAvailable() {
        Ingredient testIngredient = new Ingredient("test ingredient");
        testIngredient.setAvailable(true);
        assertTrue(testIngredient.isAvailable());
    }

    @Test
    public void testObserverUpdate() {
        HashMap<String, Integer> map1 = new HashMap<String, Integer>();
        HashMap<String, Integer> map2 = new HashMap<String, Integer>();
        map1.put("a", 5);
        map1.put("b", 10);
        map1.put("c", 15);
        map2.put("a", 5);
        map2.put("b", 10);
        map2.put("c", 15);
        map2.put("testIngredient", 0);
        Recipe testRecipe = new Recipe("testRecipe", map1);
        Ingredient testIngredient = new Ingredient("testIngredient");
        testRecipe.addIngredients(testIngredient);
        assertEquals(testRecipe.getIngredients(), map2);
    }
    // AUSTIN - SPRINT 4
    @Test
    public void testShoppingListItemDecreaseQuantity() {
        ShoppingListItem item1 = new ShoppingListItem("Eggs", 12, 30);
        item1.decreaseQuantity();
        assertEquals(11, item1.getQuantity());
        assertEquals(30, item1.getCalories());
    }

    @Test
    public void testShoppingListItemIncreaseQuantity() {
        ShoppingListItem item1 = new ShoppingListItem("Eggs", 12, 30);
        item1.increaseQuantity();
        assertEquals(13, item1.getQuantity());
        assertEquals(30, item1.getCalories());
    }

    // SUBHA - SPRINT 4
    @Test
    public void testGetCaloriesForDay() {
        User user = new User("John Doe", 180, "Male", 72, "123456", "john.doe@example.com");
        user.addCalories("2024-04-01", 2000);
        user.addCalories("2024-04-02", 2200);
        user.addCalories("2024-04-03", 1800);
        user.addCalories("2024-04-04", 2500);
        assertEquals(2000, user.getCaloriesForDay("2024-04-01"));
        assertEquals(2200, user.getCaloriesForDay("2024-04-02"));
        assertEquals(1800, user.getCaloriesForDay("2024-04-03"));
        assertEquals(2500, user.getCaloriesForDay("2024-04-04"));
    }

    @Test
    public void testGetCaloriesForMonth() {
        User user = new User("John Doe", 180, "Male", 72, "123456", "john.doe@example.com");
        user.addCalories("2024-04-01", 2000);
        user.addCalories("2024-04-02", 2200);
        user.addCalories("2024-04-03", 1800);
        user.addCalories("2024-04-04", 2500);
        HashMap<Integer, Integer> expectedCalories = new HashMap<>();
        expectedCalories.put(1, 2000);
        expectedCalories.put(2, 2200);
        expectedCalories.put(3, 1800);
        expectedCalories.put(4, 2500);

        assertEquals(expectedCalories, user.getCaloriesForMonth("04", "2024"));
    }



    // KUSHAL - SPRINT 4
    @Test
    public void testShoppingListItemConstructor() {
        ShoppingListItem item = new ShoppingListItem("Apple", 3, 80);
        assertEquals("Apple", item.getName());
        assertEquals(3, item.getQuantity());
    }

    @Test
    public void testToMap() {
        ShoppingListItem item = new ShoppingListItem("Orange",5);
        Map<String, Object> expectedMap = new HashMap<>();
        expectedMap.put("name", "Orange");
        expectedMap.put("quantity", 5);
        expectedMap.put("calories", 0);

        assertEquals(expectedMap, item.toMap());
    }

    // DANIEL - SPRINT 4
    @Test
    public void testShoppingListItem_SetName() {
        ShoppingListItem item = new ShoppingListItem("Milk", 1, 400);
        item.setName("Eggs");
        assertEquals("Eggs", item.getName());
    }
    @Test
    public void testShoppingListItem_ToMap() {
        ShoppingListItem item = new ShoppingListItem("Milk", 2, 170);

        Map<String, Object> map = item.toMap();

        // Check the map contains the correct values
        assertEquals("Milk", map.get("name"));
        assertEquals(2, map.get("quantity"));
    }


    // ANYA - SPRINT 4

    @Test
    public void testRecipeNameWithSpecialCharacters() {
        String recipeName = "Chicken Curry!";
        boolean isValid = isValidRecipeName(recipeName);
        assertFalse(isValid);
    }

    @Test
    public void testRecipeNameWithSpecialCharactersAndSpaces() {
        String recipeName = "Chocolate Cake @ Home";
        boolean isValid = isValidRecipeName(recipeName);
        assertEquals(false, isValid);
    }

    @Test
    public void testRecipeNameWithNumbers() {
        String recipeName = "Spaghetti 123";
        boolean isValid = isValidRecipeName(recipeName);
        assertEquals(true, isValid);
    }
}