package com.example.timbreapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
   private val auth: FirebaseAuth = Firebase.auth

   private val _isLoading = MutableStateFlow(false)
   val isLoading = _isLoading.asStateFlow()

   private val _currentUser = MutableStateFlow(auth.currentUser)
   val currentUser = _currentUser.asStateFlow()

    fun signIn(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                _currentUser.value = auth.currentUser
                onSuccess()
            } catch (e: Exception) {
                onError("Las credenciales son inválidas" ?: "Error desconocido")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun signOut() {
        auth.signOut()
        _currentUser.value = null
    }
}