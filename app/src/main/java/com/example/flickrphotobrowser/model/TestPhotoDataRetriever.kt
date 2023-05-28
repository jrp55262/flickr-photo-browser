package com.example.flickrphotobrowser.model

import kotlin.math.min

/**
 * An implementation of PhotoDataRetriever that is backed by a
 * static list of data items for testing/debugging
 */
class TestPhotoDataRetriever: PhotoDataRetriever {
    companion object {
        val testData = arrayListOf(
            PhotoData(
                title = "Taking Notes",
                imageUrl = "https://picsum.photos/id/4/1280/1024",
                thumbnailUrl = "https://picsum.photos/id/4/150/100",
                description = "Taking notes at the computer",
                dateTaken = "1/1/1970",
                datePosted = "1/1/1970"
            ),
            PhotoData(
                title = "Peaceful Marsh",
                imageUrl = "https://picsum.photos/id/11/1280/1024",
                thumbnailUrl = "https://picsum.photos/id/11/150/100",
                description = "Marsh with mountain in the distance",
                dateTaken = "4/1/1970",
                datePosted = "4/15/1970"
            ),
            PhotoData(
                title = "Rocky Shore",
                imageUrl = "https://picsum.photos/id/14/1280/1024",
                thumbnailUrl = "https://picsum.photos/id/14/150/100",
                description = "Rocky coastline on an overcast day",
                dateTaken = "7/1/1970",
                datePosted = "7/4/1970"
            ),
            PhotoData(
                title = "Into The Distance",
                imageUrl = "https://picsum.photos/id/17/1280/1024",
                thumbnailUrl = "https://picsum.photos/id/17/150/100",
                description = "A path leading to distant woods",
                dateTaken = "12/31/1999",
                datePosted = "1/5/2000"
            ),
            PhotoData(
                title = "Mountain Vista",
                imageUrl = "https://picsum.photos/id/29/1280/1024",
                thumbnailUrl = "https://fastly.picsum.photos/id/29/150/100.jpg?hmac=VcvkELtUePQaXF7h8TpSNj8QzIv_iRvy4mat9OUXIn4",
                description = "Overlooking snowy mountains",
                dateTaken = "5/1/2015",
                datePosted = "6/1/2015"
            )
        )
    }

    /**
     * Get a certain number of photos, starting at the specified index.
     * Returns at most "count" photos, or an empty list if out of bounds
     */
    override fun getPhotos(count: Int, start: Int): List<PhotoData> {
        val end = min(start + count, testData.size)
        try {
            return testData.subList(start, end)
        } catch (e: IndexOutOfBoundsException) {
            return arrayListOf<PhotoData>()
        }
    }
}