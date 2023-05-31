package com.example.mobile_dev.data.response

data class UserResponse (
    val token: String,
    val isVerified: Boolean? = null,
    val id: String? = null
)