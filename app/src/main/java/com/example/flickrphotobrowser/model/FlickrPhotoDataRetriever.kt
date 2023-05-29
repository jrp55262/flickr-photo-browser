package com.example.flickrphotobrowser.model

import com.flickr4java.flickr.Flickr
import com.flickr4java.flickr.REST
import com.flickr4java.flickr.photos.SearchParameters


class FlickrPhotoDataRetriever(
    searchTerm: String,
    photosPerSearch: Int  = 50): PhotoDataRetriever(searchTerm, photosPerSearch) {

    // TODO - When we add a settings function, store these
    // in a properties file
    companion object {
        const val API_KEY = "d927a953aa04b9a6f2fc5174e507657a"
        const val SECRET = "3021c453bec60362"
    }

    private val flickr: Flickr by lazy {
        Flickr.debugRequest = false
        Flickr.debugStream = false
        Flickr(API_KEY, SECRET, REST())
    }

    override fun getMorePhotos(): List<PhotoData> {

        // TODO - Error Handling
        val searchParams = SearchParameters()
        searchParams.tags = arrayOf(searchTag)
        val photoList = flickr.photosInterface.search(searchParams, pageSize, currentPage)
        val photoDataList = photoList.map {photo ->
            PhotoData(
                title = photo.title,
                imageUrl = photo.largeUrl,
                thumbnailUrl = photo.thumbnailUrl,
                description = photo.description ?: "No Description",
                dateTaken = photo.dateTaken?.toString() ?: "Unknown",
                datePosted = photo.datePosted?.toString() ?: "Unknown"
            )
        }
        currentPage++
        return photoDataList
    }
}