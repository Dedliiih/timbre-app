package com.example.timbreapp.data

data class BuzzerConfig(
    val melody: List<Int> = emptyList(),
    val durations: List<Float> = emptyList()
 )

data class MelodyOption(
    val name: String,
    val buzzerConfig: BuzzerConfig
)