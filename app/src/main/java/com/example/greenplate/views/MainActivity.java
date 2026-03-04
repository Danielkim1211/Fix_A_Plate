package com.example.greenplate.views;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import com.example.greenplate.R;
import com.example.greenplate.viewmodels.FirebaseViewModel;

import android.content.Intent;
/**
 * MainActivity acts as a splash screen for the application,
 * determining whether a user is already logged in.
 * If a user is logged in, it redirects to the HomeActivity;
 * otherwise, it navigates to the LoginActivity.
 * This decision is made after a brief delay,
 * simulating a loading process.
 */

public class MainActivity extends AppCompatActivity {
    /**
     * Called when the activity is starting.
     * This method initializes the activity,
     * inflates the welcome screen layout,
     * and starts a new thread to handle the
     * delay and subsequent navigation based
     * on the user's login status.
     *
     * @param savedInstanceState If the activity is
     *                           being re-initialized
     *                           after previously being shut down,
     *                           this Bundle contains
     *                           the data it most recently
     *                           supplied in
     *                           onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
        FirebaseViewModel fvm = FirebaseViewModel.getInstance();
        new Thread(() -> {
            try {
                Thread.sleep(1000); // Simulate loading process with a delay.
                boolean userLoggedIn = FirebaseViewModel.isUserLoggedIn();
                if (userLoggedIn) {
                    Intent intent = new Intent(MainActivity.this,
                            HomeActivity.class);
                    startActivity(intent);
                } else {
                    // Navigate to LoginActivity if no user is logged in.
                    Intent intent = new Intent(MainActivity.this,
                            LoginActivity.class); // Restore the interrupted status
                    startActivity(intent);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
