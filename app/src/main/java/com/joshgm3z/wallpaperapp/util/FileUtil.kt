package com.joshgm3z.wallpaperapp.util

import android.os.Environment
import java.io.File

class FileUtil {
    companion object {
        fun getLocalFile(filesDir: File, name: String): File {
//            val cacheDirectory = Environment.getFDownloadCacheDirectory()
            return File(filesDir.absolutePath + "/" + name)
        }
    }
}