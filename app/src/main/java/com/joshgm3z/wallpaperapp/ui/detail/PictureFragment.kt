package com.joshgm3z.wallpaperapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(
            R.layout.fragment_picture,
            container,
            false
        )
        val iv: ImageView = view.findViewById(R.id.iv_picture)
        iv.setImageResource(picture.res)
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