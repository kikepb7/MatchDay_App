package com.example.firebase

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.storageMetadata
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseStorageService(
    private val storage: FirebaseStorage
) {
    suspend fun uploadImage(
        fileName: String,
        bytes: ByteArray,
        metadata: StorageMetadata? = null
    ): String {
        val reference = storage.reference.child(fileName)

        if (metadata != null) {
            reference.putBytes(bytes, metadata).await()
        } else {
            reference.putBytes(bytes).await()
        }

        return reference.downloadUrl.await().toString()
    }

    private fun downloadImage(
        uploadTask: UploadTask.TaskSnapshot, cancellableContinuation: CancellableContinuation<Uri>
    ) {
        uploadTask.storage.downloadUrl
            .addOnSuccessListener { uri -> cancellableContinuation.resume(uri) }
            .addOnFailureListener { cancellableContinuation.resumeWithException(it) }
    }

    suspend fun uploadFileWithUri(uri: Uri): Uri = suspendCancellableCoroutine { continuation ->
        val fileName = uri.lastPathSegment ?: UUID.randomUUID().toString()
        val reference = storage.reference.child("uploads/$fileName")

        reference.putFile(uri, defaultMetaData())
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl
                    .addOnSuccessListener { downloadUri ->
                        continuation.resume(downloadUri)
                    }
                    .addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    fun defaultMetaData(): StorageMetadata = storageMetadata {
        contentType = "image/jpeg"
        setCustomMetadata("uploadedAt", System.currentTimeMillis().toString())
        setCustomMetadata("source", "android-app")
    }

    suspend fun readAllCustomMetaData(path: String): Map<String, String> {
        val reference = storage.reference.child(path)
        val metadata = reference.metadata.await()

        return metadata.customMetadataKeys.associateWith { key ->
            metadata.getCustomMetadata(key) ?: ""
        }
    }

    suspend fun readMetaDataValue(path: String, key: String): String? {
        val reference = storage.reference.child(path)
        val metadata = reference.metadata.await()
        return metadata.getCustomMetadata(key)
    }

    private fun removeImage(path: String): Boolean {
        val reference = storage.reference.child(path)
        return reference.delete().isSuccessful
    }

    private fun uploadImageWithProgress(path: String, uri: Uri) {
        val reference = storage.reference.child(path)
        reference.putFile(uri).addOnProgressListener { uploadTaskTaskSnapshot ->
            val progress = (100.0 * uploadTaskTaskSnapshot.bytesTransferred) / uploadTaskTaskSnapshot.totalByteCount
        }
    }

    private suspend fun getAllImages(path: String): List<Uri> {
        val reference = storage.reference.child(path)
        return reference.listAll().await().items.map { it.downloadUrl.await() }
    }
}
