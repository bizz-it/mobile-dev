package com.example.mobile_dev.data.response

import com.google.gson.annotations.SerializedName

data class APIResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)
