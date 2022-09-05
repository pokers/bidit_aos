package com.alexk.bidit.common.util

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.alexk.bidit.R
import com.alexk.bidit.presentation.ui.home.HomeActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kakao.sdk.common.util.SdkLogLevel
import com.sendbird.android.SendBird

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