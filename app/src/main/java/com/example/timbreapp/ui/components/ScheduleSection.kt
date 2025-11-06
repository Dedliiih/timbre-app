package com.example.timbreapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.timbreapp.ui.components.TimeRangeSelector

@Composable
fun ScheduleSection(
    title: String,
    description: String,
    startTime:  String,
    endTime: String,
    onStartTimeClick: () -> Unit,
    onEndTimeClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
        )
        TimeRangeSelector(
            startTime = startTime,
            endTime = endTime,
            onStartTimeClick = onStartTimeClick,
            onEndTimeClick = onEndTimeClick
        )
    }
}