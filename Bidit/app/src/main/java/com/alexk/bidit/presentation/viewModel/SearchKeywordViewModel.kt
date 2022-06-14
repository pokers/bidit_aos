package com.alexk.bidit.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchKeywordViewModel:ViewModel() {
    val keywordLiveData = MutableLiveData<ArrayList<String>>()
    private val data = arrayListOf<String>()

    fun deleteKeyword(keyword: String){
        data.remove(keyword)
        keywordLiveData.value = data
    }

    fun initKeywordList(keywordList:ArrayList<String>){
        data.clear()
        data.addAll(keywordList)
        keywordLiveData.value = data
    }

    fun deleteAllKeyword(){
        data.clear()
        keywordLiveData.value = data
    }
}