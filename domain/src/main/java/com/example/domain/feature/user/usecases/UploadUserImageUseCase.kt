package com.example.domain.feature.user.usecases

import com.example.domain.feature.user.model.ImageMetaDataModel
import com.example.domain.feature.user.repository.UploadImageRepository

class UploadUserImageUseCase(
    private val uploadImageRepository: UploadImageRepository
) {
    suspend operator fun invoke(fileName: String, bytes: ByteArray, metadata: ImageMetaDataModel? = null): String? {
        return uploadImageRepository.uploadImage(fileName = fileName, bytes = bytes, metadata = metadata)
    }
}