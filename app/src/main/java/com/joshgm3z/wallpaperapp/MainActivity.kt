package com.joshgm3z.wallpaperapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joshgm3z.wallpaperapp.domain.data.Picture
import com.joshgm3z.wallpaperapp.ui.detail.PictureFragment
import com.joshgm3z.wallpaperapp.ui.main.MainAdapter
import com.joshgm3z.wallpaperapp.ui.main.MainViewHolder
import com.joshgm3z.wallpaperapp.ui.main.MainViewModel
import com.joshgm3z.wallpaperapp.ui.main.UploadOptionsDialog
import com.joshgm3z.wallpaperapp.util.FbLogging
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), MainViewHolder.ClickListener, MainViewModel.UiListener,
    UploadOptionsDialog.UploadDialogListener {

    private var mainAdapter: MainAdapter = MainAdapter(this)

    private val REQUEST_GALLERY_IMAGE = 1
    private val REQUEST_IMAGE_CAPTURE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FbLogging(this).logEntry()

        setContentView(R.layout.activity_main)
        initUI()

        MainViewModel(this)
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

    private fun initUI() {
        val recyclerView: RecyclerView = findViewById(R.id.rv_picture_list)
        recyclerView.layoutManager = GridLayoutManager(this, SPAN_COUNT)
        recyclerView.adapter = mainAdapter

        val ivUploadBtn: ImageView = findViewById(R.id.ic_upload)
        ivUploadBtn.setOnClickListener {
            UploadOptionsDialog(this)
                .show(supportFragmentManager, UploadOptionsDialog.TAG)
        }
    }

    companion object {
        const val SPAN_COUNT = 3
    }

    override fun onListPictureClicked(picture: Picture) {
        val newInstance = PictureFragment.newInstance(picture)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fl_main, newInstance)
            .addToBackStack("")
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        FbLogging(this).logEntry()
        if (resultCode == RESULT_OK) {
            var imageUri: Uri? = null
            if (requestCode == REQUEST_GALLERY_IMAGE) {
                imageUri = data?.data!!
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                val imageBitmap = data!!.extras!!.get("data") as Bitmap
                imageUri = getUriFromBitmap(imageBitmap)
            }
            UploadActivity.start(this, imageUri!!)
        }
    }

    private fun getUriFromBitmap(imageBitmap: Bitmap): Uri {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        val file = File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
        val bos = ByteArrayOutputStream()
        imageBitmap.compress(CompressFormat.JPEG, 100, bos);
        val bitmapData: ByteArray = bos.toByteArray()
        val fos = FileOutputStream(file)
        fos.write(bitmapData)
        fos.flush()
        fos.close()
        return Uri.fromFile(file)
    }

    override fun showData(list: ArrayList<Picture>) {
        mainAdapter.setPictureList(list)
    }

    override fun onOpenGalleryClick() {
        FbLogging(this).logEvent(FbLogging.CLICK_ACTION)
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_IMAGE)
    }

    override fun onOpenCameraClick() {
        FbLogging(this).logEvent(FbLogging.CLICK_ACTION)
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

}