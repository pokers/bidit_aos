package com.alexk.bidit.presentation.ui.selling.category

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.selling.SellingItemCategoryListAdapter
import com.alexk.bidit.common.adapter.selling.callback.SellingItemCategoryClickListener
import com.alexk.bidit.common.util.setTextColorWithResourceCompat
import com.alexk.bidit.common.util.value.SELLING_ITEM_CATEGORY_IDX
import com.alexk.bidit.common.util.value.SELLING_ITEM_CATEGORY_LISTENER_KEY
import com.alexk.bidit.databinding.FragmentSellingCategoryBinding
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.selling.SellingFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SellingCategoryFragment :
    BaseFragment<FragmentSellingCategoryBinding>(R.layout.fragment_selling_category) {

    private val args : SellingCategoryFragmentArgs by navArgs()
    private val categoryIdx by lazy { args.categoryIndexValue }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCategoryList()
    }

    private fun initCategoryAdapter() : SellingItemCategoryListAdapter{
        return SellingItemCategoryListAdapter().apply {
            selectedIndex(categoryIdx)
            addCategoryItem(resources.getStringArray(R.array.category_home_item).toList())
            setItemCategoryClickListener(object : SellingItemCategoryClickListener{
                override fun onCategoryClick(index: Int) {
                    setFragmentResult(SELLING_ITEM_CATEGORY_LISTENER_KEY , bundleOf(SELLING_ITEM_CATEGORY_IDX to index))
                    navPopStack()
                }
            })
        }
    }

    private fun initCategoryList(){
        binding.rvCategoryList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = initCategoryAdapter()
        }
    }
}
