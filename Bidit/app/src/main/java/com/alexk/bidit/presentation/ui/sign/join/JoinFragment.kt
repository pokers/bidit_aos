package com.alexk.bidit.presentation.ui.sign.join

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.alexk.bidit.R
import com.alexk.bidit.data.repository.SignRepositoryImpl
import com.alexk.bidit.databinding.FragmentJoinBinding
import com.alexk.bidit.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//Inject 어노테이션 변수에대해 의존성 주입
@AndroidEntryPoint
class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join) {

    private val args: JoinFragmentArgs by navArgs()
    @Inject lateinit var testRepository: SignRepositoryImpl

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvJoinTest.text = args.argLogin
        testRepository.load(binding.tvJoinTest.text.toString())
    }
}