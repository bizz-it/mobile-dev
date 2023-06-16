package com.example.mobile_dev.data.response

import com.google.gson.annotations.SerializedName

data class FranchiseResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class FranchiseCategory(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("category")
	val category: String? = null
)

data class FranchisePackagesItem(

	@field:SerializedName("package")
	val jsonMemberPackage: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("franchise_id")
	val franchiseId: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class DataItem(

	@field:SerializedName("franchise_category_id")
	val franchiseCategoryId: String? = null,

	@field:SerializedName("total_gerai")
	val totalGerai: Int? = null,

	@field:SerializedName("franchise_packages")
	val franchisePackages: List<FranchisePackagesItem?>? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("requirement")
	val requirement: String? = null,

	@field:SerializedName("franchise_category")
	val franchiseCategory: FranchiseCategory? = null,

	@field:SerializedName("dokumen")
	val dokumen: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("foto")
	val foto: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("logo")
	val logo: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null
)
