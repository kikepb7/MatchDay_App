package com.example.firebase.authentication

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class FirebaseAuthService(
    private val firebaseAuth: FirebaseAuth
) {
    private var storedVerificationId: String? = null

    suspend fun signInWithEmailAndPassword(email: String, password: String): String? {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return result.user?.uid
    }

    suspend fun registerWithEmailAndPassword(email: String, password: String): FirebaseUser? {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        return result.user
    }

    fun isUserLogged(): Boolean = firebaseAuth.currentUser != null

    fun logout() = firebaseAuth.signOut()

    suspend fun loginWithGoogle(idToken: String): FirebaseUser? {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return signInWithCredential(credential)
    }

    private suspend fun signInWithCredential(credential: AuthCredential): FirebaseUser? {
        val result = firebaseAuth.signInWithCredential(credential).await()
        return result.user
    }

    suspend fun signUpWithPhoneVerification(
        phoneNumber: String,
        activity: Activity,
        onCodeSent: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    firebaseAuth.signInWithCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    onError(e.message ?: "Verificación fallida")
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    storedVerificationId = verificationId
                    onCodeSent(verificationId)
                }
            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    suspend fun verifyOtpCode(code: String): String? {
        val credential = PhoneAuthProvider.getCredential(
            storedVerificationId ?: return null,
            code
        )
        val result = firebaseAuth.signInWithCredential(credential).await()
        return result.user?.uid
    }
}