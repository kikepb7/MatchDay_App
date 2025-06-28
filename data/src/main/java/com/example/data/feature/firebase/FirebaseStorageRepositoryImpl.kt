package com.example.data.feature.firebase

import com.example.domain.feature.user.repository.UploadImageRepository
import com.example.firebase.FirebaseStorageService

class FirebaseStorageRepositoryImpl(
    private val firebaseStorageService: FirebaseStorageService
) : UploadImageRepository {

    override suspend fun uploadImage(fileName: String, bytes: ByteArray): String? {
        return firebaseStorageService.uploadImage(fileName = fileName, bytes = bytes)
    }
}