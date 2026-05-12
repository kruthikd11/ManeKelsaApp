pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // JitPack is often required for specific UI libraries like Coil
        maven { url = uri("https://jitpack.io") }
    }
}

// Sets the project name and includes your app folder
rootProject.name = "ManeKelsaApp"
include(":app")