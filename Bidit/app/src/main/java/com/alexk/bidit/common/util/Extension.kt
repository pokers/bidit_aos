package com.alexk.bidit.common.util

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.alexk.bidit.common.view.LoadingDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


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