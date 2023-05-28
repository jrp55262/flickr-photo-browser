package com.example.flickrphotobrowser.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flickrphotobrowser.model.PhotoData
import com.example.flickrphotobrowser.model.FlickrPhotoDataRetriever
import com.example.flickrphotobrowser.model.PhotoDataRetriever
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PhotoListViewModel(): ViewModel() {

    val photos = MutableLiveData<List<PhotoData>>()
    val loading = MutableLiveData<Boolean>()
    private var photoRetriever: PhotoDataRetriever? = null
    private val PHOTOS_PER_PAGE = 50

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
            loading.postValue(true)
            CoroutineScope(Dispatchers.IO).launch {
                val newPhotos = retriever.getMorePhotos()
                loading.postValue(false)
                if (newPhotos.size > 0) {
                    photos.postValue(photos.value?.toMutableList()?.plus(newPhotos) ?: newPhotos)
                }
            }
        }
    }
}