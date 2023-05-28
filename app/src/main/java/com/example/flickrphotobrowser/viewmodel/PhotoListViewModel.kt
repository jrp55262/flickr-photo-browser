package com.example.flickrphotobrowser.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flickrphotobrowser.model.PhotoData
import com.example.flickrphotobrowser.model.TestPhotoDataRetriever
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PhotoListViewModel(): ViewModel() {

    val photos = MutableLiveData<List<PhotoData>>()
    private var currentPhotoOffset = 0
    private val PHOTOS_PER_PAGE = 1
    private val LOOP_DELAY = 5000L
    private val photoRetriever = TestPhotoDataRetriever()

    fun getMorePhotos() {
        val newPhotos = photoRetriever.getPhotos(PHOTOS_PER_PAGE, currentPhotoOffset)
        if (newPhotos.size > 0) {
            photos.postValue(photos.value?.toMutableList()?.plus(newPhotos) ?: newPhotos)
            currentPhotoOffset += newPhotos.size
        }
    }

    init {
        timerLoop()
    }

    private fun timerLoop() {
        CoroutineScope(Dispatchers.IO).launch {
            getMorePhotos()
            delay(LOOP_DELAY)
            timerLoop()
        }
    }
}