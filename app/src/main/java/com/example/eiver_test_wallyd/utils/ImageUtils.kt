package com.example.eiver_test_wallyd.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageUtils {
    fun displayImageFromUrl(
        context: Context, url: String?, imageView: ImageView
    ) {
        Glide.with(context)
            .load(url)
            .fitCenter()
            .into(imageView)

    }
}