package com.alexk.bidit.common.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.alexk.bidit.common.util.addComma
import com.bumptech.glide.Glide

object CommonBindingAdapter {
    @BindingAdapter("app:imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }

    @BindingAdapter("app:price")
    @JvmStatic
    fun addPriceFormat(textView: TextView, price: Int) {
        textView.text = "${addComma(price)}원"
    }

    @BindingAdapter("app:number")
    @JvmStatic
    fun addNumberFormat(textView: TextView, price: Int) {
        textView.text = addComma(price)
    }
}