package com.joshgm3z.wallpaperapp.ui

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.joshgm3z.wallpaperapp.R
import com.joshgm3z.wallpaperapp.domain.data.Picture

class MainViewHolder(itemView: View, val callback: ClickListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val ivPicture: ImageView = itemView.findViewById(R.id.iv_picture)
    private lateinit var picture: Picture

    init {
        itemView.setOnClickListener(this)
    }

    fun setData(picture: Picture) {
        this.picture = picture
        ivPicture.setImageResource(picture.res)
    }

    override fun onClick(view: View?) {
        callback.onListPictureClicked(picture)
    }

    interface ClickListener {
        fun onListPictureClicked(picture: Picture)
    }
}