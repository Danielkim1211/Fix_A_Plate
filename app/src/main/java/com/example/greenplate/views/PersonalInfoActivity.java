package com.example.greenplate.views;

import com.example.greenplate.R;
import com.example.greenplate.viewmodels.FirebaseViewModel;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class PersonalInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info_page);
        // Initialize navigation buttons and set their onClickListeners.
        final ImageButton toHome = findViewById(R.id.toHomePage);
        final int[] genderSelection = {0};
        EditText heightText = findViewById(R.id.personal_info_height_enter);
        EditText weightText = findViewById(R.id.personal_info_weight_enter);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radioButtonMale = findViewById(R.id.radioButtonMale);
        RadioButton radioButtonFemale = findViewById(R.id.radioButtonFemale);
        FirebaseViewModel fvm = FirebaseViewModel.getInstance();
        String[] info = fvm.getPersonalInformationArray();
        if (info != null) {
            heightText.setText(info[0]);
            weightText.setText(info[1]);
            if (info[2].equals("Male")) {
                radioButtonMale.toggle();
            } else {
                radioButtonFemale.toggle();
            }
        }
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInfoActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toInput = findViewById(R.id.toInputPage);
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInfoActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInfoActivity.this,
                        RecipeActivityAtoZ.class);
                startActivity(intent);
            }
        });
        final ImageButton toIngredients = findViewById(R.id.toIngredientsPage);
        toIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInfoActivity.this,
                        IngredientsActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);
        toShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInfoActivity.this,
                        ShoppingActivity.class);
                startActivity(intent);
            }
        });

        final Button saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginActivity.checkInput(weightText.getText().toString())
                        && LoginActivity.checkInput(heightText.getText().toString())
                        && genderSelection[0] != 0) {
                    fvm.addPersonalInformation(Integer.valueOf(weightText.getText().toString()),
                            genderSelection[0] == 1 ? "Male" : "Female",
                            Integer.valueOf(heightText.getText().toString()));
                    Intent intent = new Intent(PersonalInfoActivity.this,
                            InputActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(PersonalInfoActivity.this, "Fill out all fields.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button cancelButton = findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInfoActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });



        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonMale) {
                genderSelection[0] = 1;
            } else if (checkedId == R.id.radioButtonFemale) {
                genderSelection[0] = 2;
            }
        });
    }

}

