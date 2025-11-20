package com.example.timbreapp.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.timbreapp.data.BuzzerStatus
import com.example.timbreapp.firestore.LeerFirebase
import com.example.timbreapp.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel() // Inyectamos el ViewModel
) {
    val (buzzerStatus, isLoading, _) = LeerFirebase(
        field = "buzzer_status",
        valueType = BuzzerStatus::class.java
    )

    var isBuzzerEnabled by remember { mutableStateOf(false) }

    val isUpdating by homeViewModel.isUpdating.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(buzzerStatus) {
        buzzerStatus?.let {
            isBuzzerEnabled = it.isEnabled
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text= "Bienvenido",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        StatusCard(
            isOnline = isBuzzerEnabled,
            isLoading = isLoading && buzzerStatus == null
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Activar Sonido del Timbre", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = if (isBuzzerEnabled) "El timbre sonará al ser presionado" else "El timbre está silenciado",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(start = 16.dp))
            } else {
                Switch(
                    checked = isBuzzerEnabled,
                    onCheckedChange = { newCheckedState ->
                        isBuzzerEnabled = newCheckedState
                        homeViewModel.setBuzzerEnabled(
                            isEnabled = newCheckedState,
                            onSuccess = {
                               Toast.makeText(context, "Estado del buzzer actualizado", Toast.LENGTH_SHORT).show()
                            },
                            onError = { errorMsg ->
                                Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_LONG).show()
                                isBuzzerEnabled = !newCheckedState
                            }
                        )
                    },
                    enabled = !isUpdating
                )
            }
        }
    }
}

@Composable
fun StatusCard(isOnline: Boolean, isLoading: Boolean) {
    val onlineColor = Color(0xFF138aec)
    val offlineColor = Color(0xFFD32F2F)

    val statusColor = if (isOnline) onlineColor else offlineColor
    val connectionStatusText = if (isOnline) "En línea" else "Desconectado"
    val mainStatusText = if (isOnline) "Activado" else "Desactivado"
    val icon = if (isOnline) Icons.Default.NotificationsActive else Icons.Default.NotificationsOff

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AnimatedContent(
            targetState = isLoading,
            label = "Card Content Animation"
        ) { cardIsLoading ->
            if (cardIsLoading) {
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
                        .padding(24.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Estado del Timbre", style = MaterialTheme.typography.titleLarge)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(statusColor)
                            )
                            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                            Text(
                                text = connectionStatusText,
                                color = statusColor,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = "Estado del timbre",
                            modifier = Modifier.size(64.dp),
                            tint = statusColor
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = mainStatusText,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = statusColor
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}