package com.example.timbreapp.data

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SensorRangeConfig(
    @get:PropertyName("range") @set:PropertyName("range")
    var range: Int = 50
) : Parcelable {
    constructor() : this(50)
}