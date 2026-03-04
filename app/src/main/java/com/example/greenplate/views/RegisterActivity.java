package com.example.greenplate.views;

import static com.example.greenplate.views.LoginActivity.checkInput;
import com.example.greenplate.models.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.greenplate.R;
import com.example.greenplate.viewmodels.FirebaseViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;

/**
 * RegisterActivity provides an interface for new users
 * to register an account within the application.
 * It collects user details such as email, password,
 * and name within the text fields,
 * then communicates with Firebase to create
 * a new user account. Additionally, it includes
 * validation to ensure all fields are filled correctly and
 * the entered passwords match.
 */

public class RegisterActivity extends AppCompatActivity {

    /**
     * Initializes the activity, setting up the
     * UI components from the activity_register layout.
     * This method configures the register button
     * with a click listener to handle the registration process,
     * including input validation and account
     * creation with Firebase authentication.
     *
     * @param savedInstanceState If the activity
     *                           is being re-initialized
     *                           after previously being shut down,
     *                           this Bundle contains
     *                           the data it most recently
     *                           supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     *                           This bundle can be
     *                           used to recreate the activity
     *                           as it was prior to
     *                           being paused or stopped.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = findViewById(R.id.register);
        EditText emailInput = findViewById(R.id.emailAddress);
        EditText passwordInput = findViewById(R.id.passwordCreation);
        EditText passwordInputConfirmation =
                findViewById(R.id.passwordConfirmation);
        EditText nameInput = findViewById(R.id.fullName);
        FirebaseViewModel fvm = FirebaseViewModel.getInstance();
        final User[] user = new User[1];

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                String passwordConfirmation = passwordInputConfirmation.getText().toString();
                String name = nameInput.getText().toString();

                if (!(checkInput(email) && checkInput(password)
                        && checkInput(passwordConfirmation) && checkInput(name))) {
                    Toast.makeText(RegisterActivity.this, "Fill out all fields.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!passwordConfirmation.equals(password)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseAuth mAuth = fvm.getAuth();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this,
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            String userId = UUID.randomUUID().toString();
                                            user[0] = fvm.createUser(userId, name, email);
                                            Intent intent = new Intent(
                                                    RegisterActivity.this, HomeActivity.class);
                                            FirebaseViewModel.loadUser();
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(RegisterActivity.this,
                                                    "Account creation failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
            }
        });
    }

    /**
     * Handles button clicks for the RegisterActivity,
     * primarily used to navigate back to the LoginActivity.
     *
     * @param v The view that was clicked.
     */
    public void onClick(View v) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
