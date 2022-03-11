package com.joshgm3z.wallpaperapp

import android.content.Context
import android.content.Intent
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

class UploadActivity : AppCompatActivity(), UploadViewModel.UploadProgressListener {

    private val uploadViewModel: UploadViewModel = UploadViewModel()
    private lateinit var pbUpload: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            }
        }
    }

    companion object {
        private const val EXTRA_IMAGE_URI: String = "EXTRA_IMAGE_URI"

        fun start(context: Context, imageUri: Uri?) {
            val intent = Intent(context, UploadActivity::class.java).apply {
                putExtra(EXTRA_IMAGE_URI, imageUri.toString())
            }
            context.startActivity(intent)
        }
    }

    override fun onProgressUpdate(progress: Int) {
        pbUpload.progress = progress
        Log.println(Log.ASSERT, "uploadActivity", "progress: $progress")
    }

    override fun onSuccess() {
        pbUpload.visibility = View.INVISIBLE
        Log.println(Log.ASSERT, "uploadActivity", "success")
        finish()
    }

    override fun onFail(message: String) {
        pbUpload.visibility = View.INVISIBLE
        Log.println(Log.ASSERT, "uploadActivity", "fail: $message")
    }
}