package com.alexk.bidit.common.util.view

import android.text.Editable
import android.text.Selection
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.alexk.bidit.R
import com.alexk.bidit.common.util.addComma


class EditTextAutoCommaWatcher(private val editText: EditText) : TextWatcher {

    var inputText = ""
    var number = 0
    var wonTextView: TextView? = null

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (!TextUtils.isEmpty(s.toString()) && s.toString() != inputText
        ) {
            number = s?.toString()?.replace(",", "")?.toInt()!!
            inputText = addComma(number)
            editText.setText(inputText)
            val editable = editText.text
            Selection.setSelection(editable, inputText.length)
        }
        if(wonTextView != null){
            if(s.toString() != ""){
                wonTextView?.setTextColor(ResourcesCompat.getColor(editText.resources, R.color.black,null))
            }
            else{
                wonTextView?.setTextColor(ResourcesCompat.getColor(editText.resources, R.color.nobel,null))
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {

    }
}