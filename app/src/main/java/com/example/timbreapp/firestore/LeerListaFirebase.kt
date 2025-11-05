package com.example.timbreapp.firestore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

@Composable
fun <T: Any> LeerListaFirebase(
    field: String,
    valueType: Class<T>
): Triple<List<T>, Boolean, String?> {
    var itemList by remember { mutableStateOf<List<T>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = field) {
        val database = Firebase.database
        val myRef = database.getReference(field)

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    itemList = emptyList()
                    isLoading = false
                    return
                }
                try {
                    val items = snapshot.children.mapNotNull { dataSnapshot ->
                        dataSnapshot.getValue(valueType)

                    }

                    itemList = items.reversed()
                    isLoading = false
                    errorMessage = null
                } catch (e: Exception) {
                    errorMessage = "Error parsing data: ${e.message}"
                    isLoading = false

                }
            }

            override fun onCancelled(error: DatabaseError) {
                errorMessage = "Error: ${error.message}"
                isLoading = false
            }
        }
        myRef.addValueEventListener(listener)
    }

    return Triple(itemList, isLoading, errorMessage)
}
