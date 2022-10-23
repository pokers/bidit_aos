package com.alexk.bidit.common.util

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.alexk.bidit.GetBiddingInfoQuery
import com.alexk.bidit.GetItemListQuery
import com.alexk.bidit.GetMyBiddingInfoQuery
import com.alexk.bidit.GetMyInfoQuery
import com.alexk.bidit.common.dialog.LoadingDialog
import com.alexk.bidit.domain.entity.item.ItemBasicEntity
import com.alexk.bidit.domain.entity.item.img.ItemImgEntity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sendbird.android.constant.StringSet.s

fun addComma(number: Int): String = if (number >= 0) {
    "%,d".format(number)
} else {
    "- "
}

fun parsePriceTypeToInt(price: String): Int =
    price.replace(",", "").toInt()


fun BottomSheetDialogFragment.setDialogTransparentBackground() {
    dialog?.apply {
        setOnShowListener {
            val bottomSheet =
                findViewById<View?>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.setBackgroundResource(android.R.color.transparent)
        }
    }
}

fun TextView.setTextColorWithResourceCompat(id: Int) {
    this.setTextColor(ResourcesCompat.getColor(resources, id, null))
}

fun Context.setLoadingDialog(flag: Boolean) {
    if (flag) LoadingDialog.getLoadingDialogInstance(this)?.show()
    else LoadingDialog.getLoadingDialogInstance(this)?.dismiss()
}

fun Context.showLongToastMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}