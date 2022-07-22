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
                        -1
                    )
                )
            }

            tvIphone.setOnClickListener {
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        2
                    )
                )
            }
            tvGalaxy.setOnClickListener {
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        3
                    )
                )
            }
            tvAnotherPhone.setOnClickListener {
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        4
                    )
                )
            }
            tvSmartWatch.setOnClickListener {
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        5
                    )
                )
            }
            tvNotebook.setOnClickListener {
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        6
                    )
                )

                tvTablet.setOnClickListener {
                    navigate(
                        SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                            7
                        )
                    )
                }
                tvMonitor.setOnClickListener {
                    navigate(
                        SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                            8
                        )
                    )
                }
                tvGame.setOnClickListener {
                    navigate(
                        SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                            9
                        )
                    )
                }
                tvAudio.setOnClickListener {
                    navigate(
                        SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                            10
                        )
                    )
                }
                tvCamera.setOnClickListener {
                    navigate(
                        SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                            11
                        )
                    )
                }
                tvDrone.setOnClickListener {
                    navigate(
                        SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                            12
                        )
                    )
                }
                tvAnother.setOnClickListener {
                    navigate(
                        SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                            13
                        )
                    )
                }
            }
        }
    }
}