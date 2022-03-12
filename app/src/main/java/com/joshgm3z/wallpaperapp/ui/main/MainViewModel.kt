package com.joshgm3z.wallpaperapp.ui.main

import androidx.lifecycle.ViewModel
import com.joshgm3z.wallpaperapp.domain.PictureRepository
import com.joshgm3z.wallpaperapp.domain.data.Picture
import java.io.File

class MainViewModel(private val callback: UiListener) : ViewModel(), PictureRepository.PictureDownloadListener {

    private val repo: PictureRepository = PictureRepository()

    init {
        repo.getPictures(this)
    }

    override fun onDownloadComplete(list: ArrayList<Picture>) {
        callback.showData(list)
    }

    interface UiListener {
        fun showData(list: ArrayList<Picture>)
    }

}