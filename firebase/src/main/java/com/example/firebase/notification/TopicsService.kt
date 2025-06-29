package com.example.firebase.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class TopicsService(
    private val messaging: FirebaseMessaging
) {
    companion object {
        const val MATCH_TOPIC = "match_topic"
    }

    suspend fun subscribeToTopic(topic: String) {
        suspendCancellableCoroutine { cont ->
            messaging.subscribeToTopic(topic)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.i("MatchDay", "Suscrito al topic: $topic")
                        cont.resume(Unit)
                    } else {
                        Log.e("MatchDay", "Error al suscribirse", task.exception)
                        cont.resumeWithException(task.exception ?: Exception("Error desconocido"))
                    }
                }
        }
    }

    suspend fun unsubscribeToTopic(topic: String) {
        suspendCancellableCoroutine { cont ->
            messaging.unsubscribeFromTopic(topic)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.i("MatchDay", "Desuscrito del topic: $topic")
                        cont.resume(Unit)
                    } else {
                        Log.e("MatchDay", "Error al desuscribirse", task.exception)
                        cont.resumeWithException(task.exception ?: Exception("Error desconocido"))
                    }
                }
        }
    }
}