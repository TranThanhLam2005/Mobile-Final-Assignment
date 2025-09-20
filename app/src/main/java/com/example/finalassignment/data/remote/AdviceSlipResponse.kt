package com.example.finalassignment.data.remote

import kotlinx.serialization.Serializable


@Serializable
data class AdviceResponse(
    val slip: Slip
)

@Serializable
data class Slip(
    val id: Int,
    val advice: String
)