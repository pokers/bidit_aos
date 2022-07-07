package com.alexk.bidit.common.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
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
        if(price == null){
            this.text = "0원"
        }
        else{
            this.text =  "${addComma(price)}원"
        }
    }

    @JvmStatic
    @BindingAdapter("peopleCount")
    fun TextView.changePeopleCountType(count: Int?) {
        if(count == null){
            this.text = "0명"
        }
        else{
            this.text =  "${addComma(count)}명"
        }
    }

    @JvmStatic
    @BindingAdapter("number")
    fun TextView.changeNumberType(number: Int?) {
        if(number == null){
            this.text = "0"
        }
        else{
            this.text =  "${addComma(number)}"
        }
    }

    @JvmStatic
    @BindingAdapter("time")
    fun TextView.changeDateType(date: String?) {
        //텍스트 날짜 형식으로 변환 필요
        date?.let { this.text = date }
    }

    @JvmStatic
    @BindingAdapter("status")
    fun TextView.changeStatusType(status : Int?) {
        //텍스트 날짜 형식으로 변환 필요
        val statusText : String = when(status){
            1 -> {
                "판매중"
            }
            2->{
                "예약중"
            }
            3->{
                "판매완료"
            }
            else -> {
                "error"
            }
        }
        this.text = statusText
    }
}