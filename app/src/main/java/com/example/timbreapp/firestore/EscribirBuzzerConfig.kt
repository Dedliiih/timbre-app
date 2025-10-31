package com.example.timbreapp.firestore

import com.example.timbreapp.data.BuzzerConfig
import com.google.firebase.Firebase
import com.google.firebase.database.database

fun EscribirBuzzerConfigEnFirebase(
    field: String,
    value: BuzzerConfig,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val database = Firebase.database("https://iot-timbre-default-rtdb.firebaseio.com/")
    val myRef = database.getReference(field)

    myRef.setValue(value)
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { error ->
            onError("Error: ${error.message}")
        }
}