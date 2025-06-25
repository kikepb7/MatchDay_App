package com.example.data.feature.authentication

import com.example.domain.common.Either
import com.example.domain.common.FailureModel
import com.example.domain.feature.authentication.repository.AuthRepository
import com.example.domain.feature.user.model.UserModel
import com.example.domain.feature.user.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val userRepository: UserRepository
): AuthRepository {

    override suspend fun signUpWithEmailAndPassword(email: String, password: String): Either<FailureModel, String> {

        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user

//            if (user != null && user.isEmailVerified) {   // TODO
            if (user != null) {
                Either.Success(user.uid)
            } else {
                Either.Error(FailureModel.GenericError("Email no verificado"))
            }
        } catch (e: FirebaseAuthInvalidUserException) {
            Either.Error(FailureModel.GenericError("El usuario no existe"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Either.Error(FailureModel.GenericError("Contraseña incorrecta"))
        } catch (e: Exception) {
            Either.Error(FailureModel.GenericError("Error inesperado: ${e.localizedMessage}"))
        }
    }

    override suspend fun registerWithEmailAndPassword(email: String, password: String, userModel: UserModel): Either<FailureModel, String> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user

            if (user != null) {
                val userId = user.uid
                user.sendEmailVerification().await()

                val userToSave = userModel.copy(id = userId)
                userRepository.createUser(userToSave)

                Either.Success(userId)

            } else {
                Either.Error(FailureModel.GenericError("No se pudo registrar el usuario"))
            }
        } catch (e: FirebaseAuthUserCollisionException) {
            Either.Error(FailureModel.GenericError("El correo ya está registrado"))
        } catch (e: FirebaseAuthWeakPasswordException) {
            Either.Error(FailureModel.GenericError("La contraseña es muy débil."))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Either.Error(FailureModel.GenericError("El formato del correo no es válido"))
        } catch (e: Exception) {
            Either.Error(FailureModel.GenericError("Error inesperado: ${e.localizedMessage}"))
        }
    }

    override suspend fun isUserLogged(): Either<FailureModel, Boolean> {
        return try {
            val currentUser = firebaseAuth.currentUser
            Either.Success(currentUser != null)
        } catch (e: Exception) {
            Either.Error(FailureModel.GenericError("Error al comprobar si el usuario está logeado"))
        }
    }

    override suspend fun logout() = firebaseAuth.signOut()
}