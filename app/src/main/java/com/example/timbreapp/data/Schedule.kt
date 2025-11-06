package com.example.timbreapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScheduleConfig(
    val startTime: Int = 0,
    val endTime: Int = 0
) : Parcelable