package com.example.roombooking.ui.screen.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.res.stringResource
import com.example.roombooking.data.model.UserProfile
import com.example.roombooking.R

@Composable
fun AuthenticationScreen(
    viewModel: AuthenticationViewModel,
    onAuthenticated: (UserProfile) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState: SnackbarHostState = rememberSnackbarHostState()

    LaunchedEffect(state.message) {
        state.message?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearMessage()
        }
    }

    LaunchedEffect(state.profile) {
        state.profile?.let(onAuthenticated)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.yen_login_title))
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.user,
            onValueChange = viewModel::updateUser,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.yen_username)) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.password,
            onValueChange = viewModel::updatePassword,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.yen_password)) },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = viewModel::authenticate,
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.height(20.dp))
            } else {
                Text(text = stringResource(id = R.string.yen_sign_in))
            }
        }
        TextButton(onClick = { viewModel.updateUser("estudiante") }) {
            Text(text = stringResource(id = R.string.yen_use_sample))
        }
    }
    SnackbarHost(hostState = snackbarHostState, modifier = Modifier.padding(16.dp))
}
