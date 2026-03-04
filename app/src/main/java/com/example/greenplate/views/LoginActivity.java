package com.example.greenplate.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greenplate.R;
import com.example.greenplate.viewmodels.FirebaseViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * LoginActivity manages the user login process,
 * allowing users to sign in to their accounts.
 * It provides input fields for username and password,
 * a login button to submit the credentials,
 * a sign-up button to navigate to the registration
 * screen, and an exit button to close the app.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * TextView for entering the username.
     * This field is initialized in the onCreate method
     * and is used to capture the user's input for their username.
     */
    private TextView usernameTextView;

    /**
     * TextView for entering the password.
     * Similar to the usernameTextView, this field is
     * initialized in the onCreate method and
     * is used to capture the user's input for their password.
     */
    private TextView passwordTextView;

    /**
     * Initializes the activity, inflates the
     * login screen layout, and sets up event listeners
     * for the login, sign-up, and exit buttons.
     * It also initializes {@link FirebaseViewModel}
     * for handling authentication operations with Firebase.
     *
     * @param savedInstanceState If the activity is being
     *                           re-initialized after
     *                           previously being shut down,
     *                           this Bundle contains the
     *                           data it most recently
     *                           supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen_activity);
        usernameTextView = findViewById(R.id.usernameEditText);
        passwordTextView = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        Button signUpButton = findViewById(R.id.signUpButton);
        Button exitButton = findViewById(R.id.exitButton);
        FirebaseViewModel fvm = FirebaseViewModel.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTextView.getText().toString();
                String password = passwordTextView.getText().toString();

                if (checkInput(username) && checkInput(password)) {
                    FirebaseAuth mAuth = fvm.getAuth();
                    mAuth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(LoginActivity.this,
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Home screen
                                                System.out.println("Success");
                                                Toast.makeText(LoginActivity.this,
                                                        "Account found.",
                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(LoginActivity.this,
                                                        HomeActivity.class);
                                                FirebaseViewModel.loadUser();
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(LoginActivity.this,
                                                        "Account not found.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Fill out all fields.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Checks if the input text is non-null and
     * not empty after trimming whitespace.
     *
     * @param text The string input to check.
     * @return true if the input is valid
     * (non-null and not empty), false otherwise.
     */
    public static boolean checkInput(String text) {
        return text != null && !text.trim().isEmpty();
    }
}
