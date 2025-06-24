package com.example.ui.feature.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.feature.club.model.ClubModel
import com.example.domain.feature.player.model.PlayerModel
import com.example.domain.feature.user.model.UserModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenView(onSuccessNavigate: () -> Unit) {

    val registerViewModel = koinViewModel<RegisterViewModel>()
    val registerState by registerViewModel.state.collectAsStateWithLifecycle()
    var user by remember { mutableStateOf(UserModel()) }
    var password by remember { mutableStateOf("") }
    var isAdmin by remember { mutableStateOf(false) }
    var player by remember { mutableStateOf(PlayerModel()) }
    var club by remember { mutableStateOf(ClubModel()) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Registro") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            UserInfoForm(user = user, onUserChange = { user = it }, password = password, onPasswordChange = { password = it })

            RoleSelector(isAdmin = isAdmin, onRoleSelected = { isAdmin = it })

            if (isAdmin) {
                ClubForm(club = club, onClubChange = { club = it })
            }

            PlayerForm(player = player, onPlayerChange = { player = it })

            SubmitButton(isAdmin = isAdmin, onClick = {
                val userToRegister = user.copy(password = password, rol = if (isAdmin) "admin" else "player")
                if (isAdmin) {
                    registerViewModel.registerAdmin(
                        user = userToRegister,
                        club = club.copy(adminUserId = listOf(user.id ?: ""))
                    )
                } else {
                    registerViewModel.registerPlayer(
                        user = userToRegister,
                        player = player
                    )
                }
            })

            RegisterStatusMessage(state = registerState, onSuccess = onSuccessNavigate)
        }
    }
}

@Composable
fun UserInfoForm(user: UserModel, onUserChange: (UserModel) -> Unit, password: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        value = user.name,
        onValueChange = { onUserChange(user.copy(name = it)) },
        label = { Text(text = "Nombre") },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) }
    )

    OutlinedTextField(
        value = user.lastName,
        onValueChange = { onUserChange(user.copy(lastName = it)) },
        label = { Text(text = "Apellido") },
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = user.email,
        onValueChange = { onUserChange(user.copy(email = it)) },
        label = { Text(text = "Correo electrónico") },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) }
    )

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(text = "Contraseña") },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation(),
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) }
    )
}

@Composable
fun RoleSelector(isAdmin: Boolean, onRoleSelected: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = !isAdmin, onClick = { onRoleSelected(false) })
        Text(text = "Jugador")
        Spacer(modifier = Modifier.width(16.dp))
        RadioButton(selected = isAdmin, onClick = { onRoleSelected(true) })
        Text(text = "Administrador")
    }
}

@Composable
fun ClubForm(club: ClubModel, onClubChange: (ClubModel) -> Unit) {
    OutlinedTextField(
        value = club.name,
        onValueChange = { onClubChange(club.copy(name = it)) },
        label = { Text(text = "Nombre del club") },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(imageVector = Icons.Default.Flag, contentDescription = null) }
    )

    OutlinedTextField(
        value = club.description,
        onValueChange = { onClubChange(club.copy(description = it)) },
        label = { Text(text = "Descripción del club") },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(imageVector = Icons.Default.Info, contentDescription = null) }
    )
}

@Composable
fun PlayerForm(player: PlayerModel, onPlayerChange: (PlayerModel) -> Unit) {
    OutlinedTextField(
        value = player.number.toString(),
        onValueChange = { onPlayerChange(player.copy(number = it.toIntOrNull() ?: 0)) },
        label = { Text(text = "Número") },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(imageVector = Icons.Default.FormatListNumbered, contentDescription = null) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )

    OutlinedTextField(
        value = player.position,
        onValueChange = { onPlayerChange(player.copy(position = it)) },
        label = { Text(text = "Posición") },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(imageVector = Icons.Default.SportsSoccer, contentDescription = null) }
    )
}

@Composable
fun SubmitButton(isAdmin: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(imageVector = Icons.Default.Check, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = if (isAdmin) "Registrar administrador" else "Registrar jugador")
    }
}

@Composable
fun RegisterStatusMessage(state: RegisterState, onSuccess: () -> Unit) {
    when (state) {
        is RegisterState.Loading -> CircularProgressIndicator()
        is RegisterState.Success -> {
            Text(text = "Registro exitoso", color = MaterialTheme.colorScheme.primary)
            LaunchedEffect(Unit) { onSuccess() }
        }
        is RegisterState.Error -> {
            Text(text = "Error: ${state.message}", color = MaterialTheme.colorScheme.error)
        }
        else -> {}
    }
}