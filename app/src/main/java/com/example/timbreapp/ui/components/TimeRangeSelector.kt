package com.example.timbreapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.timbreapp.ui.components.TimePickerRow

@Composable
fun TimeRangeSelector(
    startTime: String,
    endTime: String,
    onStartTimeClick: () -> Unit,
    onEndTimeClick: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            TimePickerRow(label = "Desde", time = startTime, onClick = onStartTimeClick)
            Spacer(modifier = Modifier.height(16.dp))
            TimePickerRow(label = "Hasta", time = endTime, onClick = onEndTimeClick)
        }
    }
}