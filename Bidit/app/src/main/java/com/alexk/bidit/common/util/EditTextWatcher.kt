package com.alexk.bidit.common.util

import android.text.Editable
import android.text.Selection
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText


class EditTextWatcher(private val editText: EditText) : TextWatcher {

    var inputText = ""
    var price = editText.text.toString().replace(",","").toInt()

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (!TextUtils.isEmpty(s.toString()) && s.toString() != inputText
        ) {
            price = s?.toString()?.replace(",", "")?.toInt()!!
            inputText = addComma(price)
            editText.setText(inputText)
            val editable = editText.text
            Selection.setSelection(editable, inputText.length)
        }
    }

    override fun afterTextChanged(s: Editable?) {

    }

    fun getInputPrice() : Int = price
}