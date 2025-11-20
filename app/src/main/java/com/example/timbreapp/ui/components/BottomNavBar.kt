package com.example.timbreapp.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.timbreapp.ui.navigation.Screen

@Composable
fun BottomNavBar(navController: NavController, modifier: Modifier = Modifier) {
    val items = listOf(
        Screen.Notifications,
        Screen.Home,
        Screen.Settings,
    )

    NavigationBar(
        containerColor = Color.White,
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true // Guarda estado de la pantalla
                        }
                        launchSingleTop = true // Evita crear varias instancias de la misma pantalla
                        restoreState = true // Restaura estado de la pantalla ya guardada
                    }
                }
            )
        }
    }
}