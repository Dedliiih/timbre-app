package com.example.timbreapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timbreapp.data.AlertModeConfig
import com.example.timbreapp.firestore.EscribirFirebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlertModesViewModel : ViewModel() {
    private val _isWriting = MutableStateFlow(false)
    val isWriting = _isWriting.asStateFlow()

    fun saveAlertMode(
        config: AlertModeConfig,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            _isWriting.value = true
            EscribirFirebase(
                field = "alert_modes",
                value = config,
                onSuccess = {
                    _isWriting.value = false
                    onSuccess()
                },
                onError = { errorMsg ->
                    _isWriting.value = false
                    onError(errorMsg)
                }
            )
        }
    }
}