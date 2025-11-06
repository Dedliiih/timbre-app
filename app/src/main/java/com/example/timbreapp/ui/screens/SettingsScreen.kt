package com.example.timbreapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.timbreapp.ui.navigation.Screen
import com.example.timbreapp.ui.components.SettingItem

@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold{ paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SettingItem(
                title = Screen.BuzzerSound.title,
                description = "Cambia la melodía del buzzer",
                icon = Screen.BuzzerSound.icon,
                onClick = {
                    navController.navigate(Screen.BuzzerSound.route)
                }
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            SettingItem(
                title = Screen.AlertSchedule.title,
                description = "Configura el horario de alerta de tu timbre",
                icon = Screen.AlertSchedule.icon,
                onClick = {
                    navController.navigate(Screen.AlertSchedule.route)
                }
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

            SettingItem(
               title = Screen.AlertModes.title,
               description = "Configura los modos de alerta",
               icon = Screen.AlertModes.icon,
               onClick = {
                   navController.navigate(Screen.AlertModes.route)
               }
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

        }
    }
}

