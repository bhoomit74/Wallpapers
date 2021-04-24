package com.bhoomit.wallpapers.dashboard.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bhoomit.wallpapers.dashboard.data.model.ImageDetail
import com.bhoomit.wallpapers.dashboard.data.repository.DashboardRepository
import com.bhoomit.wallpapers.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel(){

    private var _imageList : MutableLiveData<ArrayList<ImageDetail>> = MutableLiveData()
    val imageList : LiveData<ArrayList<ImageDetail>> = _imageList

    private var _updateList : MutableLiveData<ArrayList<ImageDetail>> = MutableLiveData()
    val updateList : LiveData<ArrayList<ImageDetail>> = _updateList

    private var _error : MutableLiveData<String> = MutableLiveData()
    val error : LiveData<String> = _error

    private var _noticeVisibility : MutableLiveData<Boolean> = MutableLiveData()
    val noticeVisibility : LiveData<Boolean> = _noticeVisibility

    private var _progressBarVisibility : MutableLiveData<Boolean> = MutableLiveData()
    val progressBarVisibility : LiveData<Boolean> = _progressBarVisibility

    var noticeText : ObservableField<String> = ObservableField("")

    var page = 1

    init {
        showNotice("Click on image to set as a wallpaper.")
        Log.d("RECYCLERVIEW","init viewModel")
        getImages(page)
    }


    private fun getImages(page : Int){
        _progressBarVisibility.value = true
        CoroutineScope(Dispatchers.IO).launch {
            when(val response = DashboardRepository.getImages(page)){
                is Result.Success -> {
                    if(page == 1) {
                        _imageList.postValue(response.data)
                        _updateList.postValue(response.data)
                    }
                    else{
                        val temp = imageList.value?: arrayListOf()
                        temp.addAll(response.data)
                        _imageList.postValue(temp)
                        _updateList.postValue(response.data)
                    }
                }
                is Result.Error -> {
                    _error.postValue(response.error)
                }
                is Result.Exception -> {
                    _error.postValue(response.exception.toString())
                }
            }
            _progressBarVisibility.postValue(false)
        }
    }

    fun increasePageAndGetData(){
        if (progressBarVisibility.value==false) {
            page += 1
            getImages(page)
        }
    }

    fun hideNotice(){
        _noticeVisibility.value = false
    }

    fun showNotice(notice : String){
        noticeText.set(notice)
        _noticeVisibility.value = true
    }

    fun setProgressBarVisibility(show : Boolean){
        _progressBarVisibility.value = show
    }

    fun removeUpdateList(){
        _updateList.value = null
    }

}