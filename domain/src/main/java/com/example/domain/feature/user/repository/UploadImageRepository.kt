package com.example.domain.feature.user.repository

import com.example.domain.feature.user.model.ImageMetaDataModel

interface UploadImageRepository {
    suspend fun uploadImage(fileName: String, bytes: ByteArray, metadata: ImageMetaDataModel? = null): String?
}