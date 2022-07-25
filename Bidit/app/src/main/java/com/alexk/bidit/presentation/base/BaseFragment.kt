package com.alexk.bidit.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.alexk.bidit.common.dialog.LoadingDialog
import com.alexk.bidit.presentation.viewModel.NavigationViewModel

// 프래그먼트 상속 개념
// 프래그먼트의 기능을 구현함
abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes private val layoutId: Int) :
    Fragment() {
    //direction 으로 통신
    protected val navController: NavController get() = NavHostFragment.findNavController(this)
    val navigationViewModel: NavigationViewModel by viewModels()

    private var _binding: T? = null
    protected val binding: T get() = _binding!!

    private val loadingDialog by lazy { LoadingDialog(requireContext()) }

    abstract fun init()
    abstract fun initEvent()

    fun loadingDialogShow(){
        loadingDialog.show()
    }

    fun loadingDialogDismiss(){
        if(loadingDialog.isShowing){
            loadingDialog.dismiss()
        }
    }

    //옵저버 패턴
    private fun navigationAction() {
        navigationViewModel.apply {
            navDirectionAction.observe(viewLifecycleOwner) {
                if (it != null) navController.navigate(it)
            }
            popBackStack.observe(viewLifecycleOwner) {
                if (it) navController.popBackStack()
            }
        }
    }

    private fun navigationClear() {
        navigationViewModel.apply {
            navDirectionAction.value = null
        }
    }

    fun navPopStack() {
        navigationViewModel.popBackStack.postValue(true)
    }


    fun navigate(direction: NavDirections) {
        navigationViewModel.navDirectionAction.postValue(direction)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationAction()
    }

    //생성
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            layoutInflater,
            layoutId,
            null,
            false
        )
        binding.lifecycleOwner = this
        return binding.root
    }


    //super 코드를 무조건 해줘야 한다는 어노테이션
    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        navigationClear()
    }

}