package com.example.flickrphotobrowser.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.example.flickrphotobrowser.databinding.PhotoListFragmentBinding
import com.example.flickrphotobrowser.viewmodel.PhotoListViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PhotoListFragment : Fragment() {

    private lateinit var viewModel: PhotoListViewModel
    private var _binding: PhotoListFragmentBinding? = null
    private val listViewAdapter = PhotoListViewAdapter()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = PhotoListFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var wasLoading = false
        binding.photoList.adapter = listViewAdapter
        binding.photoList.layoutManager = LinearLayoutManager(view.context)

        // Instantiate the ViewModel
        viewModel = ViewModelProvider(this).get(PhotoListViewModel::class.java)

        // Observer for view model data updates
        viewModel.photos.observe(viewLifecycleOwner) { photos ->
            listViewAdapter.updateData(photos)
        }

        // Observer for view model loading state
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (loading && !wasLoading) {
                // TODO - Create an overlay scrim with a spinner instead of a toast.
                Toast.makeText(view.context, "Loading Photos...", Toast.LENGTH_SHORT).show()
            }
            wasLoading = loading
        }

        // Set up the search box to start the photo retrieval when we do a new search
        val searchBox = binding.searchBox
        searchBox.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchBox.clearFocus()
                viewModel.getPhotos(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        // Detect vertical overscroll, load more photos if available.
        val recyclerView = binding.photoList
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(1)) {
                    viewModel.getMorePhotos()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}