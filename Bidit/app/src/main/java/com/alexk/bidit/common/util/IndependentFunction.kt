package com.alexk.bidit.common.util

import android.app.Dialog
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.alexk.bidit.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kakao.sdk.common.util.SdkLogLevel

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
