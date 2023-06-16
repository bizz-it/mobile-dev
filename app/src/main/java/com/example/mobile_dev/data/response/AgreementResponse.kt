package com.example.mobile_dev.data.response

import com.google.gson.annotations.SerializedName

data class AgreementResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class Data(

	@field:SerializedName("agreement_document")
	val agreementDocument: String? = null,

	@field:SerializedName("franchise_package_id")
	val franchisePackageId: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("franchise_id")
	val franchiseId: String? = null,

	@field:SerializedName("users_location")
	val usersLocation: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("users_photo_location")
	val usersPhotoLocation: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
