package com.bhoomit.wallpapers.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bhoomit.wallpapers.util.Extensions.setImage

@BindingAdapter("imageUrl")
fun load(imageView: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        imageView.context.setImage(imageView, imageUrl)
    }
}