package com.example.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

object HomeNavKeys {
    @Serializable
    object LoginScreen : NavKey

    @Serializable
    object SignUpScreen : NavKey

    @Serializable
    object RegisterScreen : NavKey

    @Serializable
    data class DashboardScreen(
        val userId: String,
        val clubId: String
    ) : NavKey
}