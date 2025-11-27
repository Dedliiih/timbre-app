package com.example.timbreapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.timbreapp.data.Notification


@Composable
fun NotificationItem(alert: Notification) {
    Row(
     modifier = Modifier
         .fillMaxWidth()
         .clip(RoundedCornerShape(12.dp))
         .background(Color.White)
         .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = alert.notificationType.icon,
            contentDescription = alert.notificationType.displayName,
            modifier = Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = alert.notificationType.displayName,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Text(
                text = alert.date,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            alert.duration?.let { durationInSeconds ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Activado por $durationInSeconds segundos",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

