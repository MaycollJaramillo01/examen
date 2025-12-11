package com.example.roombooking.data.model

data class ApiResponse<T>(
    val data: T?,
    val responseCode: String,
    val message: String
)

enum class ResponseCode {
    INFO_FOUND,
    INFO_NOT_FOUND,
    ERROR,
    UNKNOWN;

    companion object {
        fun fromString(value: String?): ResponseCode = when (value) {
            INFO_FOUND.name -> INFO_FOUND
            INFO_NOT_FOUND.name -> INFO_NOT_FOUND
            ERROR.name -> ERROR
            else -> UNKNOWN
        }
    }
}
