package com.example.flickrphotobrowser.model

import com.flickr4java.flickr.Flickr
import com.flickr4java.flickr.REST
import com.flickr4java.flickr.photos.SearchParameters


class FlickrPhotoDataRetriever(
    private val searchTerm: String,
    private val photosPerSearch: Int  = 50): PhotoDataRetriever(searchTerm, photosPerSearch) {

    // TODO - When we add a settings function, store these
    // in a properties file
    companion object {
        const val API_KEY = "d927a953aa04b9a6f2fc5174e507657a"
        const val SECRET = "3021c453bec60362"
    }
    private var flickr: Flickr? = null

    private fun getConnection(): Flickr {
        if (flickr != null) {
            return flickr!!
        }

        flickr = Flickr(API_KEY, SECRET, REST())
        Flickr.debugRequest = false
        Flickr.debugStream = false
        return flickr!!
    }
    override fun getMorePhotos(): List<PhotoData> {

        // TODO - Error Handling
        val f = getConnection()
        val searchParams = SearchParameters()
        searchParams.setTags(arrayOf(searchTag))
        val photoList = f.photosInterface.search(searchParams, pageSize, currentPage)
        val photoDataList = photoList.map {photo ->
            PhotoData(
                title = photo.title,
                imageUrl = photo.largeUrl,
                thumbnailUrl = photo.thumbnailUrl,
                description = photo.description,
                dateTaken = photo.dateTaken.toString(),
                datePosted = photo.datePosted.toString()
            )
        }
        currentPage++
        return photoDataList
    }
}