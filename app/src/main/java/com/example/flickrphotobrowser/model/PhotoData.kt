package com.example.flickrphotobrowser.model

data class PhotoData(
    val title: String,
    val imageUrl: String,
    val thumbnailUrl: String,
    val description: String,
    val dateTaken: String,
    val datePosted: String
)
