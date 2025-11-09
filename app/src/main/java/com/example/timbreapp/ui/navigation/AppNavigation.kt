package com.example.timbreapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.timbreapp.ui.screens.AlertModesScreen
import com.example.timbreapp.ui.screens.AlertScheduleScreen
import com.example.timbreapp.ui.screens.BuzzerSoundScreen
import com.example.timbreapp.ui.screens.LoginScreen
import com.example.timbreapp.ui.screens.NotificationsScreen
import com.example.timbreapp.ui.screens.SettingsScreen
import com.example.timbreapp.ui.viewmodel.AuthViewModel

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier, authViewModel: AuthViewModel) {
    NavHost(navController = navController, startDestination = Screen.Notifications.route) {
        composable(Screen.Notifications.route) {
            NotificationsScreen()
        }

        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController, authViewModel = authViewModel)
        }

        composable(Screen.BuzzerSound.route) {
            BuzzerSoundScreen(navController = navController)
        }

        composable(Screen.AlertSchedule.route) {
            AlertScheduleScreen(navController = navController)
        }

        composable(Screen.AlertModes.route) {
            AlertModesScreen(navController = navController)
        }
    }
}