package com.example.flickrphotobrowser.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flickrphotobrowser.model.PhotoData
import com.example.flickrphotobrowser.model.FlickrPhotoDataRetriever
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PhotoListViewModel(): ViewModel() {

    val photos = MutableLiveData<List<PhotoData>>()
    private val photoRetriever = FlickrPhotoDataRetriever("moon", 5)

    // Run this on the IO thread in case the underlying retriever
    // needs to do so
    fun getMorePhotos() {
        CoroutineScope(Dispatchers.IO).launch {
            val newPhotos = photoRetriever.getMorePhotos()
            if (newPhotos.size > 0) {
                photos.postValue(photos.value?.toMutableList()?.plus(newPhotos) ?: newPhotos)
            }
        }
    }

    init {
        // timerLoop()
        getMorePhotos()
    }

    fun timerLoop() {
        CoroutineScope(Dispatchers.IO).launch {
            getMorePhotos()
            delay(5000)
            timerLoop()
        }
    }

}