package com.joshgm3z.wallpaperapp.domain

import com.joshgm3z.wallpaperapp.R
import com.joshgm3z.wallpaperapp.domain.data.Picture

class PictureRepository {
    fun getSamplePictures(): List<Picture> {
        val desc = "Thanks Michell, I created an Images folder in my project, inside of it there is a png image Black_x.png, So what should be the path here to decode image, it should be Images Black_x.png ?"

        val list = ArrayList<Picture>()
        list.add(Picture("", "", R.drawable.wallpaper1, desc))
        list.add(Picture("", "", R.drawable.wallpaper2, desc))
        list.add(Picture("", "", R.drawable.wallpaper3, desc))
        list.add(Picture("", "", R.drawable.wallpaper4, desc))
        list.add(Picture("", "", R.drawable.wallpaper5, desc))
        list.add(Picture("", "", R.drawable.wallpaper2, desc))
        list.add(Picture("", "", R.drawable.wallpaper1, desc))
        list.add(Picture("", "", R.drawable.wallpaper4, desc))
        list.add(Picture("", "", R.drawable.wallpaper5, desc))
        list.add(Picture("", "", R.drawable.wallpaper2, desc))
        list.add(Picture("", "", R.drawable.wallpaper1, desc))
        return list
    }
}