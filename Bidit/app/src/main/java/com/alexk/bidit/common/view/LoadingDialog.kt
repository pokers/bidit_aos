package com.alexk.bidit.common.view

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.alexk.bidit.databinding.DialogLoadingBinding

class LoadingDialog(context: Context) :Dialog(context){

    private lateinit var binding : DialogLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.2f)
    }

    companion object{
        private var loadingDialog : Dialog? = null
        fun getLoadingDialogInstance(context: Context) : Dialog?{
            if(loadingDialog == null) loadingDialog = LoadingDialog(context)
            return loadingDialog
        }
    }
}