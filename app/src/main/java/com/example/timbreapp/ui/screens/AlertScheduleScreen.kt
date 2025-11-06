package com.example.timbreapp.ui.screens

import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.timbreapp.ui.viewmodel.ScheduleViewModel
import androidx.navigation.NavController
import com.example.timbreapp.data.ScheduleConfig
import com.example.timbreapp.firestore.LeerFirebase
import com.example.timbreapp.ui.components.ScheduleSection
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertScheduleScreen(
    navController: NavController,
    scheduleViewModel: ScheduleViewModel = viewModel()
) {
    val (buttonConfig, isButtonLoading, buttonError) = LeerFirebase(
        field = "schedules/button",
        valueType = ScheduleConfig::class.java
    )

    val (sensorConfig, isSensorLoading, sensorError) = LeerFirebase(
        field = "schedules/sensor",
        valueType = ScheduleConfig::class.java
    )

    // Estados para el horairo del botón
    var buttonStartTime by remember { mutableStateOf("00:00") }
    var buttonEndTime by remember { mutableStateOf("00:00") }

    // Estados para el horario del sensor
    var sensorStartTime by remember { mutableStateOf("00:00") }
    var sensorEndTime by remember { mutableStateOf("00:00") }

    LaunchedEffect(buttonConfig) {
        buttonConfig?.let {
            buttonStartTime = scheduleViewModel.minutesToTime(it.startTime)
            buttonEndTime = scheduleViewModel.minutesToTime(it.endTime)
        }
    }

    LaunchedEffect(sensorConfig) {
        sensorConfig?.let {
         sensorStartTime = scheduleViewModel.minutesToTime(it.startTime)
         sensorEndTime = scheduleViewModel.minutesToTime(it.endTime)
        }
    }

    val context = LocalContext.current

    fun showTimePicker(time: String, onTimeSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        try {
            val (hour, minute) = time.split(":").map { it.toInt() }
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
        } catch (e: Exception) {

        }
        TimePickerDialog(
            context,
            { _, selectedHour: Int, selectedMinute: Int ->
                onTimeSelected(String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute))
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configurar horarios") },
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
        if (isButtonLoading || isSensorLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScheduleSection(
                    title = "Horario de activación del Botón",
                    description = "El timbre sonará al ser presionado solo durante este intervalo de tiempo",
                    startTime = buttonStartTime,
                    endTime = buttonEndTime,
                    onStartTimeClick = { showTimePicker(buttonStartTime) { newTime -> buttonStartTime = newTime } },
                    onEndTimeClick = { showTimePicker(buttonEndTime) { newTime -> buttonEndTime = newTime } }
                )

                Spacer(modifier = Modifier.height(32.dp))

                ScheduleSection(
                    title = "Horario de activación del Sensor",
                    description = "Se enviarán notificaciones de movimiento detectado durante este intervalo de tiempo",
                    startTime = sensorStartTime,
                    endTime = sensorEndTime,
                    onStartTimeClick = { showTimePicker(sensorStartTime) { newTime -> sensorStartTime = newTime } },
                    onEndTimeClick = { showTimePicker(sensorEndTime) { newTime -> sensorEndTime = newTime } }
                )

                Spacer(modifier = Modifier.padding(8.dp))


                Button(
                    onClick = {
                        scheduleViewModel.saveSchedule(
                            buttonStartTime = buttonStartTime,
                            buttonEndTime = buttonEndTime,
                            sensorStartTime = sensorStartTime,
                            sensorEndTime = sensorEndTime,
                            onSuccess = {
                                Toast.makeText(context, "Horario actualizado con éxito", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            },
                            onError = {
                                Toast.makeText(context, "Error al actualizar la melodía", Toast.LENGTH_LONG).show()
                            }
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar Cambios")
                }
            }
        }
    }
}

