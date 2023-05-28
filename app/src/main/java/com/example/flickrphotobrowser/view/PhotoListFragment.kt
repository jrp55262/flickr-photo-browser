package com.example.flickrphotobrowser.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}