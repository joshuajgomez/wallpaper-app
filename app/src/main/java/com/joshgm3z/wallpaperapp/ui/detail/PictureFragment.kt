package com.joshgm3z.wallpaperapp.ui.detail

import android.app.WallpaperManager
import android.content.ContentResolver
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
import com.joshgm3z.wallpaperapp.util.FbLogging


private const val ARG_PARAM = "param_picture"

class PictureFragment : Fragment(), SetWallpaperDialog.SetWallpaperDialogListener {

    private lateinit var picture: Picture
    private lateinit var ivPicture: ImageView

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
        FbLogging(requireContext()).logEntry()
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
        ivPicture = view.findViewById(R.id.iv_picture)
        Glide.with(view)
            .load(child)
            .into(ivPicture)

        val ivBack: ImageView = view.findViewById(R.id.iv_back_btn)
        ivBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        ivPicture.setOnClickListener {
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
            FbLogging(requireContext()).logEvent(FbLogging.BUTTON_CLICK)
            downloadImageToGallery(ivPicture)
            showNotification("Downloaded to Gallery")
        }
        val llSetWallpaper: LinearLayout = view.findViewById(R.id.ll_set_wallpaper)
        llSetWallpaper.setOnClickListener {
            FbLogging(requireContext()).logEvent(FbLogging.BUTTON_CLICK)
            SetWallpaperDialog(this).show(childFragmentManager, "")
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
            picture.description)
        FbLogging(requireContext()).logEvent(FbLogging.DOWNLOAD_WALLPAPER)

    }

    private fun showNotification(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
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

    override fun onResume() {
        super.onResume()
        FbLogging(requireContext()).logEntry()
    }

    override fun onPause() {
        super.onPause()
        FbLogging(requireContext()).logEntry()
    }

    override fun onStop() {
        super.onStop()
        FbLogging(requireContext()).logEntry()
    }

    override fun onDestroy() {
        super.onDestroy()
        FbLogging(requireContext()).logEntry()
    }

    override fun onOkPress() {
        FbLogging(requireContext()).logEntry()
        val wm = WallpaperManager.getInstance(context)
        wm.setBitmap(ivPicture.drawToBitmap())
        FbLogging(requireContext()).logEvent(FbLogging.SET_WALLPAPER)
        showNotification("Wallpaper set")
    }
}