package com.example.roombooking.data.repository

import com.example.roombooking.data.api.RoomApi
import com.example.roombooking.data.model.ApiResponse
import com.example.roombooking.data.model.BookingRequest
import com.example.roombooking.data.model.CreateRoomRequest
import com.example.roombooking.data.model.ResponseCode
import com.example.roombooking.data.model.RoomModel
import com.example.roombooking.data.model.UserCredentials
import com.example.roombooking.data.model.UserProfile
import com.example.roombooking.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomRepository(private val api: RoomApi) {
    suspend fun authenticate(credentials: UserCredentials): Result<ApiResponse<UserProfile>> = safeRequest {
        api.authenticate(credentials)
    }

    suspend fun fetchRooms(): Result<ApiResponse<List<RoomModel>>> = safeRequest {
        api.fetchRooms()
    }

    suspend fun createRoom(name: String): Result<ApiResponse<RoomModel>> = safeRequest {
        api.createRoom(CreateRoomRequest(name = name))
    }

    suspend fun bookRoom(id: Int, user: String): Result<ApiResponse<RoomModel>> = safeRequest {
        api.bookRoom(BookingRequest(id = id, user = user))
    }

    suspend fun unbookRoom(id: Int, user: String): Result<ApiResponse<RoomModel>> = safeRequest {
        api.unbookRoom(BookingRequest(id = id, user = user))
    }

    private suspend fun <T> safeRequest(block: suspend () -> ApiResponse<T>): Result<ApiResponse<T>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = block()
                val responseCode = ResponseCode.fromString(response.responseCode)
                if (responseCode == ResponseCode.INFO_FOUND) {
                    Result.Success(response)
                } else {
                    Result.Error(response.message)
                }
            } catch (exception: Exception) {
                Result.Error(exception.message ?: "Unknown error")
            }
        }
    }
}
