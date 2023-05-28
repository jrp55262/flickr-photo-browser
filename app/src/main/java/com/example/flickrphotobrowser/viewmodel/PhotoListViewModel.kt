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
    private val photoRetriever = TestPhotoDataRetriever("ignored", 2)

    fun getMorePhotos() {
        val newPhotos = photoRetriever.getMorePhotos()
        if (newPhotos.size > 0) {
            photos.postValue(photos.value?.toMutableList()?.plus(newPhotos) ?: newPhotos)
        }
    }

    init {
        timerLoop()
    }

    fun timerLoop() {
        CoroutineScope(Dispatchers.IO).launch {
            getMorePhotos()
            delay(2000)
            timerLoop()
        }
    }

}