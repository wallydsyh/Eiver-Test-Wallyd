package com.example.eiver_test_wallyd.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class ImageUtils {
    fun displayImageFromUrl(
        context: Context, url: String?, imageView: ImageView,
        placeholderDrawable: Drawable?
    ) {
        val myOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .frame(0)
            .placeholder(placeholderDrawable)
        Glide.with(context)
            .load(url)
            .apply(myOptions)
            .into(imageView)

    }
}