package com.joshgm3z.wallpaperapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joshgm3z.wallpaperapp.R
import com.joshgm3z.wallpaperapp.domain.data.Picture

class MainAdapter(val callback: MainViewHolder.ClickListener) :
    RecyclerView.Adapter<MainViewHolder>() {

    private var pictureList: List<Picture>? = null

    fun setPictureList(list: List<Picture>) {
        pictureList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_picture, parent, false)
        return MainViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.setData(pictureList!![position])
    }

    override fun getItemCount(): Int {
        return pictureList?.size ?: 0
    }

}
