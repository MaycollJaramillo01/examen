package com.example.roombooking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.roombooking.data.api.ApiProvider
import com.example.roombooking.data.repository.RoomRepository
import com.example.roombooking.ui.navigation.AppDestination
import com.example.roombooking.ui.screen.authentication.AuthenticationScreen
import com.example.roombooking.ui.screen.authentication.AuthenticationViewModel
import com.example.roombooking.ui.screen.room.RoomScreen
import com.example.roombooking.ui.screen.room.RoomViewModel
import com.example.roombooking.ui.theme.RoomBookingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomBookingTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    RoomBookingApp()
                }
            }
        }
    }
}

@Composable
fun RoomBookingApp() {
    val navController = rememberNavController()
    val repository = remember { RoomRepository(ApiProvider.roomApi) }

    val authenticationViewModel: AuthenticationViewModel = viewModel(factory = AuthenticationViewModelFactory(repository))
    val roomViewModel: RoomViewModel = viewModel(factory = RoomViewModelFactory(repository))

    NavHost(navController = navController, startDestination = AppDestination.Login.route) {
        composable(AppDestination.Login.route) {
            AuthenticationScreen(
                viewModel = authenticationViewModel,
                onAuthenticated = {
                    roomViewModel.updateSelectedUser(it.user)
                    roomViewModel.loadRooms()
                    navController.navigate(AppDestination.Rooms.route) {
                        popUpTo(AppDestination.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(AppDestination.Rooms.route) {
            RoomScreen(
                viewModel = roomViewModel,
                onLogout = {
                    navController.navigate(AppDestination.Login.route) {
                        popUpTo(AppDestination.Rooms.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
