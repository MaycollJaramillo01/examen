package com.example.roombooking.data.model

data class UserCredentials(
    val user: String,
    val password: String
)

data class UserProfile(
    val user: String,
    val name: String,
    val lastname: String,
    val emailname: String
)
