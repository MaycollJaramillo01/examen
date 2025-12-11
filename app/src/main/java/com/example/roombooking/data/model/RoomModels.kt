package com.example.roombooking.data.model

data class RoomModel(
    val id: Int,
    val name: String,
    val reserved: Boolean,
    val reservedBy: String?
)

data class CreateRoomRequest(
    val name: String
)

data class BookingRequest(
    val id: Int,
    val user: String
)
