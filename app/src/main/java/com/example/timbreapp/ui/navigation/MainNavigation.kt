package com.example.timbreapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.timbreapp.MainAppScreen
import com.example.timbreapp.ui.screens.LoginScreen
import com.example.timbreapp.ui.viewmodel.AuthViewModel

@Composable
fun MainNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    key (currentUser) {
        val startDestination = if (currentUser != null) "main_app" else Screen.Login.route

        NavHost(navController = navController, startDestination = startDestination) {
            composable(Screen.Login.route) {
                LoginScreen(
                    authViewModel = authViewModel,
                    onLoginSuccess = {
                        navController.navigate("main_app") {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
            }

            composable("main_app") {
                MainAppScreen(authViewModel = authViewModel)
            }
        }
    }
}