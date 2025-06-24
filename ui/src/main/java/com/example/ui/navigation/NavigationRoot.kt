package com.example.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.example.domain.feature.club.model.ClubModel
import com.example.domain.feature.user.model.UserModel
import com.example.ui.feature.dashboard.DashboardScreenView
import com.example.ui.feature.login.LoginScreenView
import com.example.ui.feature.signup.SignUpScreenView
import com.example.ui.feature.register.RegisterScreenView
import com.example.ui.navigation.HomeNavKeys.HomeScreen
import com.example.ui.navigation.HomeNavKeys.LoginScreen
import com.example.ui.navigation.HomeNavKeys.RegisterScreen
import com.example.ui.navigation.HomeNavKeys.DashboardScreen

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {

    val backStack = rememberNavBackStack(HomeScreen)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        entryProvider = { key ->
            when (key) {
                is HomeScreen -> NavEntry(key = key) {
                    LoginScreenView(
                        onSignUpClick = { backStack.add(LoginScreen) },
                        onRegisterClick = { backStack.add(RegisterScreen)}
                    )
                }
                is LoginScreen -> NavEntry(key = key) {
                    SignUpScreenView(
                        onSignUpClick = { backStack.add(DashboardScreen(UserModel(), ClubModel())) },
                        onRegisterClick = { backStack.add(HomeScreen)}
                    )
                }
                is RegisterScreen -> NavEntry(key = key) {
                    RegisterScreenView(onSuccessNavigate = { backStack.add(DashboardScreen(UserModel(), ClubModel())) })
                }
                is DashboardScreen -> NavEntry(key = key) {
                    DashboardScreenView(user = key.user, club = key.club)
                }
                else -> throw RuntimeException("Unknown NavKey: $key")
            }
        }
    )
}