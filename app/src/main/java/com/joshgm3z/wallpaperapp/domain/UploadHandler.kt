package com.joshgm3z.wallpaperapp.domain

import android.net.Uri
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.joshgm3z.wallpaperapp.domain.data.Picture
import com.joshgm3z.wallpaperapp.util.Const
import com.joshgm3z.wallpaperapp.util.ProgressUtil
import java.util.*

class UploadHandler {
    lateinit var callback: UploadProgressListener
    fun uploadImage(desc: String, filePath: Uri, callback: UploadProgressListener) {
        this.callback = callback
        val storageInstance = FirebaseStorage.getInstance()
        val storageReference = storageInstance.reference
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val serverFilePath = storageReference.child(
            "pictures/$fileName"
        )

        serverFilePath.putFile(filePath)
            .addOnProgressListener {
                val progress: Int = ProgressUtil.getProgress(it.bytesTransferred, it.totalByteCount)
                callback.onProgressUpdate(progress)
            }
            .addOnSuccessListener {
                val picture = Picture()
                picture.description = desc
                picture.url = serverFilePath.path
                picture.dateAdded = System.currentTimeMillis()
                picture.name = fileName
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