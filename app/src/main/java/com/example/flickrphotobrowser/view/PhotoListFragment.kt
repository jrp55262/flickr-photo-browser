package com.example.flickrphotobrowser.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.bumptech.glide.Glide
import com.example.flickrphotobrowser.R
import com.example.flickrphotobrowser.databinding.PhotoListFragmentBinding
import com.example.flickrphotobrowser.model.PhotoData
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
    ): View? {

        _binding = PhotoListFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.photoList.adapter = listViewAdapter
        binding.photoList.layoutManager = LinearLayoutManager(view.context)

        viewModel = ViewModelProvider(this).get(PhotoListViewModel::class.java)
        viewModel.photos.observe(viewLifecycleOwner, Observer { photos ->
            listViewAdapter.updateData(photos)
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

    // Called when the user taps on an entry; navigates
    // to the detail fragment
    fun onClickCallback(photoData: PhotoData) {
        findNavController().navigate(R.id.action_PhotoFragment_to_DetailFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}