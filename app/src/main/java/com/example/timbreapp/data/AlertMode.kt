package com.example.timbreapp.data

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlertModeConfig(
    @get:PropertyName("isButtonEnabled") @set:PropertyName("isButtonEnabled")
    var isButtonEnabled: Boolean = false,

    @get:PropertyName("isSensorEnabled") @set:PropertyName("isSensorEnabled")
    var isSensorEnabled: Boolean
) : Parcelable {
    constructor() : this(false, false)
}