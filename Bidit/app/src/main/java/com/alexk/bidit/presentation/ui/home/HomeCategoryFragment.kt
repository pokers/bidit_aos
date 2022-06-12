package com.alexk.bidit.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.home.HomeMerchandiseListAdapter
import com.alexk.bidit.data.service.response.home.HomeResponse
import com.alexk.bidit.databinding.FragmentHomeMerchandiseListBinding
import com.alexk.bidit.presentation.base.BaseFragment

class HomeCategoryFragment :
    BaseFragment<FragmentHomeMerchandiseListBinding>(R.layout.fragment_home_merchandise_list) {
    private val getListData: List<HomeResponse>? by lazy { arguments?.getParcelableArrayList("listData") }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun init() {
        binding.apply {
            rvMerchandiseList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvMerchandiseList.adapter = HomeMerchandiseListAdapter(requireContext(), getListData!!)
        }
    }

    override fun initEvent() {

    }

    companion object {
        private var homeCategoryFragment: Fragment? = null
        fun getFragmentInstance(): Fragment? {
            homeCategoryFragment = HomeCategoryFragment()
            return homeCategoryFragment
        }
    }
}