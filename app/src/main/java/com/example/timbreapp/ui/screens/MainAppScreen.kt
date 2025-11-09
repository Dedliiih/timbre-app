package com.example.timbreapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.timbreapp.ui.components.BottomNavBar
import com.example.timbreapp.ui.navigation.AppNavigation
import com.example.timbreapp.ui.viewmodel.AuthViewModel

@Composable
fun MainAppScreen(authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        AppNavigation(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            authViewModel = authViewModel
        )
    }
}