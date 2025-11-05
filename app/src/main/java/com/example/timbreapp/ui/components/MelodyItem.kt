package com.example.timbreapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MelodyItem(melodyName: String, isActive: Boolean, onSelect: () -> Unit) {
    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = melodyName, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)

            if (isActive) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Activado",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Melodía activada",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                Button(onClick = onSelect) {
                    Text("Seleccionar")
                }
            }


        }
    }
}
