package com.alexk.bidit.presentation.ui.selling

import android.os.Bundle
import android.view.View
import com.alexk.bidit.R
import com.alexk.bidit.databinding.FragmentSellingCategoryBinding
import com.alexk.bidit.presentation.base.BaseFragment

class SellingCategoryFragment :
    BaseFragment<FragmentSellingCategoryBinding>(R.layout.fragment_selling_category) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }

    override fun init() {

    }

    override fun initEvent() {
        binding.apply {

            ivBack.setOnClickListener {
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        ""
                    )
                )
            }

            tvIphone.setOnClickListener {
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        "아이폰"
                    )
                )
            }
            tvGalaxy.setOnClickListener {
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        "갤럭시"
                    )
                )
            }
            tvAnotherPhone.setOnClickListener {
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        "기타폰"
                    )
                )
            }
            tvSmartWatch.setOnClickListener {
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        "스마트워치"
                    )
                )
            }
            tvNotebook.setOnClickListener {
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        "노트북/PC"
                    )
                )

                tvTablet.setOnClickListener {
                    navigate(
                        SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                            "태블릿"
                        )
                    )
                }
                tvMonitor.setOnClickListener {
                    navigate(
                        SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                            "티비/모니터"
                        )
                    )
                }
                tvGame.setOnClickListener {
                    navigate(
                        SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                            "게임"
                        )
                    )
                }
                tvAudio.setOnClickListener {
                    navigate(
                        SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                            "음향기기"
                        )
                    )
                }
                tvCamera.setOnClickListener {
                    navigate(
                        SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                            "카메라"
                        )
                    )
                }
                tvDrone.setOnClickListener {
                    navigate(
                        SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                            "드론"
                        )
                    )
                }
                tvAnother.setOnClickListener {
                    navigate(
                        SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                            "기타"
                        )
                    )
                }
            }
        }
    }
}