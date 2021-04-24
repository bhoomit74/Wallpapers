package com.bhoomit.wallpapers.full_screen_image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FullScreenImageViewModel : ViewModel() {

    private var _image : MutableLiveData<String> = MutableLiveData()
    val image : LiveData<String> = _image

    private var _setWallpaper : MutableLiveData<Boolean> = MutableLiveData()
    var setWallpaper : LiveData<Boolean> = _setWallpaper

    private var _error : MutableLiveData<String> = MutableLiveData()
    var error : LiveData<String> = _error

    fun setImage(url : String?){
        if (url.isNullOrEmpty()){
            _error.value = "Image not found"
        }
        else {
            _image.value = url
        }
    }

    fun setWallpaper(){
        _setWallpaper.value = true
    }

}