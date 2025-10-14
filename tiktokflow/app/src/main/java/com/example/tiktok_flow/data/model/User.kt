package com.example.tiktok_flow.data.model

data class User(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val city: String = "",
    val street: String = "",
    val country: String = "",
    val fullAddress: String = "",
    val role: String = "",
    val profileImageUrl: String = ""
)
