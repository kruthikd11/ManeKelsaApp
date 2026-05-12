package com.example.manekelsaapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    // This is the single source of truth for your data
    val mainViewModel: ManeKelsaViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        // 1. SPLASH SCREEN
        composable("splash") {
            SplashScreen(onTimeout = {
                navController.navigate("onboarding") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }

        // 2. ONBOARDING SCREEN
        composable("onboarding") {
            OnboardingScreen(onFinished = {
                navController.navigate("role_selection") {
                    popUpTo("onboarding") { inclusive = true }
                }
            })
        }

        // 3. ROLE SELECTION
        composable("role_selection") {
            RoleSelectionScreen(onRoleSelected = { role ->
                if (role == "resident") {
                    navController.navigate("home")
                } else {
                    navController.navigate("worker_login")
                }
            })
        }

        // 4. WORKER LOGIN
        composable("worker_login") {
            WorkerLoginScreen(
                viewModel = mainViewModel,
                navController = navController,
                onRegisterClick = {
                    navController.navigate("worker_registration")
                }
            )
        }

        // 5. WORKER REGISTRATION - FIXED HERE
        composable("worker_registration") {
            WorkerRegistrationScreen(
                viewModel = mainViewModel, // PASSING THE VIEWMODEL
                onBackClick = { navController.popBackStack() },
                onRegisterSuccess = {
                    // After successful registration, go to home or back to login
                    navController.navigate("home") {
                        popUpTo("role_selection") { inclusive = false }
                    }
                }
            )
        }

        // 6. RESIDENT HOME SCREEN
        composable("home") {
            ResidentHomeScreen(
                viewModel = mainViewModel,
                onWorkerClick = { id ->
                    navController.navigate("worker_detail/$id")
                }
            )
        }

        // 7. DETAIL SCREEN
        composable(
            route = "worker_detail/{workerId}",
            arguments = listOf(navArgument("workerId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("workerId") ?: ""
            WorkerDetailScreen(
                workerId = id,
                viewModel = mainViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        // 8. PROFILE SCREEN
        composable("profile") {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("ಪ್ರೊಫೈಲ್")
            }
        }
    }
}