package com.alexk.bidit.domain.repository

import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import java.io.File

interface ItemImgRepository {
    suspend fun uploadImg(fileDirName: String, file: File) : TransferObserver
}