package com.joshgm3z.wallpaperapp.ui.detail

import android.content.ContentResolver
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.joshgm3z.wallpaperapp.R
import com.joshgm3z.wallpaperapp.domain.data.Picture
import com.joshgm3z.wallpaperapp.util.DateUtil


private const val ARG_PARAM = "param_picture"

class PictureFragment : Fragment() {

    private lateinit var picture: Picture

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            picture = it.getParcelable(ARG_PARAM)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return initUI(inflater, container)
    }

    private fun initUI(inflater: LayoutInflater, container: ViewGroup?): View? {
        val view = inflater.inflate(
            R.layout.fragment_picture,
            container,
            false
        )
        val ll: LinearLayout = view.findViewById(R.id.ll_desc)
        ll.visibility = View.VISIBLE

        val reference = FirebaseStorage.getInstance().reference
        val child = reference.child(picture.url!!)
        val iv: ImageView = view.findViewById(R.id.iv_picture)
        Glide.with(view)
            .load(child)
            .into(iv)

        val ivBack: ImageView = view.findViewById(R.id.iv_back_btn)
        ivBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        iv.setOnClickListener {
            if (ll.isVisible) {
                ll.visibility = View.GONE
                ivBack.visibility = View.INVISIBLE
            } else {
                ll.visibility = View.VISIBLE
                ivBack.visibility = View.VISIBLE
            }
        }

        val tv: TextView = view.findViewById(R.id.tv_desc)
        tv.text = picture.description

        val llDownload: LinearLayout = view.findViewById(R.id.ll_download)
        llDownload.setOnClickListener {
            downloadImageToGallery(iv)
            showDownloadNotification(view)
        }
        val llSetWallpaper: LinearLayout = view.findViewById(R.id.ll_set_wallpaper)
        llSetWallpaper.setOnClickListener {
            Toast.makeText(context, "Feature coming soon", Toast.LENGTH_SHORT).show()
        }

        val tvDate: TextView = view.findViewById(R.id.tv_date)
        val textDate: String = DateUtil.getTextDate(picture.dateAdded)
        tvDate.text = "added on $textDate"
        return view
    }

    private fun downloadImageToGallery(iv: ImageView) {
        MediaStore.Images.Media.insertImage(getContentResolver(),
            iv.drawToBitmap(),
            picture.name,
            picture.description);
    }

    private fun showDownloadNotification(view: View) {
        Snackbar.make(view, "Downloaded to Gallery", Snackbar.LENGTH_SHORT).show()
    }

    private fun getContentResolver(): ContentResolver {
        return requireContext().contentResolver
    }

    companion object {

        @JvmStatic
        fun newInstance(picture: Picture) =
            PictureFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM, picture)
                }
            }
    }
}