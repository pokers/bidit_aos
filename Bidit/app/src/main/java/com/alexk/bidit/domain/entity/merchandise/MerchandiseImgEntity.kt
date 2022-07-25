package com.alexk.bidit.domain.entity.merchandise

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MerchandiseImgEntity(
    val imgUrl : String?
):Parcelable