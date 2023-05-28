package com.example.flickrphotobrowser.model

interface PhotoDataRetriever {

    /**
     * Retrieve photos from the data source.  This
     * could potentially block depending on the data source
     * used.
     *
     * @param count: Maximum number of photos to retrieve
     * @param start: Starting index from which to retrieve photos
     *
     * @return A list of between 0 and count PhotoData objects
     */
    abstract fun getPhotos(count: Int, start: Int = 0): List<PhotoData>
}