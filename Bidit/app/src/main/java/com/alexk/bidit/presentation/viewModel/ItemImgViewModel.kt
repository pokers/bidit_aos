package com.alexk.bidit.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexk.bidit.common.view.ViewState
import com.alexk.bidit.domain.entity.item.img.ItemImgEntity
import com.alexk.bidit.domain.repository.ItemImgRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ItemImgViewModel @Inject constructor(private val repository: ItemImgRepository) :
    ViewModel() {
    private var _itemImgUrl: MutableLiveData<ViewState<ItemImgEntity>>? =
        MutableLiveData<ViewState<ItemImgEntity>>()

    val itemImgUrl get() = _itemImgUrl

    fun uploadItemImg(fileDirName: String, file: File) = viewModelScope.launch {
        _itemImgUrl?.postValue(ViewState.Loading())
        try {
            val response = repository.uploadImg(fileDirName, file)
            _itemImgUrl?.postValue(ViewState.Success(response))
        } catch (e: Exception) {
            _itemImgUrl?.postValue(ViewState.Error(e.message))
        }
    }
}