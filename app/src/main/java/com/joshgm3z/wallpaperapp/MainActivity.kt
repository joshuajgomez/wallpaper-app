package com.joshgm3z.wallpaperapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joshgm3z.wallpaperapp.domain.data.Picture
import com.joshgm3z.wallpaperapp.ui.MainAdapter
import com.joshgm3z.wallpaperapp.ui.MainViewHolder
import com.joshgm3z.wallpaperapp.ui.MainViewModel
import com.joshgm3z.wallpaperapp.ui.detail.PictureFragment

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
}