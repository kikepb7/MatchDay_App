package com.example.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.example.ui.feature.dashboard.DashboardScreenView
import com.example.ui.feature.home.LoginScreenView
import com.example.ui.feature.home.login.SignUpScreenView
import com.example.ui.feature.home.signup.RegisterScreenView
import com.example.ui.navigation.HomeNavKeys.HomeScreen
import com.example.ui.navigation.HomeNavKeys.LoginScreen
import com.example.ui.navigation.HomeNavKeys.SignUpScreen
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
                        onRegisterClick = { backStack.add(SignUpScreen)}
                    )
                }
                is LoginScreen -> NavEntry(key = key) {
                    SignUpScreenView(
                        onSignUpClick = { userId, clubId ->
                            backStack.add(DashboardScreen(userId = userId, clubId = clubId))
                        },
                        onRegisterClick = { backStack.add(LoginScreen)}
                    )
                }
                is SignUpScreen -> NavEntry(key = key) {
                    RegisterScreenView(onSuccessNavigate = { userId, clubId ->
                        backStack.add(DashboardScreen(userId = userId, clubId = clubId))
                    })
                }
                is DashboardScreen -> NavEntry(key = key) {
                    DashboardScreenView(userId = key.userId, clubId = key.clubId, backStack = backStack)
                }
                else -> throw RuntimeException("Unknown NavKey: $key")
            }
        }
    )
}