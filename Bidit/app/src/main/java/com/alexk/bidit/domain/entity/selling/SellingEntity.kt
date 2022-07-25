package com.alexk.bidit.domain.entity.selling

import android.os.Parcelable
import com.alexk.bidit.domain.entity.merchandise.MerchandiseImgEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class SellingEntity(
    var imgUrlList: MutableList<MerchandiseImgEntity>,
    var title: String?,
    var categoryIdx: Int?,
    var startPrice: String?,
    var immediatePrice: String?,
    var endData: SellingCalendarEntity?,
    var endTime: SellingTimeEntity?,
    var description : String?
):Parcelable