package com.alexk.bidit.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.alexk.bidit.common.adapter.merchandise.MerchandiseListAdapter
import com.alexk.bidit.common.util.GridRecyclerViewDeco
import com.alexk.bidit.databinding.FragmentHomeMerchandiseListBinding
import com.alexk.bidit.tempResponse.HomeResponse

class HomeCategoryFragment :
    Fragment() {

    private val getListData: List<HomeResponse>? by lazy { arguments?.getParcelableArrayList("listData") }
    private var _binding: FragmentHomeMerchandiseListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeMerchandiseListBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun init() {
        binding.apply {
            rvMerchandiseList.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            rvMerchandiseList.adapter = MerchandiseListAdapter(requireContext(), getListData!!)
            rvMerchandiseList.addItemDecoration((GridRecyclerViewDeco(0, 80, 40, 0)))
        }
    }
}