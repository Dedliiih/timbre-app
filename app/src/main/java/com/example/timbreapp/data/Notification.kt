package com.example.timbreapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Doorbell
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NotificationType(val id: String, val displayName: String, val icon: ImageVector) {
    object Doorbell: NotificationType(
        id = "DOORBELL",
        displayName = "Timbre activado",
        icon = Icons.Default.Doorbell
    )

    object MotionDetected: NotificationType(
        id ="MOTION_DETECTED",
        displayName = "Movimiento detectado",
        icon = Icons.AutoMirrored.Filled.DirectionsWalk
    )

    companion object {
        fun createNotificationFromId(id: String): NotificationType {
            return when (id) {
                "DOORBELL" -> Doorbell
                "MOTION_DETECTED" -> MotionDetected
                else -> Doorbell
            }
        }
    }
}

data class Notification(
    val notificationType: NotificationType,
    val date: String
)

data class FirebaseNotification(
    val notificationType: String? = null,
    val date: String? = null
)