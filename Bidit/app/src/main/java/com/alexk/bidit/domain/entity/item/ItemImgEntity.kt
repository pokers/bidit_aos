package com.alexk.bidit.domain.entity.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemImgEntity(
    val imgUrl : String?
):Parcelable