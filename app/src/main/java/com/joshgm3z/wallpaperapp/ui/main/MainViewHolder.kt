package com.joshgm3z.wallpaperapp.ui.main

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.joshgm3z.wallpaperapp.R
import com.joshgm3z.wallpaperapp.domain.data.Picture
import com.joshgm3z.wallpaperapp.util.FileUtil

class MainViewHolder(itemView: View, val callback: ClickListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val ivPicture: ImageView = itemView.findViewById(R.id.iv_picture)
    private lateinit var picture: Picture

    init {
        itemView.setOnClickListener(this)
    }

    fun loadPicture(picture: Picture) {
        this.picture = picture
        val storageReference = FirebaseStorage.getInstance().reference
        val child = storageReference.child(picture.url!!)

        Glide.with(itemView)
            .load(child)
            .into(ivPicture)
    }

    override fun onClick(view: View?) {
        callback.onListPictureClicked(picture)
    }

    interface ClickListener {
        fun onListPictureClicked(picture: Picture)
    }
}