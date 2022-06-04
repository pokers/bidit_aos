package com.alexk.bidit.presentation.ui.sign.join

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.alexk.bidit.R
import com.alexk.bidit.databinding.FragmentJoinBinding
import com.alexk.bidit.presentation.base.BaseFragment

class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join) {

    private val args: JoinFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvJoinTest.text = args.argLogin
    }
}