package com.example.roombooking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roombooking.data.repository.RoomRepository
import com.example.roombooking.ui.screen.authentication.AuthenticationViewModel
import com.example.roombooking.ui.screen.room.RoomViewModel

class AuthenticationViewModelFactory(
    private val repository: RoomRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthenticationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthenticationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class RoomViewModelFactory(
    private val repository: RoomRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RoomViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
