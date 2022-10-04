package com.alexk.bidit.common.util

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.alexk.bidit.GetBiddingInfoQuery
import com.alexk.bidit.GetItemListQuery
import com.alexk.bidit.GetMyBiddingInfoQuery
import com.alexk.bidit.GetMyInfoQuery
import com.alexk.bidit.common.dialog.LoadingDialog
import com.alexk.bidit.domain.entity.item.ItemBaiscEntity
import com.alexk.bidit.domain.entity.item.img.ItemImgEntity
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

fun typeCastUsersItemToItemEntity(response : List<GetMyInfoQuery.Edge?>?) : MutableList<ItemBaiscEntity>{
    val typecastItemList = mutableListOf<ItemBaiscEntity>()

    if(response == null){
        return typecastItemList;
    }

    for (idx in response.indices) {
        val data = response[idx]?.node!!
        val imgList = mutableListOf<ItemImgEntity>()
        for (imgIdx in data.image?.indices!!) {
            imgList.add(ItemImgEntity(data.image[imgIdx]?.url))
        }
        val inputData = ItemBaiscEntity(
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

fun typeCastItemQueryToItemEntity(response : List<GetItemListQuery.Edge?>?) : MutableList<ItemBaiscEntity>{
    val typecastItemList = mutableListOf<ItemBaiscEntity>()

    if(response == null){
        return typecastItemList;
    }

    for (idx in response.indices) {
        val data = response[idx]?.node
        val imgList = mutableListOf<ItemImgEntity>()
        for (imgIdx in data?.image?.indices!!) {
            imgList.add(ItemImgEntity(data.image[imgIdx]?.url))
        }
        val inputData = ItemBaiscEntity(
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


fun typeCastBiddingItemToItemEntity(response : List<GetBiddingInfoQuery.GetBidding?>?) : MutableList<ItemBaiscEntity>{
    val typecastItemList = mutableListOf<ItemBaiscEntity>()

    if(response == null){
        return typecastItemList;
    }

    for (idx in response.indices) {
        val data = response[idx]?.item
        val imgList = mutableListOf<ItemImgEntity>()
        for (imgIdx in data?.image?.indices!!) {
            imgList.add(ItemImgEntity(data.image[imgIdx]?.url))
        }
        val inputData = ItemBaiscEntity(
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

fun typeCastMyBiddingItemToItemEntity(response : List<GetMyBiddingInfoQuery.GetMyBidding?>?) : MutableList<ItemBaiscEntity>{
    val typecastItemList = mutableListOf<ItemBaiscEntity>()

    if(response == null){
        return typecastItemList;
    }

    for (idx in response.indices) {
        val data = response[idx]?.item
        val imgList = mutableListOf<ItemImgEntity>()
        for (imgIdx in data?.image?.indices!!) {
            imgList.add(ItemImgEntity(data.image[imgIdx]?.url))
        }
        val inputData = ItemBaiscEntity(
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


fun Context.setLoadingDialog(flag : Boolean){
    if(flag) LoadingDialog.getLoadingDialogInstance(this)?.show()
    else  LoadingDialog.getLoadingDialogInstance(this)?.dismiss()
}