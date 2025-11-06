package com.example.timbreapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.timbreapp.data.ScheduleConfig
import com.example.timbreapp.firestore.EscribirFirebase
import java.util.Locale

class ScheduleViewModel : ViewModel() {
    private fun timeToMinutes(timeString: String): Int {
        return try {
           val parts = timeString.split(":")
           val hours = parts[0].toInt()
           val minutes = parts[1].toInt()
           hours * 60 + minutes
        } catch (e: Exception) {
            0
        }
    }

    fun minutesToTime(totalMinutes: Int): String {
        if (totalMinutes < 0) return "00:00"
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60
        return String.format(Locale.getDefault(), "%02d:%02d", hours, minutes)
    }

    fun saveSchedule(
        buttonStartTime: String,
        buttonEndTime: String,
        sensorStartTime: String,
        sensorEndTime: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val buttonSchedule = ScheduleConfig(
            startTime = timeToMinutes(buttonStartTime),
            endTime = timeToMinutes(buttonEndTime)
        )

        val sensorSchedule = ScheduleConfig(
            startTime = timeToMinutes(sensorStartTime),
            endTime = timeToMinutes(sensorEndTime)
        )

        EscribirFirebase(
            field = "schedules/button",
            value = buttonSchedule,
            onSuccess = {
                EscribirFirebase(
                    field = "schedules/sensor",
                    value = sensorSchedule,
                    onSuccess = onSuccess,
                    onError = onError
                )
            },
            onError = onError

        )
    }
}