package com.alexk.bidit.domain.entity.item

import android.os.Parcelable
import com.alexk.bidit.domain.entity.item.img.ItemImgEntity
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class ItemBasicEntity(
    var id: Int?,
    var status: Int?,
    var sPrice : Int?,
    var cPrice : Int?,
    var viewCount : Int?,
    var title : String?,
    var createdAt : String?,
    var dueDate :String?,
    var itemImgList : List<ItemImgEntity>
):Parcelable, Serializable