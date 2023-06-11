package com.example.mobile_dev.data.response
data class UserResponse (
    val token: String,
    val isVerified: Boolean? = null,
    val id: String? = null,
    val nama: String? = null,
    val foto: Any? = null,
    val tempatLahir: Any? = null,
    val noTelp: String? = null,
    val email: String? = null,
    val tanggalLahir: Any? = null
)