package com.joshgm3z.wallpaperapp.util

import com.google.firebase.storage.UploadTask

class ProgressUtil {
    companion object {
        fun getProgress(bytesTransferred: Long, totalByteCount: Long): Int {
            return (100.0 * bytesTransferred / totalByteCount).toInt()
        }
    }
}