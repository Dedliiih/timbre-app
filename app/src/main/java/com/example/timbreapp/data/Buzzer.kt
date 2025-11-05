package com.example.timbreapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BuzzerConfig(
    val melody: List<Int> = emptyList(),
    val durations: List<Float> = emptyList()
) : Parcelable

@Parcelize
data class MelodyOption(
    val name: String,
    val buzzerConfig: BuzzerConfig
) : Parcelable
