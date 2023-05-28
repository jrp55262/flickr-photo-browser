package com.example.flickrphotobrowser.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.flickrphotobrowser.R
import com.example.flickrphotobrowser.databinding.PhotoDetailFragmentBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PhotoDetailFragment : Fragment() {

    private var _binding: PhotoDetailFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = PhotoDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backToListButton.setOnClickListener {
            findNavController().navigate(R.id.action_DetailFragment_to_PhotoFragment)
        }

        val title = arguments?.getString("photoTitle")
        val imageUrl = arguments?.getString("photoImageUrl")
        val description = arguments?.getString("photoDescription")
        val dateTaken = arguments?.getString("photoDateTaken") ?: "Unknown"
        val datePosted = arguments?.getString("photoDatePosted") ?: "Unknown"

        binding.photoDisplayTitle.text = title
        binding.photoDisplayDescription.text = description
        binding.photoDates.text = "Date taken: $dateTaken  Date posted: $datePosted"
        Glide.with(view)
            .load(imageUrl)
            .into(binding.photoDisplayView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}