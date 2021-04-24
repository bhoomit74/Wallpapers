package com.bhoomit.wallpapers.dashboard.viewmodel

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

    private var _allImages : MutableLiveData<ArrayList<ImageDetail>> = MutableLiveData()
    val allImages : LiveData<ArrayList<ImageDetail>> = _allImages

    private var _updateList : MutableLiveData<ArrayList<ImageDetail>> = MutableLiveData()
    val updateList : LiveData<ArrayList<ImageDetail>> = _updateList

    private var _error : MutableLiveData<String> = MutableLiveData()
    val error : LiveData<String> = _error

    private var _noticeVisibility : MutableLiveData<Boolean> = MutableLiveData()
    val noticeVisibility : LiveData<Boolean> = _noticeVisibility

    private var _isLoading : MutableLiveData<Boolean> = MutableLiveData()
    val isLoading : LiveData<Boolean> = _isLoading

    var noticeText : ObservableField<String> = ObservableField("")
    private var page = 1



    init {
        getImages(page)
    }


    private fun getImages(page : Int){
        _isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            when(val response = DashboardRepository.getImages(page)){
                is Result.Success -> {
                    if(page == 1) {
                        _allImages.postValue(response.data)
                        _updateList.postValue(response.data)
                    }
                    else{
                        val temp = allImages.value?: arrayListOf()
                        temp.addAll(response.data)
                        _allImages.postValue(temp)
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
            _isLoading.postValue(false)
        }
    }

    fun incrementPageAndGetImages(){
        if (isLoading.value==false) {
            page += 1
            getImages(page)
        }
    }

    //To showing notice or notify anything in app using snackbar
    /*fun showNotice(notice : String){
        noticeText.set(notice)
        _noticeVisibility.value = true
    }
    fun hideNotice(){
        _noticeVisibility.value = false
    }*/

    //Call this method before navigate to another fragment
    fun removeUpdateList(){
        _updateList.value = null
    }

}