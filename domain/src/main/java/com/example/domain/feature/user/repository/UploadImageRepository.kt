package com.example.domain.feature.user.repository

interface UploadImageRepository {
    suspend fun uploadImage(fileName: String, bytes: ByteArray): String?
}