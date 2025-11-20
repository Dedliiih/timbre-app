package com.example.timbreapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timbreapp.data.BuzzerStatus
import com.example.timbreapp.firestore.EscribirFirebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _isUpdating = MutableStateFlow(false)
    val isUpdating = _isUpdating.asStateFlow()

    fun setBuzzerEnabled(isEnabled: Boolean, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isUpdating.value = true
            val newStatus = BuzzerStatus(isEnabled = isEnabled)
            EscribirFirebase(
                field = "buzzer_status",
                value = newStatus,
                onSuccess = {
                    _isUpdating.value = false
                    onSuccess()
                },
                onError = { errorMsg ->
                    _isUpdating.value = false
                    onError(errorMsg)
                }
            )
        }
    }
}