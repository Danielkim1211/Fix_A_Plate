package com.example.greenplate.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
/**
 * Provides simplified access to Firebase authentication and database services.
 * This class encapsulates the initialization and retrieval of FirebaseAuth
 * and FirebaseDatabase instances to be used across the application.
 */

public final class Firebase {
    /**
     * Singleton instance of FirebaseDatabase used for database operations.
     * Initialized once upon the first request to ensure
     * efficient use of resources.
     */
    private static FirebaseDatabase database;

    /**
     * Singleton instance of FirebaseAuth used for authentication operations.
     * Initialized once upon the first request to manage
     * user authentication seamlessly.
     */
    private static FirebaseAuth mAuth;

    private static Firebase instance;

    /**
     * Initializes new instances of FirebaseAuth and FirebaseDatabase
     * if they have not been initialized already. This constructor ensures
     * that Firebase services can be accessed throughout the application.
     */
    private Firebase() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    public static Firebase getInstance() {
        if (instance == null) {
            instance = new Firebase();
        }
        return instance;
    }

    /**
     * Gets the FirebaseAuth instance for managing user authentication.
     *
     * @return The FirebaseAuth instance for the application.
     */
    public FirebaseAuth getAuth() {
        return mAuth;
    }

    /**
     * Gets the FirebaseDatabase instance for accessing the Firebase
     * Realtime Database.
     *
     * @return The FirebaseDatabase instance for the application.
     */
    public FirebaseDatabase getDatabase() {
        return database;
    }
}
