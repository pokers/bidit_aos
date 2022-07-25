package com.alexk.bidit.common.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.alexk.bidit.databinding.DialogLoginSignOutUserBinding

class SignOutUserDialog(context: Context):Dialog(context) {

    private lateinit var binding : DialogLoginSignOutUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLoginSignOutUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCanceledOnTouchOutside(true)
        setCancelable(true)
    }
}