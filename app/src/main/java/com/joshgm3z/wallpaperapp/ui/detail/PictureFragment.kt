package com.joshgm3z.wallpaperapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.joshgm3z.wallpaperapp.R
import com.joshgm3z.wallpaperapp.domain.data.Picture

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

        iv.setOnClickListener {
            if (ll.isVisible)
                ll.visibility = View.GONE
            else
                ll.visibility = View.VISIBLE
        }

        val tv: TextView = view.findViewById(R.id.tv_desc)
        tv.text = picture.description
        return view
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