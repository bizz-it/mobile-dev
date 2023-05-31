package com.example.mobile_dev.data.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(

	@field:SerializedName("data")
	val value: Value? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null,

	@field:SerializedName("token")
	val token: String? = null
)

data class Value(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("foto")
	val foto: Any? = null,

	@field:SerializedName("tempat_lahir")
	val tempatLahir: Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("no_telp")
	val noTelp: String? = null,

	@field:SerializedName("is_verified")
	val isVerified: Boolean? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("tanggal_lahir")
	val tanggalLahir: Any? = null
)
