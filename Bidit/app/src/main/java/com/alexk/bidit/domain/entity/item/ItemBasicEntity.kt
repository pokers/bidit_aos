package com.alexk.bidit.domain.entity.item

import android.os.Parcelable
import com.alexk.bidit.domain.entity.item.img.ItemImgEntity
import com.alexk.bidit.domain.entity.user.UserBasicEntity
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class ItemBasicEntity(
    var id: Int? = null,
    var status: Int? = null,
    var sPrice : Int? = null,
    var cPrice : Int? = null,
    var buyNow : Int? = null,
    var viewCount : Int? = null,
    var title : String? = null,
    var createdAt : String? = null,
    var dueDate :String? = null,
    var itemUserInfo : UserBasicEntity? = null,
    var description : String? = null,
    var itemImgList : List<ItemImgEntity>? = null,
):Parcelable, Serializable