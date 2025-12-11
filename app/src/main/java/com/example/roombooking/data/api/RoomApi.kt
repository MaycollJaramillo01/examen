package com.example.roombooking.data.api

import com.example.roombooking.data.model.ApiResponse
import com.example.roombooking.data.model.BookingRequest
import com.example.roombooking.data.model.CreateRoomRequest
import com.example.roombooking.data.model.RoomModel
import com.example.roombooking.data.model.UserCredentials
import com.example.roombooking.data.model.UserProfile
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface RoomApi {
    @POST("/users/auth")
    suspend fun authenticate(@Body credentials: UserCredentials): ApiResponse<UserProfile>

    @GET("/users/")
    suspend fun listUsers(): ApiResponse<List<UserProfile>>

    @GET("/rooms")
    suspend fun fetchRooms(): ApiResponse<List<RoomModel>>

    @POST("/rooms")
    suspend fun createRoom(@Body request: CreateRoomRequest): ApiResponse<RoomModel>

    @PUT("/rooms/booking")
    suspend fun bookRoom(@Body request: BookingRequest): ApiResponse<RoomModel>

    @PUT("/rooms/unbooking")
    suspend fun unbookRoom(@Body request: BookingRequest): ApiResponse<RoomModel>
}
