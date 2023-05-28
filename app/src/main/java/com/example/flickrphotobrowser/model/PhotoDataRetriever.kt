package com.example.flickrphotobrowser.model

/**
 * Interface for a paginated retriever of photos from a data source.
 * For expedience the search tag and page size are
 * constructor parameters, but this would be more extensible
 * if it were converted to a builder pattern.
 */
abstract class PhotoDataRetriever(
    val searchTag: String = "",
    val pageSize: Int = 10
) {
    protected var currentPage = 0

    /**
     * Retrieve photos from the data source.  This
     * could potentially block depending on the data source
     * used.
     *
     * @return A list of between 0 and pageSize PhotoData objects
     */
    abstract fun getMorePhotos(): List<PhotoData>
}