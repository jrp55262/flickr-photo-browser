package com.example.flickrphotobrowser.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
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
    ): View {

        _binding = PhotoDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // When the main page navigates to us, the photo details are
        // bundled up in the arguments.  Populate the view from this.
        arguments?.apply {
            binding.photoDisplayTitle.text = getString("photoTitle")
            binding.photoDisplayDescription.text = getString("photoDescription")
            binding.photoDates.text =
                "Date taken: ${getString("photoDateTaken") ?: "Unknown"}, Date Posted: ${getString("photoDatePosted") ?: "Unknown"}"
            Glide.with(view)
                .load(getString("photoImageUrl"))
                .into(binding.photoDisplayView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}