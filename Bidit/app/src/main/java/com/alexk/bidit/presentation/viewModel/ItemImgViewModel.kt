package com.alexk.bidit.presentation.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexk.bidit.DoBidMutation
import com.alexk.bidit.GetBiddingInfoQuery
import com.alexk.bidit.data.repository.ItemImgRepositoryImpl
import com.alexk.bidit.di.S3Client
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.domain.entity.merchandise.MerchandiseImgEntity
import com.alexk.bidit.domain.repository.ItemImgRepository
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloHttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ItemImgViewModel @Inject constructor(private val repository: ItemImgRepository) :
    ViewModel() {
    private val _itemUrl by lazy { MutableLiveData<ViewState<MerchandiseImgEntity>>() }
    val itemUrl get() = _itemUrl

    fun uploadItemImg(fileDirName: String, file: File) = viewModelScope.launch {
        _itemUrl.postValue(ViewState.Loading())
        val response = repository.uploadImg(fileDirName, file)
        response.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState?) {
                if (state == TransferState.COMPLETED) {
                    _itemUrl.postValue(ViewState.Success(MerchandiseImgEntity((S3Client().provideS3Client().getResourceUrl(response.bucket, response.key)))))
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                _itemUrl.postValue(ViewState.Loading())
            }

            override fun onError(id: Int, ex: Exception?) {
                _itemUrl.postValue(ViewState.Error("Error img upload"))
            }
        })
    }
}