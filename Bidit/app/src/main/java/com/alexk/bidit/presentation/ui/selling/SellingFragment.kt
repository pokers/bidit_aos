package com.alexk.bidit.presentation.ui.selling

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.selling.SellingItemImgListAdapter
import com.alexk.bidit.common.util.view.EditTextAutoCommaWatcher
import com.alexk.bidit.databinding.FragmentSellingBinding
import com.alexk.bidit.di.ViewState
import com.alexk.bidit.domain.entity.merchandise.MerchandiseImgEntity
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.selling.dialog.SellingEssentialRequiredItemDialog
import com.alexk.bidit.presentation.viewModel.ItemImgViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SellingFragment : BaseFragment<FragmentSellingBinding>(R.layout.fragment_selling) {

    private lateinit var resultLauncherActivityInfo: ActivityResultLauncher<Intent>
    private val itemImgViewModel by viewModels<ItemImgViewModel>()
    private val itemUrlImgList = mutableListOf<MerchandiseImgEntity>()
    private val itemImgAdapter by lazy { SellingItemImgListAdapter() }
    private val args: SellingFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEvent()
    }

    override fun init() {
        observeImgUrl()
        resultLauncherActivityInfo =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    //가져온 사진 데이터, 만약 Glide를 사용한다고 하면 load에 uri를 넣어주자.

                    //uri
                    val filePath = data?.data
                    val inputStream: InputStream? =
                        requireContext().contentResolver.openInputStream(filePath!!)

                    //file
                    val file = File.createTempFile("image", filePath.lastPathSegment)
                    val outStream: OutputStream = FileOutputStream(file)
                    outStream.write(inputStream!!.readBytes())

                    Log.d("test file path", filePath.lastPathSegment!!)

                    itemImgViewModel.uploadItemImg(filePath.lastPathSegment!! + ".png", file)
                }
            }

        binding.apply {
            if (args.category == "") {
                tvCategory.text = "카테고리"
            } else {
                tvCategory.setTextColor(ResourcesCompat.getColor(resources, R.color.nero, null))
                tvCategory.text = args.category
            }
            rvMerchandiseImgList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvMerchandiseImgList.adapter = itemImgAdapter
        }
    }

    override fun initEvent() {
        binding.apply {
            tvCategory.setOnClickListener {
                navigate(SellingFragmentDirections.actionSellingFragmentToSellingCategoryFragment())
            }

            editBiddingImmediatePrice.apply {
                val immediatePriceTextWatcher = EditTextAutoCommaWatcher(this)
                immediatePriceTextWatcher.wonTextView = tvImmediatePriceWon
                addTextChangedListener(immediatePriceTextWatcher)
            }
            editBiddingStartPrice.apply {
                val startPriceTextWatcher = EditTextAutoCommaWatcher(this)
                startPriceTextWatcher.wonTextView = tvStartPriceWon
                addTextChangedListener(startPriceTextWatcher)
            }

            ivMerchandiseImg.setOnClickListener {
                resultLauncherActivityInfo.launch(getImageResource())
            }

            btnPostRegistration.setOnClickListener {
                if (tvCategory.text == "" ||
                    editBiddingStartPrice.text.toString() == "" ||
                    editPostTitle.text.toString() == "" ||
                    editBiddingImmediatePrice.text.toString() == "" ||
                    tvBiddingEndingDate.text == "" ||
                    tvBiddingEndingTime.text == ""
                ) {
                    val dialog =
                        SellingEssentialRequiredItemDialog(requireContext())
                    dialog.setCanceledOnTouchOutside(true)
                    dialog.show()
                    dialog.window?.setLayout(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                    )
//                    dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
                }
            }
        }
    }


    private fun observeImgUrl() {
        itemImgViewModel.itemUrl.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    Log.d("img upload", "Loading img upload")
                }
                is ViewState.Success -> {
                    Log.d("img upload", "Success img upload")
                    itemUrlImgList.add(response.value!!)
                    itemImgAdapter.submitList(itemUrlImgList)
                    itemImgAdapter.onItemClicked = {
                        itemUrlImgList.removeAt(it!!)
                        itemImgAdapter.submitList(itemUrlImgList)
                    }
                }
                else -> {
                    Log.d("img error", "Error img upload")
                    Log.d("why error", response.message.toString())
                }
            }
        }
    }

    private fun getImageResource(): Intent {
        return Intent().apply {
            action = Intent.ACTION_OPEN_DOCUMENT
            type = "image/*"
        }
    }
}
