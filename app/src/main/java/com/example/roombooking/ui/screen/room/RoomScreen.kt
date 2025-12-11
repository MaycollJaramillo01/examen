package com.example.roombooking.ui.screen.room

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.res.stringResource
import com.example.roombooking.data.model.RoomModel
import com.example.roombooking.R

@Composable
fun RoomScreen(
    viewModel: RoomViewModel,
    onLogout: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState: SnackbarHostState = rememberSnackbarHostState()

    LaunchedEffect(state.message) {
        state.message?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.yen_rooms_title)) },
                actions = {
                    IconButton(onClick = viewModel::loadRooms) {
                        Icon(Icons.Default.Refresh, contentDescription = stringResource(id = R.string.yen_refresh))
                    }
                    IconButton(onClick = onLogout) {
                        Text(text = stringResource(id = R.string.yen_logout))
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = state.selectedUser,
                    onValueChange = viewModel::updateSelectedUser,
                    modifier = Modifier.weight(1f),
                    label = { Text(text = stringResource(id = R.string.yen_acting_user)) }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = state.newRoomName,
                    onValueChange = viewModel::updateNewRoomName,
                    modifier = Modifier.weight(1f),
                    label = { Text(text = stringResource(id = R.string.yen_new_room_name)) }
                )
                IconButton(onClick = viewModel::createRoom) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.yen_create_room))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(state.rooms) { room ->
                    RoomCard(
                        room = room,
                        onBook = { viewModel.bookRoom(room) },
                        onUnbook = { viewModel.unbookRoom(room) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = viewModel::loadRooms, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Refresh, contentDescription = stringResource(id = R.string.yen_refresh))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.yen_refresh))
            }
        }
    }
}

@Composable
private fun RoomCard(
    room: RoomModel,
    onBook: () -> Unit,
    onUnbook: () -> Unit
) {
    val backgroundColor = if (room.reserved) Color(0xFFFFE4E1) else Color(0xFFE0FFE5)
    val subtitle = if (room.reserved) {
        stringResource(id = R.string.yen_reserved_by, room.reservedBy ?: "unknown")
    } else {
        stringResource(id = R.string.yen_available)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { if (room.reserved) onUnbook() else onBook() }
    ) {
        Column(modifier = Modifier
            .background(backgroundColor)
            .padding(16.dp)) {
            Text(text = room.name, fontWeight = FontWeight.Bold)
            Text(text = subtitle)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                if (room.reserved) {
                    Button(onClick = onUnbook, modifier = Modifier.weight(1f)) {
                        Text(text = stringResource(id = R.string.yen_release_room))
                    }
                } else {
                    Button(onClick = onBook, modifier = Modifier.weight(1f)) {
                        Text(text = stringResource(id = R.string.yen_reserve_room))
                    }
                }
            }
        }
    }
}
