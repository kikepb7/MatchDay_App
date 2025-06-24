package com.example.ui.navigation

import androidx.navigation3.runtime.NavKey
import androidx.versionedparcelable.VersionedParcelize
import com.example.domain.feature.club.model.ClubModel
import com.example.domain.feature.user.model.UserModel
import kotlinx.serialization.Serializable

object HomeNavKeys {
    @Serializable
    object HomeScreen : NavKey

    @Serializable
    object LoginScreen : NavKey

    @Serializable
    object RegisterScreen : NavKey

    @VersionedParcelize
    data class DashboardScreen(
        val user: UserModel,
        val club: ClubModel
    ) : NavKey
}

