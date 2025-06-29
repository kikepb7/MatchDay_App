package com.example.data.feature.firebase.storage

import com.example.domain.feature.user.model.ImageMetaDataModel
import com.example.domain.feature.user.repository.UploadImageRepository
import com.example.firebase.storage.FirebaseStorageService
import com.google.firebase.storage.StorageMetadata

class FirebaseStorageRepositoryImpl(
    private val firebaseStorageService: FirebaseStorageService
) : UploadImageRepository {

    override suspend fun uploadImage(fileName: String, bytes: ByteArray, metadata: ImageMetaDataModel?): String? {
        val firebaseMetadata = metadata?.let {
            StorageMetadata.Builder().apply {
                it.contentType?.let { setContentType(it) }
                it.customMetaData.forEach { (key, value) ->
                    setCustomMetadata(key,value)
                }
            }.build()
        }

        return firebaseStorageService.uploadImage(fileName = fileName, bytes = bytes, metadata = firebaseMetadata)
    }
}