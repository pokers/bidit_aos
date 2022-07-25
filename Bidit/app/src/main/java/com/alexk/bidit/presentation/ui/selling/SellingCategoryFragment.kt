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
    private val getSellingEntity by lazy {args.sellingData}
    private val categoryList by lazy { resources.getStringArray(R.array.category_home_item) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    override fun init() {
        binding.apply {
            if(getSellingEntity?.categoryIdx == -1){
                return
            }
            when {
                tvIphone.text == categoryList[getSellingEntity?.categoryIdx?.minus(2)!!] -> {
                    tvIphone.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.persian_blue,
                            null
                        )
                    )
                }
                tvGalaxy.text == categoryList[getSellingEntity?.categoryIdx?.minus(2)!!] -> {
                    tvGalaxy.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.persian_blue,
                            null
                        )
                    )
                }
                tvAnotherPhone.text == categoryList[getSellingEntity?.categoryIdx?.minus(2)!!] -> {
                    tvAnotherPhone.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.persian_blue,
                            null
                        )
                    )
                }
                tvSmartWatch.text == categoryList[getSellingEntity?.categoryIdx?.minus(2)!!] -> {
                    tvSmartWatch.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.persian_blue,
                            null
                        )
                    )
                }
                tvNotebook.text == categoryList[getSellingEntity?.categoryIdx?.minus(2)!!] -> {
                    tvNotebook.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.persian_blue,
                            null
                        )
                    )
                }
                tvTablet.text == categoryList[getSellingEntity?.categoryIdx?.minus(2)!!] -> {
                    tvTablet.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.persian_blue,
                            null
                        )
                    )
                }
                tvMonitor.text == categoryList[getSellingEntity?.categoryIdx?.minus(2)!!] -> {
                    tvMonitor.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.persian_blue,
                            null
                        )
                    )
                }
                tvGame.text == categoryList[getSellingEntity?.categoryIdx?.minus(2)!!] -> {
                    tvGame.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.persian_blue,
                            null
                        )
                    )
                }
                tvAudio.text == categoryList[getSellingEntity?.categoryIdx?.minus(2)!!] -> {
                    tvAudio.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.persian_blue,
                            null
                        )
                    )
                }
                tvCamera.text == categoryList[getSellingEntity?.categoryIdx?.minus(2)!!] -> {
                    tvCamera.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.persian_blue,
                            null
                        )
                    )
                }
                tvDrone.text == categoryList[getSellingEntity?.categoryIdx?.minus(2)!!] -> {
                    tvDrone.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.persian_blue,
                            null
                        )
                    )
                }
                tvAnother.text == categoryList[getSellingEntity?.categoryIdx?.minus(2)!!] -> {
                    tvAnother.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.persian_blue,
                            null
                        )
                    )
                }
            }
        }
    }

    override fun initEvent() {
        binding.apply {

            ivBack.setOnClickListener {
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        getSellingEntity
                    )
                )
            }

            tvIphone.setOnClickListener {
                getSellingEntity?.categoryIdx = 2
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        getSellingEntity
                    )
                )
            }
            tvGalaxy.setOnClickListener {
                getSellingEntity?.categoryIdx = 3
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        getSellingEntity
                    )
                )
            }
            tvAnotherPhone.setOnClickListener {
                getSellingEntity?.categoryIdx = 4
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        getSellingEntity
                    )
                )
            }
            tvSmartWatch.setOnClickListener {
                getSellingEntity?.categoryIdx = 5
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        getSellingEntity
                    )
                )
            }
            tvNotebook.setOnClickListener {
                getSellingEntity?.categoryIdx = 6
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        getSellingEntity
                    )
                )
            }

            tvTablet.setOnClickListener {
                getSellingEntity?.categoryIdx = 7
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        getSellingEntity
                    )
                )
            }
            tvMonitor.setOnClickListener {
                getSellingEntity?.categoryIdx = 8
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        getSellingEntity
                    )
                )
            }
            tvGame.setOnClickListener {
                getSellingEntity?.categoryIdx = 9
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        getSellingEntity
                    )
                )
            }
            tvAudio.setOnClickListener {
                getSellingEntity?.categoryIdx = 10
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        getSellingEntity
                    )
                )
            }
            tvCamera.setOnClickListener {
                getSellingEntity?.categoryIdx = 11
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        getSellingEntity
                    )
                )
            }
            tvDrone.setOnClickListener {
                getSellingEntity?.categoryIdx = 12
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        getSellingEntity
                    )
                )
            }
            tvAnother.setOnClickListener {
                getSellingEntity?.categoryIdx = 13
                navigate(
                    SellingCategoryFragmentDirections.actionSellingCategoryFragmentToSellingFragment(
                        getSellingEntity
                    )
                )
            }
        }
    }
}
