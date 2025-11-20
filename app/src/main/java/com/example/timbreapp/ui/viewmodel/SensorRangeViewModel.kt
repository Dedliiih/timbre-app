package com.example.timbreapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timbreapp.data.SensorRangeConfig
import com.example.timbreapp.firestore.EscribirFirebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SensorRangeViewModel : ViewModel() {

    private val _isSaving = MutableStateFlow(false)
    val isSaving = _isSaving.asStateFlow()

    fun saveSensorRange(rangeValue: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isSaving.value = true
            val newConfig = SensorRangeConfig(range = rangeValue)
            EscribirFirebase(
                field = "sensor_range_config",
                value = newConfig,
                onSuccess = {
                    _isSaving.value = false
                    onSuccess()
                },
                onError = { errorMsg ->
                    _isSaving.value = false
                    onError(errorMsg)
                }
            )
        }
    }
}