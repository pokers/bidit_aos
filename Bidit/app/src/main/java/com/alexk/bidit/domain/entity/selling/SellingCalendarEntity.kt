package com.alexk.bidit.domain.entity.selling

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SellingCalendarEntity(
    var yearIdx: Int,
    var monthIdx: Int,
    var dayIdx: Int
):Parcelable
