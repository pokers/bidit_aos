package com.alexk.bidit.domain.entity.selling

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SellingTimeEntity(
    var dateIdx: Int,
    var hourIdx: Int,
    var minuteIdx: Int
):Parcelable
