package com.example.timbreapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HomeMini
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home: Screen("home_screen", title="Inicio", Icons.Default.Home)
    object Login: Screen("login_screen", "Inicio de Sesión", Icons.Default.Login)
    object Notifications: Screen("notifications", "Notificaciones", Icons.Default.Notifications)
    object Settings: Screen("settings", "Ajustes", Icons.Default.Settings)
    object BuzzerSound: Screen("buzzer_sound_screen", "Sonido del timbre", Icons.Default.Campaign)
    object AlertSchedule: Screen("alert_schedule_screen", "Horario de alerta", Icons.Default.Schedule)
    object AlertModes: Screen("alert_modes_screen", "Modos de alerta", Icons.Default.ToggleOn)
    object MainApp: Screen("main_app", "Main", Icons.Default.HomeMini)
}
