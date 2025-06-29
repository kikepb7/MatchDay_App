package com.example.data.feature.firebase.authentication

import com.example.domain.common.Either
import com.example.domain.common.FailureModel
import com.example.domain.feature.firebase.authentication.repository.AuthRepository
import com.example.domain.feature.user.model.UserModel
import com.example.domain.feature.user.repository.UserRepository
import com.example.firebase.authentication.FirebaseAuthService
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepositoryImpl(
    private val authService: FirebaseAuthService,
    private val userRepository: UserRepository
) : AuthRepository {

    override suspend fun signUpWithEmailAndPassword(email: String, password: String): Either<FailureModel, String> {
        return try {
//            if (user != null && user.isEmailVerified) {   // TODO
            val uid = authService.signInWithEmailAndPassword(email, password)
            if (uid != null) Either.Success(uid)
            else Either.Error(FailureModel.GenericError("UID nulo"))
        } catch (e: Exception) {
            Either.Error(FailureModel.GenericError(e.localizedMessage ?: "Error inesperado"))
        }
    }

    override suspend fun registerWithEmailAndPassword(email: String, password: String, userModel: UserModel): Either<FailureModel, String> {
        return try {
            val firebaseUser = authService.registerWithEmailAndPassword(email, password)
            if (firebaseUser != null) {
                firebaseUser.sendEmailVerification().await()
                val userToSave = userModel.copy(id = firebaseUser.uid)
                userRepository.createUser(userToSave)
                Either.Success(firebaseUser.uid)
            } else {
                Either.Error(FailureModel.GenericError("Registro fallido"))
            }
        } catch (e: Exception) {
            Either.Error(FailureModel.GenericError(e.localizedMessage ?: "Error inesperado"))
        }
    }

    override suspend fun loginWithGoogle(idToken: String): Either<String, String> {
        return try {
            val user = authService.loginWithGoogle(idToken)
            user?.uid?.let { Either.Success(it) } ?: Either.Error("UID nulo")
        } catch (e: Exception) {
            Either.Error(e.message ?: "Error login Google")
        }
    }

    override suspend fun signUpWithPhoneVerification(phoneNumber: String): Either<String, Unit> {
        // TODO --> Esto debe manejarse con una Activity desde UI
        return Either.Error("No implementado: se necesita Activity (UI layer)")
    }

    override suspend fun verifyOtpCode(verificationId: String, code: String): Either<String, String> {
        return try {
            val uid = authService.verifyOtpCode(code)
            uid?.let { Either.Success(it) } ?: Either.Error("UID nulo")
        } catch (e: Exception) {
            Either.Error(e.message ?: "Error verificando OTP")
        }
    }

    override suspend fun isUserLogged(): Either<FailureModel, Boolean> {
        return Either.Success(authService.isUserLogged())
    }

    override suspend fun logout() {
        authService.logout()
    }
}