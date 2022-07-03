package com.alexk.bidit.common.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.alexk.bidit.GetBiddingInfoQuery
import com.alexk.bidit.GetItemListQuery
import com.alexk.bidit.common.util.addComma
import com.bumptech.glide.Glide

object CommonBindingAdapter {
    @JvmStatic
    @BindingAdapter("imageUrlList")
    fun ImageView.loadImageListUrl(
        imageUrlList: List<GetItemListQuery.Image?>?
    ) {
        if (!imageUrlList.isNullOrEmpty()) {
            Glide.with(this.context)
                .load(imageUrlList[0])
                .into(this)
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.loadImage(imageUrl: String?) {
        if(imageUrl != null){
            Glide.with(this.context)
                .load(imageUrl)
                .into(this)
        }
    }

    @JvmStatic
    @BindingAdapter("price")
    fun TextView.changePriceType(price: Int?) {
        price?.let { this.text = "${addComma(price)}원" }
    }

    @JvmStatic
    @BindingAdapter("peopleCount")
    fun TextView.changePeopleCountType(count: Int?) {
        count?.let { this.text = "${addComma(count)}명" }
    }

    @JvmStatic
    @BindingAdapter("number")
    fun TextView.changeNumberType(number: Int?) {
        //텍스트 날짜 형식으로 변환 필요
        number?.let { this.text = addComma(number) }
    }

    @JvmStatic
    @BindingAdapter("time")
    fun TextView.changeDateType(date: String?) {
        //텍스트 날짜 형식으로 변환 필요
        date?.let { this.text = date }
    }
}