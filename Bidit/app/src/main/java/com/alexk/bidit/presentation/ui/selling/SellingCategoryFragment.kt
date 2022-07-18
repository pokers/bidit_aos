package com.alexk.bidit.presentation.ui.selling

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.navArgs
import com.alexk.bidit.R
import com.alexk.bidit.databinding.FragmentSellingCategoryBinding
import com.alexk.bidit.presentation.base.BaseFragment

class SellingCategoryFragment() :
    BaseFragment<FragmentSellingCategoryBinding>(R.layout.fragment_selling_category) {

    private val args: SellingCategoryFragmentArgs by navArgs()
    private val getCategory by lazy { args.getCategory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    override fun init() {
        binding.apply {
            when {
                tvIphone.text == getCategory -> {
                    tvIphone.setTextColor(ResourcesCompat.getColor(resources,R.color.persian_blue,null))
                }
                tvGalaxy.text == getCategory -> {
                    tvGalaxy.setTextColor(ResourcesCompat.getColor(resources,R.color.persian_blue,null))
                }
                tvAnotherPhone.text == getCategory -> {
                    tvAnotherPhone.setTextColor(ResourcesCompat.getColor(resources,R.color.persian_blue,null))
                }
                tvSmartWatch.text == getCategory -> {
                    tvSmartWatch.setTextColor(ResourcesCompat.getColor(resources,R.color.persian_blue,null))
                }
                tvNotebook.text == getCategory -> {
                    tvNotebook.setTextColor(ResourcesCompat.getColor(resources,R.color.persian_blue,null))
                }
                tvTablet.text == getCategory -> {
                    tvTablet.setTextColor(ResourcesCompat.getColor(resources,R.color.persian_blue,null))
                }
                tvMonitor.text == getCategory -> {
                    tvMonitor.setTextColor(ResourcesCompat.getColor(resources,R.color.persian_blue,null))
                }
                tvGame.text == getCategory -> {
                    tvGame.setTextColor(ResourcesCompat.getColor(resources,R.color.persian_blue,null))
                }
                tvAudio.text == getCategory -> {
                    tvAudio.setTextColor(ResourcesCompat.getColor(resources,R.color.persian_blue,null))
                }
                tvCamera.text == getCategory -> {
                    tvCamera.setTextColor(ResourcesCompat.getColor(resources,R.color.persian_blue,null))
                }
                tvDrone.text == getCategory -> {
                    tvDrone.setTextColor(ResourcesCompat.getColor(resources,R.color.persian_blue,null))
                }
                tvAnother.text == getCategory -> {
                    tvAnother.setTextColor(ResourcesCompat.getColor(resources,R.color.persian_blue,null))
                }
            }
        }
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