package com.example.timbreapp

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("FCM", "Permiso de notificación otorgado")
        } else {
            Log.w("FCM", "Permiso de notificación denegado")
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun retrieveAndSaveFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Solicitud de token fallida", task.exception)
                return@addOnCompleteListener
            }

            val token = task.result
            Log.d("FCM", "Token: $token")

            authViewModel.saveFCMToken(token)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        askNotificationPermission()
        retrieveAndSaveFCMToken()
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


