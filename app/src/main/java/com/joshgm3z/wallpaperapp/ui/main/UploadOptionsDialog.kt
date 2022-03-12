package com.joshgm3z.wallpaperapp.ui.main

import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.fragment.app.DialogFragment
import com.joshgm3z.wallpaperapp.R


class UploadOptionsDialog(private val callback: UploadDialogListener) : DialogFragment() {

    companion object {
        const val TAG = "UploadOptionsFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.dialog_fragment_upload, container, false)
        val rlOpenGallery: RelativeLayout = view.findViewById(R.id.rl_open_gallery)
        rlOpenGallery.setOnClickListener {
            callback.onOpenGalleryClick()
            dismiss()
        }
        val rlOpenCamera: RelativeLayout = view.findViewById(R.id.rl_open_camera)
        rlOpenCamera.setOnClickListener {
            callback.onOpenCameraClick()
            dismiss()
        }
        setPosition()
        return view
    }

    private fun setPosition() {
        val window: Window? = dialog!!.window
        val wlp: WindowManager.LayoutParams = window!!.attributes
        wlp.gravity = Gravity.BOTTOM
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT
        window!!.attributes = wlp
    }

    interface UploadDialogListener {
        fun onOpenGalleryClick()
        fun onOpenCameraClick()
    }

}