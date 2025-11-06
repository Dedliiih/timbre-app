package com.example.timbreapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.timbreapp.data.AlertModeConfig
import com.example.timbreapp.firestore.LeerFirebase
import com.example.timbreapp.ui.navigation.Screen
import com.example.timbreapp.ui.viewmodel.AlertModesViewModel
import com.example.timbreapp.ui.components.AlertModeRow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertModesScreen(
    navController: NavController,
    alertModesViewModel: AlertModesViewModel = viewModel()
) {
    val (currentConfig, isLoading, readError) = LeerFirebase(
        field = "alert_modes",
        valueType = AlertModeConfig::class.java
    )

    val isWriting by alertModesViewModel.isWriting.collectAsState()

    var isButtonEnabled by remember { mutableStateOf(false) }
    var isSensorEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(currentConfig) {
        currentConfig?.let {
            isButtonEnabled = it.isButtonEnabled
            isSensorEnabled = it.isSensorEnabled
        }
    }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Screen.AlertModes.title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading && currentConfig == null) {
                    CircularProgressIndicator()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    AlertModeRow(
                        title = "Activar Botón del Timbre",
                        description = "Permite que el timbre suene al ser presionado.",
                        isChecked = isButtonEnabled,
                        onCheckedChange = { isButtonEnabled = it }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                    AlertModeRow(
                        title = "Activar Sensor de Movimiento",
                        description = "Permite recibir notificaciones de movimiento.",
                        isChecked = isSensorEnabled,
                        onCheckedChange = { isSensorEnabled = it }
                    )

                    Spacer(modifier = Modifier.padding(16.dp))

                    Button(
                        onClick = {
                            val newConfig = AlertModeConfig(
                                isButtonEnabled = isButtonEnabled,
                                isSensorEnabled = isSensorEnabled
                            )
                            alertModesViewModel.saveAlertMode(
                                config = newConfig,
                                onSuccess = {
                                    Toast.makeText(context, "Modos de alerta actualizados", Toast.LENGTH_SHORT).show()
                                },
                                onError = { errorMsg ->
                                    Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_LONG).show()
                                }
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isWriting && !isLoading
                    ) {
                        if (isWriting) {
                            CircularProgressIndicator()
                        } else {
                            Text("Guardar Cambios")
                        }
                    }
                }
            }
        }
    }
}

