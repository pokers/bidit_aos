package com.alexk.bidit.presentation.ui.myPage

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.alexk.bidit.R
import com.alexk.bidit.data.sharedPreference.TokenManager
import com.alexk.bidit.databinding.FragmentMyPageSignOutBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.myPage.dialog.MyPageSignOutReasonDialog
import com.alexk.bidit.presentation.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MyPageSignOutFragment :
    BaseFragment<FragmentMyPageSignOutBinding>(R.layout.fragment_my_page_sign_out) {

    private var signOutReason = 0
    private val signOutReasonList by lazy { requireContext().resources.getStringArray(R.array.category_sign_out_reason) }
    private val userViewModel by viewModels<UserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    override fun init() {
        observeUserInfo()
        binding.apply {

        }
    }

    override fun initEvent() {
        binding.apply {
            btnSignOut.setOnClickListener {
                userViewModel.updateUserState(1)
            }
            btnSelectDeleteReason.setOnClickListener {
                val dialog = MyPageSignOutReasonDialog(requireContext(), signOutReason) {
                    signOutReason = it
                    btnSelectDeleteReason.text = signOutReasonList[signOutReason]
                    btnSelectDeleteReason.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.charcoal,
                            null
                        )
                    )
                    btnSignOut.setBackgroundResource(R.drawable.bg_rect_transparent_red_orange_radius4_stroke0)
                    btnSignOut.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.white,
                            null
                        )
                    )
                    btnSignOut.isClickable = true
                }
                dialog.setCanceledOnTouchOutside(true)
                dialog.show()
                dialog.window?.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
            }
        }
    }
    private fun observeUserInfo(){
        userViewModel.userStatusInfo.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    loadingDialogShow()
                    Log.d("Delete user info","Loading")
                }
                is ViewState.Success -> {
                    loadingDialogDismiss()
                    Log.d("Delete user info","Success")
                    TokenManager(requireContext()).removePushToken()
                    TokenManager(requireContext()).removeToken()
                    navigate(MyPageSignOutFragmentDirections.actionMyPageSignOutFragmentToMyPageSignOutCompleteFragment())
                }
                is ViewState.Error -> {
                    loadingDialogDismiss()
                    Log.d("Delete user info","Error")
                }
            }
        }
    }
}