package com.example.flickrphotobrowser.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flickrphotobrowser.R
import com.example.flickrphotobrowser.model.PhotoData

class PhotoListViewAdapter: RecyclerView.Adapter<PhotoListViewAdapter.ViewHolder>(){

    private var photoList: List<PhotoData> = emptyList()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemTitleView = itemView.findViewById<TextView>(R.id.photoTitle)
        val itemImageView = itemView.findViewById<ImageView>(R.id.photoThumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val photoView = inflater.inflate(R.layout.photo_list_item, parent, false)
        return ViewHolder(photoView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photoData = photoList[position]
        holder.itemTitleView.text = photoData.title
        Glide.with(holder.itemView)
            .load(photoData.thumbnailUrl)
            .into(holder.itemImageView)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    fun updateData(newList: List<PhotoData>) {
        photoList = newList

        // This is known to be wildly inefficient; using it
        // here for expedience.  If we stick with RecyclerView
        // then we should use diffUtils instead to calculate the
        // optimal change.  If we go to Jetpack Compose and LazyColumn
        // then we don't have to worry about this at all.
        notifyDataSetChanged()
    }
}