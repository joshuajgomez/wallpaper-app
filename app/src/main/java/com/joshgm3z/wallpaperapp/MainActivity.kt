package com.joshgm3z.wallpaperapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joshgm3z.wallpaperapp.domain.data.Picture
import com.joshgm3z.wallpaperapp.ui.detail.PictureFragment
import com.joshgm3z.wallpaperapp.ui.main.MainAdapter
import com.joshgm3z.wallpaperapp.ui.main.MainViewHolder
import com.joshgm3z.wallpaperapp.ui.main.MainViewModel


class MainActivity : AppCompatActivity(), MainViewHolder.ClickListener {

    private var mainAdapter: MainAdapter = MainAdapter(this)
    private val mainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
    }

    private fun initUI() {
        val recyclerView: RecyclerView = findViewById(R.id.rv_picture_list)
        recyclerView.layoutManager = GridLayoutManager(this, SPAN_COUNT)
        recyclerView.adapter = mainAdapter

        mainAdapter.setPictureList(mainViewModel.pictureList)

        val ivUploadBtn: ImageView = findViewById(R.id.ic_upload)
        ivUploadBtn.setOnClickListener {
            openGallery()
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

    val GALLERY_IMAGE_REQUEST = 1

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == GALLERY_IMAGE_REQUEST) {
            val imageUri = data?.data
            UploadActivity.start(this, imageUri)
        }
    }
}