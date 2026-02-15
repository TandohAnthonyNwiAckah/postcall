# Post Call

This is a simple Android application that demonstrates how to make a network call to fetch posts and
display them in a list using Jetpack Compose.

## Features

* Fetches posts from a remote API.
* Displays posts in a scrollable list.
* Shows loading and error states.
* Uses `Retrofit` for networking and `ViewModel` for managing UI-related data.

## Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

* Make sure you have [Git](https://git-scm.com/downloads) installed on your machine.
* You will need [Android Studio](https://developer.android.com/studio) to open and run the project.

### Cloning

1. Open a terminal or command prompt.
2. Navigate to the directory where you want to store the project.
3. Run the following command to clone the repository:

   ```bash
   git clone https://github.com/TandohAnthonyNwiAckah/postcall.git
   ```

### Opening in Android Studio

1. Open Android Studio.
2. Select **File > Open** from the menu bar.
3. Navigate to the cloned `postcall` directory and select it.
4. Android Studio will open the project and automatically sync the Gradle files. Wait for the sync
   to complete.

### Running the Application

1. Once the Gradle sync is finished, you can run the application.
2. Select a device (emulator or physical device) from the dropdown menu in the toolbar.
3. Click the **Run 'app'** button (the green play icon) or select **Run > Run 'app'** from the menu.

## Architecture

The application follows the MVVM (Model-View-ViewModel) architecture pattern.

* **Model**: Represents the data and business logic. The `PostRepository` fetches data from the
  remote `APIService`.
* **View**: The UI of the application, built with Jetpack Compose. The `MainActivity` observes the
  `PostViewModel`.
* **ViewModel**: The `PostViewModel` holds and processes the UI-related data. It exposes the data to
  the View and handles user interactions.

## Libraries Used

* **Lifecycle ViewModel Compose**: For managing UI-related data in a lifecycle-conscious way.
* **Retrofit**: A type-safe HTTP client for Android and Java.
* **Gson Converter**: For converting JSON to Java objects and vice-versa.
* **OkHttp Logging Interceptor**: For logging HTTP request and response data.
* **Kotlin Coroutines**: For managing background threads with simplified code and reducing needs for
  callbacks.
