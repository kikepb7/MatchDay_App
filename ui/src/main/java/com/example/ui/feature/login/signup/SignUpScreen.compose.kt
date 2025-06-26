package com.example.ui.feature.login.signup

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.ui.R.string as RS
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignUpScreenView(
    onSignUpClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
) {
    val signUpViewModel = koinViewModel<SignUpViewModel>()
    val signUpState by signUpViewModel.state.collectAsStateWithLifecycle()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                signUpViewModel.registerWithGoogle(idToken = idToken)
            }
        } catch (e: ApiException) {
            Log.e("GoogleLogin", "Google sign in failed", e)
        }
    }

    val googleSignInClient = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(RS.default_web_client_id))
            .requestEmail()
            .build()
            .let { gso -> GoogleSignIn.getClient(context, gso) }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Iniciar sesión", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { signUpViewModel.signUpWithEmail(email = email, password = password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = signUpState !is SignUpState.Loading
        ) {
            Text(text = "Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = signUpState !is SignUpState.Loading
        ) {
            Text(text = "Iniciar sesión con Google")
        }

        TextButton(onClick = onRegisterClick) {
            Text("¿No tienes cuenta? Regístrate")
        }

        Spacer(Modifier.height(16.dp))

        when (val currentState = signUpState) {
            is SignUpState.Loading -> CircularProgressIndicator()
            is SignUpState.Error -> Text(
                text = currentState.message,
                color = MaterialTheme.colorScheme.error
            )
            is SignUpState.Success -> {
                LaunchedEffect(Unit) {
                    onSignUpClick(currentState.userId, currentState.clubId)
                }
            }
            else -> Unit
        }
    }
}