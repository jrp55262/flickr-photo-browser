package com.example.flickrphotobrowser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flickrphotobrowser.model.FlickrPhotoDataRetriever
import com.example.flickrphotobrowser.model.PhotoData
import com.example.flickrphotobrowser.model.PhotoDataRetriever
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for handling photo data and managing LiveData
 * for the view layer.
 *
 * TODO - Make the photoRetriever be injectible for testing.
 * We used the TestPhotoDataRetreiver class during development
 * for this purpose and we should also be able to use this
 * for unit tests.
 *
 */
class PhotoListViewModel(): ViewModel() {

    val photos = MutableLiveData<List<PhotoData>>()
    val loading = MutableLiveData<Boolean>()
    private var photoRetriever: PhotoDataRetriever? = null

    // TODO - If we implement storable settings then we should
    // store this as a preference.
    companion object {
        private val PHOTOS_PER_PAGE = 50
    }

    // Start a new photo retrieval.  Clear any existing results,
    // create a new photo retriever, and get the first batch
    fun getPhotos(searchTerm: String) {
        photoRetriever = FlickrPhotoDataRetriever(searchTerm, PHOTOS_PER_PAGE)
        photos.postValue(arrayListOf<PhotoData>())
        getMorePhotos()
    }

    // Run this on the IO thread in case the underlying retriever
    // needs to do so
    fun getMorePhotos() {
        photoRetriever?.also { retriever ->
            // Let observers know that we're in the process of loading
            loading.postValue(true)

            CoroutineScope(Dispatchers.IO).launch {
                val newPhotos = retriever.getMorePhotos()

                // Let observers know that we're done, and post the results.
                loading.postValue(false)
                if (newPhotos.size > 0) {
                    photos.postValue(photos.value?.toMutableList()?.plus(newPhotos) ?: newPhotos)
                }
            }
        }
    }
}