package com.example.timbreapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.timbreapp.data.BuzzerConfig
import com.example.timbreapp.data.MelodyOption
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import com.example.timbreapp.firestore.LeerFirebase
import com.example.timbreapp.firestore.EscribirFirebase
import com.example.timbreapp.ui.components.MelodyItem

private val melodyOptions = listOf(
    MelodyOption(
        name = "Melodía China",
        buzzerConfig = BuzzerConfig(
            melody = listOf(349, 349, 349, 349, 311, 311, 261, 261, 349),
            durations = listOf(8f, 8f, 8f, 8f, 4f, 4f, 4f, 4f, 1.5f)
        )
    ),
    MelodyOption(
        name = "Timbre Clásico",
        buzzerConfig = BuzzerConfig(
            melody = listOf(1047, 880),
            durations = listOf(4f, 2f)
        )
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuzzerSoundScreen(navController: NavController) {
    val (currentConfig, isReading, readError) = LeerFirebase(
        field = "buzzer_config",
        valueType = BuzzerConfig::class.java
    )

    var isWriting by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val isLoading = isReading || isWriting

    val handleSaveMelody = { melodyConfig: BuzzerConfig ->
        isWriting = true
        EscribirFirebase(
            field = "buzzer_config",
            value = melodyConfig,
            onSuccess = {
                isWriting = false
                Toast.makeText(context, "Melodía actualizada con éxito", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            },
            onError = { errorMessage ->
                isWriting = false
                Toast.makeText(context, "Error al guardar la melodía: $errorMessage", Toast.LENGTH_LONG).show()
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Seleccionar Sonido") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver a Configuración"
                        )
                    }
                }
            )
        }
    )
    { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (readError != null) {
                Text("Error al cargar: $readError")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(melodyOptions) { melody ->
                        val isActive = currentConfig != null && melody.buzzerConfig == currentConfig
                        MelodyItem(
                          melodyName = melody.name,
                            isActive = isActive,
                            onSelect = {
                                if (!isActive) {
                                    handleSaveMelody(melody.buzzerConfig)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
