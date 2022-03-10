package com.joshgm3z.wallpaperapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.joshgm3z.wallpaperapp.domain.PictureRepository
import com.joshgm3z.wallpaperapp.domain.data.Picture

class MainViewModel : ViewModel() {

    private val repo: PictureRepository = PictureRepository()

    var pictureList: List<Picture> = ArrayList()

}