package com.alexk.bidit.domain.entity.selling

import android.os.Parcelable
import com.alexk.bidit.domain.entity.item.img.ItemImgEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class SellingEntity(
    var imgUrlList: MutableList<ItemImgEntity>? = null,
    var title: String? = null,
    var categoryIdx: Int? = null,
    var startPrice: String? = null,
    var immediatePrice: String? = null,
    var endDate: SellingCalendarEntity? = null,
    var endTime: SellingTimeEntity? = null,
    var description : String? = null
):Parcelable, java.io.Serializable