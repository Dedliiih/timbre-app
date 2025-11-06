package com.example.timbreapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Notifications: Screen("notifications", "Notificaciones", Icons.Default.Notifications)
    object Settings: Screen("settings", "Ajustes", Icons.Default.Settings)
    object BuzzerSound: Screen("buzzer_sound_screen", "Sonido del timbre", Icons.Default.Campaign)

    object AlertSchedule: Screen("alert_schedule_screen", "Horario de alerta", Icons.Default.Campaign)
}
