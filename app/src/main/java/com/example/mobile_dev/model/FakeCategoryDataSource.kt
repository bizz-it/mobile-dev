package com.example.mobile_dev.model

import com.example.mobile_dev.R

object FakeCategoryDataSource {
    val dummyCategory = listOf(
        R.drawable.fnb_icon to R.string.category_fnb,
        R.drawable.retail_icon to R.string.category_retail,
        R.drawable.education_icon to R.string.category_education,
    ).map { Category(it.first, it.second) }
}