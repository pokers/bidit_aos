package com.alexk.bidit.presentation.ui.selling

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.selling.SellingItemImgListAdapter
import com.alexk.bidit.common.util.setLoadingDialog
import com.alexk.bidit.common.util.setTextColorWithResourceCompat
import com.alexk.bidit.common.util.showLongToastMessage
import com.alexk.bidit.common.util.value.KeyConstants.FRAGMENT_KEY_SELLING_ITEM_CATEGORY_IDX
import com.alexk.bidit.common.util.value.KeyConstants.FRAGMENT_KEY_SELLING_ITEM_CATEGORY_LISTENER_KEY
import com.alexk.bidit.common.view.EditTextAutoCommaWatcher
import com.alexk.bidit.databinding.FragmentSellingBinding
import com.alexk.bidit.common.view.ViewState
import com.alexk.bidit.domain.entity.item.img.ItemImgEntity
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.selling.SellingActivity.Companion.SELLING_INFO
import com.alexk.bidit.presentation.ui.selling.calendar.SellingCalendarDialog
import com.alexk.bidit.presentation.ui.selling.dialog.SellingEssentialRequiredItemDialog
import com.alexk.bidit.presentation.ui.selling.time.SellingTimePickerDialog
import com.alexk.bidit.presentation.viewModel.ItemImgViewModel
import com.alexk.bidit.presentation.viewModel.ItemViewModel
import com.alexk.bidit.type.ItemAddInput
import com.apollographql.apollo3.api.Optional
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SellingFragment : BaseFragment<FragmentSellingBinding>(R.layout.fragment_selling) {

    private val itemImgViewModel by viewModels<ItemImgViewModel>()
    private val merchandiseViewModel by viewModels<ItemViewModel>()

    private val resultLauncherActivityInfo: ActivityResultLauncher<Intent> = initResultLauncher()
    private lateinit var itemImgAdapter: SellingItemImgListAdapter

    private val categoryList by lazy { resources.getStringArray(R.array.category_home_item) }
    private val yearList by lazy { resources.getStringArray(R.array.category_number_picker_year) }
    private val monthList by lazy { resources.getStringArray(R.array.category_number_picker_one_to_twelve) }
    private val dayList by lazy { resources.getStringArray(R.array.category_number_picker_one_to_thirty_one) }
    private val dayNightList by lazy { resources.getStringArray(R.array.category_number_picker_day) }
    private val hourList by lazy { resources.getStringArray(R.array.category_number_picker_zero_to_twelve) }
    private val minuteList by lazy { resources.getStringArray(R.array.category_number_zero_to_fifty_10) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObserveViewModel()
        initUiSetting()
        initButtonEvent()
    }

    private fun initUiSetting() {
        setAdjustPanMode()
        setCategoryReigstCallback()
        initItemImgListAdapter()

        restoreData()
    }

    private fun restoreData(){
        if (SELLING_INFO.endTime != null) setEndingTime()
        if (SELLING_INFO.endDate != null) setEndingDate()
        if (SELLING_INFO.imgUrlList?.size != 0) {
            binding.tvImgCount.text = "${SELLING_INFO.imgUrlList?.size}/10"
            itemImgAdapter.submitList(SELLING_INFO.imgUrlList?.toList())
        }
    }

    private fun initButtonEvent() {
        addEndingDateRemoveButtonEvent()
        addEndingDateRegistButton()
        addEndTimeRemoveButtonEvent()
        addEndTimeRegistButtonEvent()
        addImageAddButtonEvent()
        addEditBiddingPriceWatcher()
        addCategoryRegistButtonEvent()
        addPostRegistButtonEvent()
        addRemoveItemImgEvent()
    }

    private fun startObserveViewModel() {
        observeRegistMerchandise()
        observeImgUrl()
    }

    private fun setAdjustPanMode() {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    private fun initItemImgListAdapter() {
        itemImgAdapter = SellingItemImgListAdapter()

        binding.rvMerchandiseImgList.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = itemImgAdapter
        }
    }

    private fun addEndingDateRemoveButtonEvent() {
        binding.run {
            ivBiddingEndingDateDelete.setOnClickListener {
                tvBiddingEndingDate.text = "경매 마감 날짜"
                tvBiddingEndingDate.setTextColorWithResourceCompat(R.color.nobel)
                ivBiddingEndingDateDelete.visibility = View.INVISIBLE
                SELLING_INFO.endDate = null
            }
        }
    }

    private fun addEndingDateRegistButton() {
        binding.apply {
            tvBiddingEndingDate.setOnClickListener {
                showEndingDateDialog()
            }
        }
    }

    private fun showEndingDateDialog() {
        binding.run {
            tvBiddingEndingDate.setOnClickListener {
                val sellingDateDialog = SellingCalendarDialog {
                    setEndingDate()
                }
                sellingDateDialog.show(childFragmentManager, sellingDateDialog.tag)
            }
        }
    }

    private fun setEndingDate() {
        binding.apply {
            setEndingateToString()
            tvBiddingEndingDate.setTextColorWithResourceCompat(R.color.nero)
            ivBiddingEndingDateDelete.visibility = View.VISIBLE
        }
    }

    private fun setEndingateToString() {
        with(SELLING_INFO.endDate) {
            binding.tvBiddingEndingDate.text =
                "${yearList[this?.yearIdx!!]}년" + " ${monthList[this.monthIdx]}월" + " ${dayList[this.dayIdx]}일"
        }
    }


    private fun addEndTimeRemoveButtonEvent() {
        binding.run {
            ivBiddingEndingTimeDelete.setOnClickListener {
                tvBiddingEndingTime.text = "경매 마감 시간"
                tvBiddingEndingTime.setTextColorWithResourceCompat(R.color.nobel)
                ivBiddingEndingTimeDelete.visibility = View.INVISIBLE
                SELLING_INFO.endTime = null
            }
        }
    }

    private fun addEndTimeRegistButtonEvent() {
        binding.tvBiddingEndingTime.setOnClickListener {
            showEndTimeDialog()
        }
    }

    private fun showEndTimeDialog() {
        val sellingTimePickerDialog = SellingTimePickerDialog {
            setEndingTime()
        }
        sellingTimePickerDialog.show(childFragmentManager, sellingTimePickerDialog.tag)
    }

    private fun setEndingTime() {
        binding.run {
            setTimeToString()
            tvBiddingEndingTime.setTextColorWithResourceCompat(R.color.nero)
            ivBiddingEndingTimeDelete.visibility = View.VISIBLE
        }
    }

    private fun setTimeToString() {
        with(SELLING_INFO.endTime) {
            binding.tvBiddingEndingTime.text =
                dayNightList[this?.dateIdx!!] + " ${hourList[this.hourIdx]}시" + " ${minuteList[this.minuteIdx]}분"
        }
    }

    private fun setCategoryReigstCallback() {
        setFragmentResultListener(FRAGMENT_KEY_SELLING_ITEM_CATEGORY_LISTENER_KEY) { _, response ->
            setCategory(response.getInt(FRAGMENT_KEY_SELLING_ITEM_CATEGORY_IDX))
        }
    }

    private fun setCategory(idx: Int) {
        binding.tvCategory.apply {
            text = categoryList[idx]
            setTextColorWithResourceCompat(R.color.nero)
        }
        SELLING_INFO.categoryIdx = idx
        if (SELLING_INFO.endDate != null) setEndingTime()
        if (SELLING_INFO.endTime != null) setEndingDate()
    }

    private fun addCategoryRegistButtonEvent() {
        binding.tvCategory.setOnClickListener {
            navigate(
                SellingFragmentDirections.actionSellingFragmentToSellingCategoryFragment(
                    SELLING_INFO.categoryIdx ?: -1
                )
            )
        }
    }

    private fun addEditBiddingPriceWatcher() {
        binding.apply {
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
        }
    }

    private fun addImageAddButtonEvent() {
        binding.ivMerchandiseImg.setOnClickListener {
            if (SELLING_INFO.imgUrlList?.size == 10) {
                requireContext().showLongToastMessage("사진은 최대 10개까지 가능합니다.")
            } else {
                resultLauncherActivityInfo.launch(getImageResource())
            }
        }
    }

    private fun showEssentialRequiredItemDialog() {
        val dialog =
            SellingEssentialRequiredItemDialog(requireContext())
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun addItemPost() {
        val imgList = mutableListOf<String>()
        SELLING_INFO.imgUrlList?.forEach { imgList.add(it.imgUrl!!) }

        //view model을 여기서
        merchandiseViewModel.addItemInfo(
            ItemAddInput(
                categoryId = SELLING_INFO.categoryIdx!!,
                sPrice = binding.editBiddingStartPrice.text.toString().replace(",", "").toInt(),
                buyNow = Optional.Present(
                    binding.editBiddingImmediatePrice.text.toString().replace(",", "").toInt()
                ),
                title = binding.editPostTitle.text.toString(),
                name = categoryList[SELLING_INFO.categoryIdx!!],
                dueDate = setTimeType(),
                deliveryType = 2,
                sCondition = 0,
                aCondition = 10000,
            ),
            description = binding.editPostContent.text.toString(),
            images = imgList
        )
    }

    private fun addPostRegistButtonEvent() {
        binding.btnPostRegistration.setOnClickListener {
            if (validateSellingItem()) {
                addItemPost()
            } else {
                showEssentialRequiredItemDialog()
            }
        }
    }

    private fun setTimeType(): String {
        with(SELLING_INFO) {
            val calcHour = if (endTime?.dateIdx == 1) {
                hourList[endTime?.hourIdx!!].toInt().plus(12)
            } else {
                hourList[endTime?.hourIdx!!].toInt()
            }

            //2022-09-11T01:22:00.000Z
            return yearList[endDate?.yearIdx!!] +
                    "-${monthList[endDate?.monthIdx!!]}-" +
                    dayList[endDate?.dayIdx!!] +
                    "T${calcHour}:${minuteList[endTime?.minuteIdx!!]}:00.000Z"
        }
    }

    private fun validateSellingItem(): Boolean {
        with(binding) {
            return tvCategory.text != "" &&
                    editBiddingStartPrice.text.toString() != "" &&
                    editPostTitle.text.toString() != "" &&
                    editBiddingImmediatePrice.text.toString() != "" &&
                    SELLING_INFO.endDate != null &&
                    SELLING_INFO.endTime != null &&
                    editPostContent.text.toString().length > 10
        }
    }

    private fun initResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

                //uri
                val filePath = data?.data
                val inputStream: InputStream? =
                    requireContext().contentResolver.openInputStream(filePath!!)

                //file
                val file = File.createTempFile("image", filePath.lastPathSegment)
                val outStream: OutputStream = FileOutputStream(file)
                outStream.write(inputStream!!.readBytes())

                itemImgViewModel.uploadItemImg(filePath.lastPathSegment!! + ".png", file)
                restoreData()
            }
        }
    }

    private fun addItemImgList(itemUrl: ItemImgEntity) {
        SELLING_INFO.imgUrlList?.add(itemUrl)
        itemImgAdapter.submitList(SELLING_INFO.imgUrlList?.toList())
        binding.tvImgCount.text = "${SELLING_INFO.imgUrlList?.size}/10"
    }

    private fun addRemoveItemImgEvent() {
        itemImgAdapter.onItemClicked = {
            SELLING_INFO.imgUrlList?.remove(it!!)
            binding.tvImgCount.text = "${SELLING_INFO.imgUrlList?.size}/10"
            itemImgAdapter.submitList(null)
            itemImgAdapter.submitList(SELLING_INFO.imgUrlList?.toList())
        }
    }

    private fun successfulItemRegist() {
        Toast.makeText(requireContext(), "상품이 정상적으로 등록됐습니다.", Toast.LENGTH_LONG).show()
        activity?.finish()
    }

    private fun observeImgUrl() {
        itemImgViewModel.itemImgUrl?.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    context?.setLoadingDialog(true)
                }
                is ViewState.Success -> {
                    context?.setLoadingDialog(false)
                    addItemImgList(response.value!!)
                    itemImgViewModel.itemImgUrl?.value = null
                }
                is ViewState.Error -> {
                    context?.setLoadingDialog(false)
                }
            }
        }
    }

    private fun observeRegistMerchandise() {
        merchandiseViewModel.addItemStatus.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    context?.setLoadingDialog(true)
                }
                is ViewState.Success -> {
                    context?.setLoadingDialog(false)
                    if (response.value?.id != null) successfulItemRegist()
                }
                is ViewState.Error -> {
                    context?.setLoadingDialog(false)
                }
                else -> {}
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
