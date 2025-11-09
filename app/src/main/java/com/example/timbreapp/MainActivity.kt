package com.example.timbreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.timbreapp.ui.theme.TimbreAppTheme
import com.example.timbreapp.ui.components.BottomNavBar
import com.example.timbreapp.ui.navigation.AppNavigation
import com.example.timbreapp.ui.navigation.MainNavigation
import com.example.timbreapp.ui.viewmodel.AuthViewModel
import com.example.timbreapp.ui.navigation.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimbreAppTheme (dynamicColor = false) {
              MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val authViewModel: AuthViewModel = viewModel()
    val navController = rememberNavController()

    MainNavigation(navController = navController, authViewModel = authViewModel)
}


