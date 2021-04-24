package com.bhoomit.wallpapers.util

import android.content.Context
import android.widget.ImageView
import com.bhoomit.wallpapers.R
import com.bumptech.glide.Glide

object Extensions {
    fun Context.setImage(imageView: ImageView, url : String){
        Glide.with(this).load(url).placeholder(R.drawable.gray_background).centerCrop().into(imageView)
    }
}