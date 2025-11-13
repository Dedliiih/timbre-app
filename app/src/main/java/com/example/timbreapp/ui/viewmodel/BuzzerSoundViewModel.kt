package com.example.timbreapp.ui.viewmodel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timbreapp.data.BuzzerConfig
import com.example.timbreapp.firestore.EscribirFirebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BuzzerSoundViewModel : ViewModel() {
    private val _isWriting = MutableStateFlow(false) // estado mutable
    val isWriting = _isWriting.asStateFlow() // estado inmutable

  fun saveBuzzerSound(
      melody: BuzzerConfig,
      onSuccess: () -> Unit,
      onError: (String) -> Unit
  ) {
     viewModelScope.launch {
         _isWriting.value = true

         EscribirFirebase(
             field = "buzzer_config",
             value = melody,
             onSuccess = {
                 _isWriting.value = false
                 onSuccess()
             },
             onError = { errorMessage ->
                 _isWriting.value = false
                 onError(errorMessage)
             }
         )
     }

  }
}