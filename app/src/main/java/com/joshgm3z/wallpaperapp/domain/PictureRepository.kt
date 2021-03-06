package com.joshgm3z.wallpaperapp.domain

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.joshgm3z.wallpaperapp.domain.data.Picture
import com.joshgm3z.wallpaperapp.util.Const

class PictureRepository() {
    private lateinit var callback: PictureDownloadListener

    fun getSamplePictures(): List<Picture> {
        val desc =
            "Thanks Michell, I created an Images folder in my project, inside of it there is a png image Black_x.png, So what should be the path here to decode image, it should be Images Black_x.png ?"

        val list = ArrayList<Picture>()
//        list.add(Picture("", "", R.drawable.wallpaper1, desc))
//        list.add(Picture("", "", R.drawable.wallpaper2, desc))
//        list.add(Picture("", "", R.drawable.wallpaper3, desc))
//        list.add(Picture("", "", R.drawable.wallpaper4, desc))
//        list.add(Picture("", "", R.drawable.wallpaper5, desc))
//        list.add(Picture("", "", R.drawable.wallpaper2, desc))
//        list.add(Picture("", "", R.drawable.wallpaper1, desc))
//        list.add(Picture("", "", R.drawable.wallpaper4, desc))
//        list.add(Picture("", "", R.drawable.wallpaper5, desc))
//        list.add(Picture("", "", R.drawable.wallpaper2, desc))
//        list.add(Picture("", "", R.drawable.wallpaper1, desc))
        return list
    }

    fun getPictures(callback: PictureDownloadListener) {
        this.callback = callback
        val db = Firebase.firestore
        db.collection(Const.COLLECTION_PICTURES)
            .addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    parseDocumentList(snapshot.documents)
                }
            }
    }

    private fun parseDocumentList(documents: List<DocumentSnapshot>) {
        val list = ArrayList<Picture>()
        for (document in documents) {
            val picture: Picture = document.toObject(Picture::class.java)!!
            list.add(picture)
        }
        callback.onDownloadComplete(list)
    }

    interface PictureDownloadListener {
        fun onDownloadComplete(list: ArrayList<Picture>)
    }
}