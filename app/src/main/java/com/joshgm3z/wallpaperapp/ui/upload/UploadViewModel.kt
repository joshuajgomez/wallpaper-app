package com.joshgm3z.wallpaperapp.ui.upload

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.joshgm3z.wallpaperapp.domain.UploadHandler

class UploadViewModel : ViewModel(), UploadHandler.UploadProgressListener {

    lateinit var uiCallback: UploadProgressListener

    fun onUploadClick(uri: Uri, textDescription: String, callback: UploadProgressListener) {
        uiCallback = callback

        val uploadHandler = UploadHandler()
        uploadHandler.uploadImage(textDescription, uri, this)
    }

    override fun onProgressUpdate(progress: Int) {
        uiCallback.onProgressUpdate(progress)
    }

    override fun onUploadSuccess() {
        uiCallback.onSuccess()
    }

    override fun onUploadFailed(message: String) {
        uiCallback.onFail(message)
    }

    interface UploadProgressListener {
        fun onProgressUpdate(progress: Int)
        fun onSuccess()
        fun onFail(message: String)
    }

}