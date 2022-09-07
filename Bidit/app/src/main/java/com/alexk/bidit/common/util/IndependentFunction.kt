package com.alexk.bidit.common.util

import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.alexk.bidit.GetBiddingInfoQuery
import com.alexk.bidit.GetMyInfoQuery
import com.alexk.bidit.domain.entity.item.ItemEntity
import com.alexk.bidit.domain.entity.item.ItemImgEntity
import com.alexk.bidit.type.CursorType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun addComma(number: Int): String = if (number >= 0) {
    "%,d".format(number)
} else {
    "- "
}

fun BottomSheetDialogFragment.setDialogTransparentBackground(){
    dialog?.apply {
        setOnShowListener {
           val bottomSheet = findViewById<View?>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.setBackgroundResource(android.R.color.transparent)
        }
    }
}

fun TextView.setTextColorWithResourceCompat(id:Int){
    this.setTextColor(ResourcesCompat.getColor(resources,id,null))
}

fun typeCastUsersItemToItemEntity(response : List<GetMyInfoQuery.Edge?>?) : MutableList<ItemEntity>{
    val typecastItemList = mutableListOf<ItemEntity>()

    for (idx in response?.indices!!) {
        val data = response[idx]?.node!!
        val imgList = mutableListOf<ItemImgEntity>()
        for (imgIdx in data.image?.indices!!) {
            imgList.add(ItemImgEntity(data.image[imgIdx]?.url))
        }
        val inputData = ItemEntity(
            id = data.id,
            status = data.status,
            sPrice = data.sPrice,
            cPrice = data.cPrice,
            viewCount = data.viewCount,
            title = data.title,
            createdAt = data.createdAt,
            dueDate = data.dueDate,
            itemImgList = imgList
        )
        typecastItemList.add(inputData)
    }
    return typecastItemList
}

fun typeCastBiddingItemToItemEntity(response : List<GetBiddingInfoQuery.GetBidding?>?) : MutableList<ItemEntity>{
    val typecastItemList = mutableListOf<ItemEntity>()

    for (idx in response?.indices!!) {
        val data = response[idx]?.item
        val imgList = mutableListOf<ItemImgEntity>()
        for (imgIdx in data?.image?.indices!!) {
            imgList.add(ItemImgEntity(data.image[imgIdx]?.url))
        }
        val inputData = ItemEntity(
            id = data.id,
            status = data.status,
            sPrice = data.sPrice,
            cPrice = data.cPrice,
            viewCount = data.viewCount,
            title = data.title,
            createdAt = data.createdAt,
            dueDate = data.dueDate,
            itemImgList = imgList
        )
        typecastItemList.add(inputData)
    }
    return typecastItemList
}
