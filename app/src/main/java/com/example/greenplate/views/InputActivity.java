package com.example.greenplate.views;

import com.example.greenplate.R;
import com.example.greenplate.models.Meal;
import com.example.greenplate.viewmodels.FirebaseViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import java.util.ArrayList;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * InputActivity is designed to serve as the user interface for
 * inputting new data into the application.
 * This could include new recipes, ingredients details,
 * or other forms of information relevant to the application's
 * functionality.
 * The activity also includes navigation buttons to
 * other pages of the app, such as the home page,
 * recipe page, ingredients page, and the shopping page,
 * facilitating easy movement across activities within the app.
 */

public class InputActivity extends AppCompatActivity {
    private static int caloriesConsumed;
    private static int caloriesRecommended;
    /**
     * Initializes the activity, sets the content view
     * from the input_page layout, and configures
     * the navigation buttons. Each button is
     * associated with an OnClickListener to navigate
     * to the respective activity, allowing
     * the user to easily access different parts of the application.
     *
     * @param savedInstanceState If the activity is being re-initialized
     *                           after previously being shut down,
     *                           this Bundle contains the data it
     *                           most recently supplied in
     *                           onSaveInstanceState(Bundle).
     *                           Otherwise, it is null. This
     *                           bundle can be used to recreate the activity
     *                           as it was prior to being paused or stopped.
     */

    private static TextView dateLabel;
    private static Calendar calendar;
    private static BarChart mBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_page);
        final EditText mealNameInput = findViewById(R.id.input_page_meal_enter);
        final EditText caloriesInput = findViewById(R.id.input_page_calorie_enter);
        final ImageButton toHome = findViewById(R.id.toHomePage);
        FirebaseViewModel fvm = FirebaseViewModel.getInstance();
        TextView userInfo = findViewById(R.id.userInfoLabel);
        TextView calorieGoal = findViewById(R.id.calorieGoalText);
        userInfo.setText(fvm.getPersonalInformation());
        calorieGoal.setText(fvm.getCalorieGoal());
        caloriesRecommended = FirebaseViewModel.getInstance().getUser().getDailyCalorieIntake();

        mBarChart = findViewById(R.id.barChart);

        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toInput = findViewById(R.id.toInputPage);
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this,
                        RecipeActivityAtoZ.class);
                startActivity(intent);
            }
        });
        final ImageButton toIngredients = findViewById(R.id.toIngredientsPage);
        toIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this,
                        IngredientsActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);
        toShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this, ShoppingActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toInputMonthly = findViewById(R.id.toInputMonthly);
        toInputMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this,
                        InputMonthlyActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toInputDaily = findViewById(R.id.toInputDaily);
        toInputDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this,
                        InputMonthlyActivity.class);
                startActivity(intent);
            }
        });

        final ImageButton submit = findViewById(R.id.input_page_submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mealName = mealNameInput.getText().toString().trim();
                String caloriesString = String.valueOf(caloriesInput.getText()).trim();
                if (!caloriesString.isEmpty()) {
                    int calories = Integer.parseInt(caloriesInput.getText().toString().trim());
                    if (mealName.isEmpty() || caloriesString.isEmpty()) {
                        Toast.makeText(InputActivity.this, "Please enter valid meal "
                                + "name " + "and calories.", Toast.LENGTH_LONG).show();
                        return; // Stop further execution
                    }
                    String dateAdded = new SimpleDateFormat("yyyy-MM-dd",
                            Locale.getDefault()).format(new Date());
                    Meal meal = new Meal(UUID.randomUUID().toString(), mealName, calories,
                            dateAdded);
                    fvm.saveOrUpdateMeal(meal); // Use FirebaseViewModel to save the meal
                    mealNameInput.setText(""); //clears input boxes
                    caloriesInput.setText(""); //clears input boxes
                    updateVisualization();
                } else {
                    Toast.makeText(InputActivity.this,
                            "Please enter valid meal name and calories.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        final ImageButton arrow = findViewById(R.id.arrow);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this,
                        PersonalInfoActivity.class);
                startActivity(intent);
            }
        });


        XAxis xAxis = mBarChart.getXAxis();  // Customize X-axis
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Position of X-axis labels
        xAxis.setGranularity(1.45f); // Interval between each label
        xAxis.setCenterAxisLabels(true); // Center the labels between the bars
        xAxis.setAxisMinimum(0.5f); // Adjust the minimum value to center the first bar
        xAxis.setValueFormatter(new IndexAxisValueFormatter(
                new String[]{"Calories Consumed", "Calorie Goal"})); // Customizing labels
        dateLabel = findViewById(R.id.dayLabel);
        calendar = Calendar.getInstance();
        updateVisualization();
        final ImageButton backwardsTime = findViewById(R.id.left_arrow_input_page);
        backwardsTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                updateVisualization();
            }
        });
        final ImageButton forwardsTime = findViewById(R.id.right_arrow_input_page);
        forwardsTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                updateVisualization();
            }
        });
    }
    public static void updateVisualization() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd", Locale.getDefault());
        String formattedDate = sdf.format(calendar.getTime());
        dateLabel.setText(formattedDate);
        SimpleDateFormat firebaseDate = new SimpleDateFormat("YYYY-MM-dd");
        String firebaseDateInput = firebaseDate.format(calendar.getTime());
        System.out.println(firebaseDateInput);
        caloriesConsumed = FirebaseViewModel.getInstance().getUser()
                .getCaloriesForDay(firebaseDateInput);
        //FirebaseViewModel.getInstance().queryMealsByDateCalories(firebaseDateInput);
        System.out.println(caloriesConsumed);
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1f, new float[]{caloriesConsumed}));
        entries.add(new BarEntry(2f, new float[]{caloriesRecommended}));
        System.out.println(entries);
        BarDataSet dataSet = new BarDataSet(entries, "Calories");
        dataSet.setColors(ColorTemplate.rgb("#D64933")); // Setting color to red
        BarData barData = new BarData(dataSet);
        mBarChart.setData(barData);
        mBarChart.invalidate();
    }
}
