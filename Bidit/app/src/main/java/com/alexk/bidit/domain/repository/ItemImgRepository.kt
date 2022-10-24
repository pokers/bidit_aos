package com.alexk.bidit.domain.repository

import com.alexk.bidit.domain.entity.item.img.ItemImgEntity
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import java.io.File

interface ItemImgRepository {
    suspend fun uploadImg(fileDirName: String, file: File) : ItemImgEntity
}