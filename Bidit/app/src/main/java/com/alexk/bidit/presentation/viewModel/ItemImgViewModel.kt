package com.alexk.bidit.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexk.bidit.di.S3Client
import com.alexk.bidit.common.util.value.ViewState
import com.alexk.bidit.domain.entity.item.img.ItemImgEntity
import com.alexk.bidit.domain.repository.ItemImgRepository
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ItemImgViewModel @Inject constructor(private val repository: ItemImgRepository) :
    ViewModel() {
    private val _itemImgUrl by lazy { MutableLiveData<ViewState<ItemImgEntity>>() }
    val itemImgUrl get() = _itemImgUrl

    fun uploadItemImg(fileDirName: String, file: File) = viewModelScope.launch {
        _itemImgUrl.postValue(ViewState.Loading())
        val response = repository.uploadImg(fileDirName, file)
        response.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState?) {
                if (state == TransferState.COMPLETED) {
                    _itemImgUrl.postValue(ViewState.Success(ItemImgEntity((S3Client().provideS3Client().getResourceUrl(response.bucket, response.key)))))
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                _itemImgUrl.postValue(ViewState.Loading())
            }

            override fun onError(id: Int, ex: Exception?) {
                _itemImgUrl.postValue(ViewState.Error("Error img upload"))
            }
        })
    }
}