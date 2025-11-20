package com.example.timbreapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.timbreapp.data.SensorRangeConfig
import com.example.timbreapp.firestore.LeerFirebase
import com.example.timbreapp.ui.navigation.Screen
import com.example.timbreapp.ui.viewmodel.SensorRangeViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorRangeScreen(
    navController: NavController,
    sensorRangeViewModel: SensorRangeViewModel = viewModel()
) {
    val (savedConfig, isLoading, _) = LeerFirebase(
        field = "sensor_range_config",
        valueType = SensorRangeConfig::class.java
    )

    var sliderValue by remember { mutableStateOf(5.0f) }
    val isSaving by sensorRangeViewModel.isSaving.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(savedConfig) {
        savedConfig?.let {
            sliderValue = it.range / 10f
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Screen.SensorRange.title) },
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
        if (isLoading && savedConfig == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Ajusta la sensibilidad del sensor",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Distancias menores activarán el sensor más fácilmente.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = "%.1f m".format(sliderValue),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))

                Slider(
                    value = sliderValue,
                    onValueChange = { newValue ->
                        sliderValue = (newValue * 10).roundToInt() / 10f
                    },
                    valueRange = 0f..10f,
                    steps = 99,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        sensorRangeViewModel.saveSensorRange(
                            rangeValue = (sliderValue * 10).roundToInt(),
                            onSuccess = {
                                Toast.makeText(context, "Rango actualizado", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            },
                            onError = { errorMsg ->
                                Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_LONG).show()
                            }
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isSaving
                ) {
                    if (isSaving) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Text("Guardar Cambios")
                    }
                }
            }
        }
    }
}
