package com.joshgm3z.wallpaperapp.domain

import android.net.Uri
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.joshgm3z.wallpaperapp.domain.data.Picture
import com.joshgm3z.wallpaperapp.util.Const
import java.util.*

class UploadHandler {
    lateinit var callback: UploadProgressListener
    fun uploadImage(desc: String, filePath: Uri, callback: UploadProgressListener) {
        this.callback = callback
        val storageInstance = FirebaseStorage.getInstance()
        val storageReference = storageInstance.reference

        val fileName = storageReference.child(
            "pictures/" + UUID.randomUUID().toString()
        )

        fileName.putFile(filePath)
            .addOnProgressListener {
                val progress: Double =
                    (100.0 * it.bytesTransferred / it.totalByteCount)
                callback.onProgressUpdate(progress.toInt())
            }
            .addOnSuccessListener {
                val picture = Picture()
                picture.description = desc
                picture.url = fileName.path
                picture.dateAdded = System.currentTimeMillis()
                uploadPictureInfo(picture)
            }
            .addOnFailureListener {
                callback.onUploadFailed(it.localizedMessage)
            }
    }

    private fun uploadPictureInfo(picture: Picture) {
        val db = Firebase.firestore
        db.collection(Const.COLLECTION_PICTURES)
            .add(picture)
            .addOnSuccessListener { callback.onUploadSuccess() }
            .addOnFailureListener { callback.onUploadFailed(it.localizedMessage) }
    }

    interface UploadProgressListener {
        fun onProgressUpdate(progress: Int)
        fun onUploadSuccess()
        fun onUploadFailed(message: String)
    }
}