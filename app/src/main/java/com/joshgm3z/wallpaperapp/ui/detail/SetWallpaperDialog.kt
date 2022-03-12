package com.joshgm3z.wallpaperapp.ui.detail

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class SetWallpaperDialog(private val callback: SetWallpaperDialogListener) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("Set this picture as your wallpaper?")
            .setPositiveButton("Ok") { _, _ ->
                callback.onOkPress()
                dismiss()
            }
            .setNegativeButton("Cancel") { _, _ -> dismiss() }
            .show()

    interface SetWallpaperDialogListener {
        fun onOkPress()
    }
}