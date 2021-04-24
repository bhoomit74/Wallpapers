package com.bhoomit.wallpapers.util

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bhoomit.wallpapers.R
import com.bumptech.glide.Glide

object Extensions {
    fun Context.setImage(imageView: ImageView, url : String){
        Glide.with(this).load(url).placeholder(R.drawable.gray_background).centerCrop().into(imageView)
    }

    fun Context.showErrorToast(message : String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}