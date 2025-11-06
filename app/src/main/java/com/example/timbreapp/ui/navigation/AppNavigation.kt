package com.example.timbreapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.timbreapp.ui.screens.AlertScheduleScreen
import com.example.timbreapp.ui.screens.BuzzerSoundScreen
import com.example.timbreapp.ui.screens.NotificationsScreen
import com.example.timbreapp.ui.screens.SettingsScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = Screen.Notifications.route) {
        composable(Screen.Notifications.route) {
            NotificationsScreen()
        }

        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }

        composable(Screen.BuzzerSound.route) {
            BuzzerSoundScreen(navController = navController)
        }

        composable(Screen.AlertSchedule.route) {
            AlertScheduleScreen(navController = navController)
        }
    }
}