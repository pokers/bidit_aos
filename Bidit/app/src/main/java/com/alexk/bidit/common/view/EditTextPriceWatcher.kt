package com.alexk.bidit.common.view

import android.text.Editable
import android.text.Selection
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import com.alexk.bidit.common.util.addComma
import java.lang.NumberFormatException


class EditTextPriceWatcher(private val editText: EditText) : TextWatcher {

    private var inputText = ""

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (!TextUtils.isEmpty(s.toString()) && s.toString() != inputText
        ) {
            inputText = addComma(s?.toString()?.replace(",", "")?.toInt()!!)
            editText.setText(inputText)
            val editable = editText.text
            Selection.setSelection(editable, inputText.length)
        }
    }

    override fun afterTextChanged(s: Editable?) {

    }

}