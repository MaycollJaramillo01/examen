package com.example.roombooking.ui.screen.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roombooking.data.model.UserCredentials
import com.example.roombooking.data.model.UserProfile
import com.example.roombooking.data.repository.RoomRepository
import com.example.roombooking.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthenticationState(
    val user: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val message: String? = null,
    val profile: UserProfile? = null
)

class AuthenticationViewModel(private val repository: RoomRepository) : ViewModel() {
    private val _state = MutableStateFlow(AuthenticationState())
    val state: StateFlow<AuthenticationState> = _state

    fun updateUser(value: String) {
        _state.value = _state.value.copy(user = value, message = null)
    }

    fun updatePassword(value: String) {
        _state.value = _state.value.copy(password = value, message = null)
    }

    fun authenticate() {
        val credentials = UserCredentials(user = _state.value.user, password = _state.value.password)
        _state.value = _state.value.copy(isLoading = true, message = null)
        viewModelScope.launch {
            when (val result = repository.authenticate(credentials)) {
                is Result.Success -> _state.value = _state.value.copy(
                    isLoading = false,
                    profile = result.data.data,
                    message = result.data.message
                )

                is Result.Error -> _state.value = _state.value.copy(
                    isLoading = false,
                    message = result.message
                )

                Result.Loading -> _state.value = _state.value.copy(isLoading = true)
            }
        }
    }

    fun clearMessage() {
        _state.value = _state.value.copy(message = null)
    }
}
