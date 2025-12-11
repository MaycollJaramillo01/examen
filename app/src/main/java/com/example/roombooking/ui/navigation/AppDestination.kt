package com.example.roombooking.ui.navigation

sealed class AppDestination(val route: String) {
    data object Login : AppDestination("login")
    data object Rooms : AppDestination("rooms")
}
