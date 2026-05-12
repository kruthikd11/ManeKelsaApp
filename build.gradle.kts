// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Top-level build file

plugins {
    // This defines the version of the Android Studio build tools (matched to your compileSdk 34)
    id("com.android.application") version "8.2.0" apply false

    // This version (1.9.22) is compatible with the Compose Compiler 1.5.8 we set in your app-level gradle
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false

    // This is required for Firebase (google-services.json) to work correctly
    id("com.google.gms.google-services") version "4.4.1" apply false

    // Required if you use Kapt for any room or dependency injection libraries
    id("org.jetbrains.kotlin.kapt") version "1.9.22" apply false
}