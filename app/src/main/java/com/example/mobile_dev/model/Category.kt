package com.example.mobile_dev.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.mobile_dev.R

data class Category(
    @DrawableRes val imageCategory: Int,
    @StringRes val textCategory: Int
)

val dummyCategory = listOf(
    R.drawable.fnb_icon to R.string.category_fnb,
    R.drawable.retail_icon to R.string.category_retail,
    R.drawable.education_icon to R.string.category_education,
).map { Category(it.first, it.second) }