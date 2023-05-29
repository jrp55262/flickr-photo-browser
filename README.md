## FlickrPhotoBrowser
This is a simple Flickr photo browser.  It allows the user to search Flickr photos by keyword, page through a list of results, and get details for each photo found.
## Usage
The user interface is very basic:
- After launch, you see the results screen with a search box.
- Tap on the hourglass and enter your search term.
- You will then be presented with a scrollable list of photo thumbnails with titles.
- Tapping on any photo will bring you to a deatil screen with a larger version of the photo as well as title and description information.
- Tapping on the Back button at the top of the screen will bring you back to the results screen.
- As you scroll down the results, more photos are loaded and displayed.
- Entering a new search term clears the current results and loads a new set of photos.

## Implementation
The basic structure of the app is MVVM.
### Model Layer
- I created a test data retriever during development which had a hardcoded list of photos.
- I then created an abstract PhotoDataRetriever class from which various data retrievers could be derived.
- First I implemented a TestPhotoDataRetriever which uses a hardcoded list of photo data for developing the rest of the functionality.
- Once the basic functionality was established I then created a FlickrPhotoDataRetriever which uses the [flickr4java](https://github.com/boncey/Flickr4Java) package to download the photos (this is a more recent fork of the flickrj-android package mentioned in the assignment doc).  The only code change required in the ViewModel was to switch out the particular PhotoDataRetriever in use.
### ViewModel layer
The ViewModel layer implements just two public methods:
- **getPhotos(searchTerm)** which initializes the search and gets the first batch of photos
-  **getMorePhotos()** which retrieves another batch of photos if there are any available.
   The ViewModel layer delivers and manages two pieces of LiveData: the actual list of PhotoData objects, and a loading status to indicate whether a load operation is in progress.
### View layer
The View layer manages the user interface elements.  It is based on the Base View Activity from Android Studio.  I chose this because it includes navigation components which facilitate moving between list and detail fragments.
#### PhotoListFragment
This fragment displays the search results.  It is based on a simple RecyclerView.

- This layer uses LiveData observers to process updates from the ViewModel layer.
- The click handler on the RecyclerView packages up the photo details as arguments to the PhotoDetailFragment
- Overscrolling the RecyclerView indicates that the user is at the end of results and triggers a getMorePhotos() call to retrieve more.
- Since the load operation could incur some network latency, the ViewModel layer manages a flag to indicate when the load is in progress.  Currently toggling that flag triggers a toast to indicate loading (see **Issues** below)

#### PhotoDetailFragment
This displays the actual photo at full width, along with the full title, the photo description, and the dates the photo was taken and posted.  This data is provided as arguments to the navigation.  Pressing the "Back" button in the action bar returns the user to the results fragment.

## Issues
Since this app was knocked together in a few hours, there were a few items that came up that remain unaddressed:

- There is no error handling in the FlickrPhotoDataRetriever and currently no way to indicate to the user that an error has occurred.
- For some reason  all of the description and date fields in the retrieved photos are null.  I don't know if this is an issue on Flickr's end or if it is an issue with the flickr4java library.
- For the View layer I would have really wanted to use Jetpack Compose rather than a recyclerview, but I'm still on the learning curve for that library and I was not sure how it would integrate with fragment navigation.
- The API key and secret for the Flickr API are currently hardcoded in FlickrPhotoDataRetriever; it would be nice to have these as saved settings instead.
- Similarly, the number of photos per retrieval is hardcoded at 50; again this could be a setting.
- The PhotoListViewAdapter uses the inefficient **notifyDataSetChanged()** method to indicate that the underlying collection has been updated.  If we stick with the RecyclerView then I'd like to use DiffUtils to manage the update efficiently.
- I have not yet written unit tests.  The PhotoListViewModel should make the underlying PhotoRetriever injectable so that we could use the TestPhotoDataRetriever in unit tests.