package com.example.domain.feature.user.model

data class ImageMetaDataModel(
    val contentType: String? = null,
    val customMetaData: Map<String, String> = emptyMap()
)