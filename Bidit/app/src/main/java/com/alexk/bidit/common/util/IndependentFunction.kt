package com.alexk.bidit.common.util

import android.content.Context
import android.util.TypedValue
import android.view.View
import com.alexk.bidit.R
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