package com.example.timbreapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.timbreapp.data.Notification
import com.example.timbreapp.ui.components.NotificationItem
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import com.example.timbreapp.data.FirebaseNotification
import com.example.timbreapp.data.NotificationType
import com.example.timbreapp.firestore.LeerFirebase
import com.example.timbreapp.firestore.LeerListaFirebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private fun formatTimestamp(timestamp: Long): String {
    if (timestamp == 0L) return "Fecha desconocida"

    try {
        val spanishLocale = Locale.Builder().setLanguage("es").setRegion("ES").build()
        val sdf = SimpleDateFormat("dd 'de' MMM 'de' yyyy, 'a las' HH:mm", spanishLocale)
        val netDate = Date(timestamp)
        return sdf.format(netDate)
    } catch (e: Exception) {
        return "Fecha desconocida"
    }
}

@Composable
fun NotificationsScreen(modifier: Modifier = Modifier) {
    val (firebaseList, isLoading, errorMessage) = LeerListaFirebase(
        field = "notifications",
        valueType = FirebaseNotification::class.java

    )


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        Text(
            text = "Historial de Notificaciones",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = if (isLoading || errorMessage != null) Alignment.Center else Alignment.TopCenter
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (errorMessage != null) {
                Text(text = "Error: $errorMessage")
            } else if (firebaseList.isEmpty()) {
                Text("No hay notificaciones para mostrar")
            } else {
                val notificationList = firebaseList.map { firebaseNotification ->
                    Notification(
                        notificationType = NotificationType.createNotificationFromId(firebaseNotification.notificationType ?: "DOORBELL"),
                        date = formatTimestamp(firebaseNotification.date ?: 0L)
                    )
            }
                LazyColumn(
                    modifier = modifier.padding(start = 16.dp, end = 16.dp)
                ) {
                    items(notificationList) { notification  ->
                        NotificationItem(alert = notification)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
        }


    }
}
}
