package com.example.domain.entity.azkar

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class AzkarRespons(
    val category: String,
    val count: String,
    val description: String,
    val reference: String,
    val zekr: String
) :Parcelable