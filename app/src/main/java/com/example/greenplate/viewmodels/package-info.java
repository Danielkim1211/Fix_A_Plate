/**
 * Contains the ViewModel classes for the application, facilitating the separation of application
 * logic
 * from the UI. ViewModels in this package are responsible for preparing data for the UI and
 * reacting
 * to user interactions by updating the data model, specifically focusing on interactions with
 * Firebase services.
 *
 * <p>The ViewModels abstract the underlying Firebase authentication and database operations,
 * providing
 * a cleaner, more testable interface for the UI components to interact with. This includes tasks
 * such as
 * user authentication status checks and retrieval of FirebaseAuth instances.</p>
 *
 * <p>This package plays a crucial role in the application's architecture by adhering to the
 * Model-View-ViewModel (MVVM)
 * pattern, promoting a separation of concerns and enhancing the maintainability and scalability
 * of the application.</p>
 *
 * @since 1.0
 */
package com.example.greenplate.viewmodels;