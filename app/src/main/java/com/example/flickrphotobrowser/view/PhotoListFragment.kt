package com.example.flickrphotobrowser.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

        binding.photoListButton.setOnClickListener {
            findNavController().navigate(R.id.action_PhotoFragment_to_DetailFragment)
        }

        viewModel = ViewModelProvider(this).get(PhotoListViewModel::class.java)
        viewModel.photos.observe(viewLifecycleOwner, Observer { photos ->
            updateViews(photos)
        })
    }

    /**
     * Generate a list of photos from the supplied data
     */
    private fun updateViews(photos: List<PhotoData>) {
        val textLines = mutableListOf<String>()
        textLines.add("Available photos")
        textLines.add("----------")
        photos.forEach { photo ->
            textLines.add(photo.title)
        }
        textLines.add("----------")
        binding.photoListText.text = textLines.joinToString("\n")

        if (photos.size > 0) {
            val lastPhoto = photos[photos.lastIndex]
            binding.photoTitle.text = lastPhoto.title
            Glide.with(this)
                .load(lastPhoto.thumbnailUrl)
                .into(binding.photoThumbnail)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}