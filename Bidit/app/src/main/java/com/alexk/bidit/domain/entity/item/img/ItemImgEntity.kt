package com.alexk.bidit.domain.entity.item.img

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class ItemImgEntity(
    val imgUrl : String?
):Parcelable, Serializable