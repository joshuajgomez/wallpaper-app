package com.joshgm3z.wallpaperapp

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import com.joshgm3z.wallpaperapp.ui.upload.UploadViewModel
import com.joshgm3z.wallpaperapp.util.FbLogging

class UploadActivity : AppCompatActivity(), UploadViewModel.UploadProgressListener {

    private val uploadViewModel: UploadViewModel = UploadViewModel()
    private lateinit var pbUpload: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FbLogging(this).logEntry()
        setContentView(R.layout.activity_upload)

        initUI()
    }

    private fun initUI() {
        val etDesc: EditText = findViewById(R.id.et_description)
        val ivPicture: ImageView = findViewById(R.id.iv_picture_draft)
        var uri: Uri? = null
        if (intent.hasExtra(EXTRA_IMAGE_URI)) {
            val stringUri = intent.getStringExtra(EXTRA_IMAGE_URI)
            uri = Uri.parse(stringUri)
            ivPicture.setImageURI(uri)
        }
        val ivBack: ImageView = findViewById(R.id.iv_back_btn)
        ivBack.setOnClickListener {
            finish()
        }

        pbUpload = findViewById(R.id.pb_uploading)
        pbUpload.visibility = View.INVISIBLE

        val btnUpload: Button = findViewById(R.id.btn_upload)
        btnUpload.setOnClickListener {
            val textDescription = etDesc.text.trim()
            if (textDescription.isNotEmpty() && uri != null) {
                pbUpload.visibility = View.VISIBLE
                btnUpload.isEnabled = false
                etDesc.isEnabled = false
                ivBack.isEnabled = false
                uploadViewModel.onUploadClick(uri, textDescription.toString(), this)
                FbLogging(this).logEvent(FbLogging.UPLOAD_WALLPAPER)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        FbLogging(this).logEntry()
    }

    override fun onPause() {
        super.onPause()
        FbLogging(this).logEntry()
    }

    override fun onStop() {
        super.onStop()
        FbLogging(this).logEntry()
    }

    override fun onDestroy() {
        super.onDestroy()
        FbLogging(this).logEntry()
    }

    companion object {
        private const val EXTRA_IMAGE_URI: String = "EXTRA_IMAGE_URI"

        fun start(context: Context, imageUri: Uri) {
            val intent = Intent(context, UploadActivity::class.java).apply {
                putExtra(EXTRA_IMAGE_URI, imageUri.toString())
            }
            context.startActivity(intent)
        }
    }

    override fun onProgressUpdate(progress: Int) {
        FbLogging(this).logInfo(progress.toString())
        pbUpload.progress = progress
        Log.println(Log.ASSERT, "uploadActivity", "progress: $progress")
    }

    override fun onSuccess() {
        FbLogging(this).logEntry()
        pbUpload.visibility = View.INVISIBLE
        Log.println(Log.ASSERT, "uploadActivity", "success")
        finish()
    }

    override fun onFail(message: String) {
        FbLogging(this).logInfo(message)
        pbUpload.visibility = View.INVISIBLE
        Log.println(Log.ASSERT, "uploadActivity", "fail: $message")
        FbLogging(this).logEvent(FbLogging.ERROR)
    }
}