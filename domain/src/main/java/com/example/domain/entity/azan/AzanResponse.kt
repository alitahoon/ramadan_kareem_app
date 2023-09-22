package com.example.domain.entity.azan

data class AzanResponse(
    val code: Int,
    val `data`: List<Data>,
    val status: String
)