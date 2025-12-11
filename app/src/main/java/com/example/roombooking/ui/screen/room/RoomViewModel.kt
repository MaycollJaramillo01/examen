package com.example.roombooking.ui.screen.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roombooking.data.model.RoomModel
import com.example.roombooking.data.repository.RoomRepository
import com.example.roombooking.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RoomState(
    val rooms: List<RoomModel> = emptyList(),
    val selectedUser: String = "",
    val newRoomName: String = "",
    val isLoading: Boolean = false,
    val message: String? = null
)

class RoomViewModel(private val repository: RoomRepository) : ViewModel() {
    private val _state = MutableStateFlow(RoomState())
    val state: StateFlow<RoomState> = _state

    fun updateSelectedUser(user: String) {
        _state.value = _state.value.copy(selectedUser = user)
    }

    fun updateNewRoomName(name: String) {
        _state.value = _state.value.copy(newRoomName = name)
    }

    fun loadRooms() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            when (val result = repository.fetchRooms()) {
                is Result.Success -> _state.value = _state.value.copy(
                    rooms = result.data.data.orEmpty(),
                    isLoading = false,
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

    fun createRoom() {
        if (_state.value.newRoomName.isBlank()) {
            _state.value = _state.value.copy(message = "Room name cannot be empty")
            return
        }
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            when (val result = repository.createRoom(_state.value.newRoomName)) {
                is Result.Success -> {
                    val updatedList = _state.value.rooms + listOfNotNull(result.data.data)
                    _state.value = _state.value.copy(
                        rooms = updatedList,
                        newRoomName = "",
                        isLoading = false,
                        message = result.data.message
                    )
                }

                is Result.Error -> _state.value = _state.value.copy(
                    isLoading = false,
                    message = result.message
                )

                Result.Loading -> _state.value = _state.value.copy(isLoading = true)
            }
        }
    }

    fun bookRoom(room: RoomModel) {
        val user = _state.value.selectedUser.ifBlank { return }
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            when (val result = repository.bookRoom(room.id, user)) {
                is Result.Success -> handleRoomUpdate(result.data.message, result.data.data)
                is Result.Error -> _state.value = _state.value.copy(isLoading = false, message = result.message)
                Result.Loading -> _state.value = _state.value.copy(isLoading = true)
            }
        }
    }

    fun unbookRoom(room: RoomModel) {
        val user = _state.value.selectedUser.ifBlank { return }
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            when (val result = repository.unbookRoom(room.id, user)) {
                is Result.Success -> handleRoomUpdate(result.data.message, result.data.data)
                is Result.Error -> _state.value = _state.value.copy(isLoading = false, message = result.message)
                Result.Loading -> _state.value = _state.value.copy(isLoading = true)
            }
        }
    }

    fun clearMessage() {
        _state.value = _state.value.copy(message = null)
    }

    private fun handleRoomUpdate(message: String, updatedRoom: RoomModel?) {
        val refreshedRooms = updatedRoom?.let { room ->
            _state.value.rooms.map { existing -> if (existing.id == room.id) room else existing }
        } ?: _state.value.rooms
        _state.value = _state.value.copy(rooms = refreshedRooms, isLoading = false, message = message)
    }
}
