package com.example.mobile_dev.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Catalog : Screen("catalog")
    object Camera : Screen("camera")
    object Class : Screen("class")
    object Profile : Screen("profile")
    object DetailFranchise : Screen("home/{franchiseId}") {
        fun createRoute(franchiseId: String) = "home/$franchiseId"
    }
}