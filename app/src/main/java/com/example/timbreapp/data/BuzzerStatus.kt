package com.example.timbreapp.data

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BuzzerStatus(
    @get:PropertyName("isEnabled") @set:PropertyName("isEnabled")
    var isEnabled: Boolean = true
) : Parcelable {
    constructor() : this(true)
}