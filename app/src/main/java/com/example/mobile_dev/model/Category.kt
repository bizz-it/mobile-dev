package com.example.mobile_dev.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.mobile_dev.R

data class Category(
    @DrawableRes val imageCategory: Int,
    @StringRes val textCategory: Int
)