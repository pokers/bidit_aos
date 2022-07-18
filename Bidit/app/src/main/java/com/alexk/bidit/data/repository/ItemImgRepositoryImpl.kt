package com.alexk.bidit.data.repository

import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.di.S3Client
import com.alexk.bidit.domain.repository.ItemImgRepository
import com.amazonaws.mobileconnectors.s3.transferutility.*
import com.amazonaws.services.s3.model.CannedAccessControlList
import java.io.File
import javax.inject.Inject

class ItemImgRepositoryImpl @Inject constructor(private val apiService: S3Client) :
    ItemImgRepository {

    override suspend fun uploadImg(fileDirName: String, file: File): TransferObserver {
        val transferUtility = TransferUtility.builder().s3Client(apiService.provideS3Client())
            .context(GlobalApplication.applicationContext()).build()
        TransferNetworkLossHandler.getInstance(GlobalApplication.applicationContext())

        return transferUtility.upload(
            "bidit-itemimage/mvp_model",
            fileDirName,
            file,
            CannedAccessControlList.PublicRead
        )
    }
}