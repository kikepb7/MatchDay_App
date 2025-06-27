package com.example.firebase

import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FirebaseStorageService(
    private val storage: FirebaseStorage
) {

    suspend fun uploadImage(fileName: String, bytes: ByteArray) {
        val reference = storage.reference.child(fileName)
        reference.putBytes(bytes).await()
    }
}
