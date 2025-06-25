package com.example.data.feature.authentication

import com.example.domain.common.Either
import com.example.domain.common.FailureModel
import com.example.domain.feature.authentication.repository.AuthRepository
import com.example.domain.feature.user.model.UserModel
import com.example.domain.feature.user.repository.UserRepository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val userRepository: UserRepository,
): AuthRepository {

    private var storedVerificationId: String? = null

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

    // TODO --> abstraer el activity del constructor
    /*override suspend fun signUpWithPhoneVerification(phoneNumber: String, activity: Activity): Either<String, Unit> {
        return suspendCancellableCoroutine { cont ->
            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        firebaseAuth.signInWithCredential(credential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    cont.resume(Either.Success(Unit))
                                } else {
                                    cont.resume(Either.Error("Error verificando credencial"))
                                }
                            }
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        cont.resume(Either.Error(e.message ?: "Verificación fallida"))
                    }

                    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                        storedVerificationId = verificationId
                        cont.resume(Either.Success(Unit))
                    }
                })
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }*/

    override suspend fun verifyOtpCode(verificationId: String, code: String): Either<String, String> {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)

        return suspendCancellableCoroutine { cont ->
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val userId = firebaseAuth.currentUser?.uid
                        cont.resume(Either.Success(userId.toString()))
                    } else {
                        cont.resume(Either.Error(("Código inválido")))
                    }
                }
        }
    }
}