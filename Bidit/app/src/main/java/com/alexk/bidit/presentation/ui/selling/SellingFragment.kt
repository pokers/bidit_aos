package com.alexk.bidit.presentation.ui.selling

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexk.bidit.R
import com.alexk.bidit.common.adapter.selling.SellingItemImgListAdapter
import com.alexk.bidit.common.util.setLoadingDialog
import com.alexk.bidit.common.util.setTextColorWithResourceCompat
import com.alexk.bidit.common.util.view.EditTextAutoCommaWatcher
import com.alexk.bidit.databinding.FragmentSellingBinding
import com.alexk.bidit.common.util.view.ViewState
import com.alexk.bidit.domain.entity.selling.SellingCalendarEntity
import com.alexk.bidit.domain.entity.selling.SellingEntity
import com.alexk.bidit.domain.entity.selling.SellingTimeEntity
import com.alexk.bidit.presentation.base.BaseFragment
import com.alexk.bidit.presentation.ui.selling.dialog.SellingCalendarDialog
import com.alexk.bidit.presentation.ui.selling.dialog.SellingEssentialRequiredItemDialog
import com.alexk.bidit.presentation.ui.selling.dialog.SellingTimePickerDialog
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
import java.lang.Exception


@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SellingFragment : BaseFragment<FragmentSellingBinding>(R.layout.fragment_selling) {

    private lateinit var resultLauncherActivityInfo: ActivityResultLauncher<Intent>
    private val itemImgViewModel by viewModels<ItemImgViewModel>()
    private val merchandiseViewModel by viewModels<ItemViewModel>()

    private val itemUrlImgList by lazy {
        try {
            args.saveSellingData?.imgUrlList
        } catch (e: Exception) {
            mutableListOf()
        }
    }
    private val itemImgAdapter by lazy { SellingItemImgListAdapter() }

    private val args: SellingFragmentArgs by navArgs()
    private val getSellingEntity by lazy {
        try {
            args.saveSellingData
        } catch (e: Exception) {
            Log.e("args", e.toString())
            SellingEntity(
                itemUrlImgList!!, "", -1, "", "",
                SellingCalendarEntity(-1, -1, -1), SellingTimeEntity
                    (-1, -1, -1), ""
            )
        }
    }

    private val categoryList by lazy { resources.getStringArray(R.array.category_home_item) }

    private val yearList by lazy { resources.getStringArray(R.array.category_number_picker_year) }
    private val monthList by lazy { resources.getStringArray(R.array.category_number_picker_one_to_twelve) }
    private val dayList by lazy { resources.getStringArray(R.array.category_number_picker_one_to_thirty_one) }

    private val dayNightList by lazy { resources.getStringArray(R.array.category_number_picker_day) }
    private val hourList by lazy { resources.getStringArray(R.array.category_number_picker_zero_to_twelve) }
    private val minuteList by lazy { resources.getStringArray(R.array.category_number_zero_to_fifty_10) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        init()
        initEvent()
    }

    private fun init() {
        observeMerchandise()
        observeImgUrl()
        setUi(getSellingEntity!!)
        resultLauncherActivityInfo = initResultLauncher()
        binding.apply {
            rvMerchandiseImgList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvMerchandiseImgList.adapter = itemImgAdapter
        }
    }

    private fun initEvent() {
        binding.apply {
            ivBiddingEndingDateDelete.setOnClickListener {
                tvBiddingEndingDate.text = "경매 마감 날짜"
                tvBiddingEndingDate.setTextColorWithResourceCompat(R.color.nero)
                ivBiddingEndingDateDelete.visibility = View.INVISIBLE
            }

            tvBiddingEndingDate.setOnClickListener {
                if(getSellingEntity?.endData?.yearIdx == -1){
                    getSellingEntity?.endData = SellingCalendarEntity(0,6,3)
                }
                val sellingDateDialog = SellingCalendarDialog(getSellingEntity?.endData) {
                    tvBiddingEndingDate.text = setDateString(it)
                    tvBiddingEndingDate.setTextColorWithResourceCompat(R.color.nero)
                    ivBiddingEndingDateDelete.visibility = View.VISIBLE
                    getSellingEntity?.endData = it
                }
                sellingDateDialog.show(childFragmentManager, sellingDateDialog.tag)
            }

            ivBiddingEndingTimeDelete.setOnClickListener {
                tvBiddingEndingTime.text = "경매 마감 시간"
                tvBiddingEndingTime.setTextColorWithResourceCompat(R.color.nobel)
                ivBiddingEndingTimeDelete.visibility = View.INVISIBLE
            }

            tvBiddingEndingTime.setOnClickListener {
                if(getSellingEntity?.endTime?.minuteIdx == -1){
                    getSellingEntity?.endTime = SellingTimeEntity(1,6,3)
                }
                val sellingTimePickerDialog = SellingTimePickerDialog(getSellingEntity?.endTime) {
                    tvBiddingEndingTime.text = setTimeString(it)
                    getSellingEntity?.endTime = it
                    tvBiddingEndingTime.setTextColorWithResourceCompat(R.color.nero)
                    ivBiddingEndingTimeDelete.visibility = View.VISIBLE
                }
                sellingTimePickerDialog.show(childFragmentManager, sellingTimePickerDialog.tag)
            }

            tvCategory.setOnClickListener {
                getSellingEntity?.title = editPostTitle.text.toString()
                getSellingEntity?.immediatePrice = editBiddingImmediatePrice.text.toString()
                getSellingEntity?.startPrice = editBiddingStartPrice.text.toString()
                getSellingEntity?.description = editPostContent.text.toString()
                getSellingEntity?.imgUrlList = itemUrlImgList!!

                navigate(
                    SellingFragmentDirections.actionSellingFragmentToSellingCategoryFragment(
                        getSellingEntity
                    )
                )
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
                if (checkSignPost()) {
                    val dialog =
                        SellingEssentialRequiredItemDialog(requireContext())
                    dialog.setCanceledOnTouchOutside(true)
                    dialog.show()
                    dialog.window?.setLayout(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                    )
                } else {
                    val imgList = mutableListOf<String>()

                    for (dataIdx in itemUrlImgList?.indices!!) {
                        imgList.add(itemUrlImgList!![dataIdx].imgUrl!!)
                    }

                    //로직 확인
                    merchandiseViewModel.addItemInfo(
                        ItemAddInput(
                            categoryId = getSellingEntity?.categoryIdx!!,
                            sPrice = editBiddingStartPrice.text.toString().replace(",", "").toInt(),
                            buyNow = Optional.Present(
                                editBiddingImmediatePrice.text.toString().replace(",", "").toInt()
                            ),
                            title = editPostTitle.text.toString(),
                            name = categoryList[getSellingEntity?.categoryIdx?.minus(2)!!],
                            dueDate = setTimeType(getSellingEntity!!),
                            deliveryType = 1,
                            sCondition = 1,
                            aCondition = 1,
                        ),
                        description = editPostContent.text.toString(),
                        images = imgList
                    )
                }
            }
        }
    }

    private fun setTimeType(data: SellingEntity): String {
        val calcHour = if (data.endTime?.dateIdx == 1) {
            hourList[data.endTime?.hourIdx!!].toInt().plus(12)
        } else {
            hourList[data.endTime?.hourIdx!!].toInt()
        }

        return yearList[data.endData?.yearIdx!!] +
                "-${monthList[data.endData?.monthIdx!!]}-" +
                dayList[data.endData?.dayIdx!!] +
                "T${calcHour}:${minuteList[data.endTime?.minuteIdx!!]}:00.000Z"
    }

    private fun checkSignPost(): Boolean {
        binding.apply {
            return tvCategory.text == "" ||
                    editBiddingStartPrice.text.toString() == "" ||
                    editPostTitle.text.toString() == "" ||
                    editBiddingImmediatePrice.text.toString() == "" ||
                    tvBiddingEndingDate.text == "" ||
                    tvBiddingEndingTime.text == "" ||
                    editPostContent.text.toString().length < 10
        }
    }

    private fun setUi(data: SellingEntity) {
        binding.apply {
            data.run {
                if (imgUrlList.size == 0) {
                    itemImgAdapter.submitList(emptyList())
                    tvImgCount.text = "0/10"
                } else {
                    itemImgAdapter.submitList(imgUrlList)
                    tvImgCount.text = "${imgUrlList.size}/10"
                }
                editPostTitle.setText(title)
                editPostContent.setText(description)
                if (data.categoryIdx != -1) {
                    tvCategory.text = categoryList[categoryIdx?.minus(2)!!]
                    tvCategory.setTextColorWithResourceCompat(R.color.nero)
                }
                editBiddingStartPrice.setText(startPrice)
                if (startPrice != "") {
                    tvStartPriceWon.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.nero,
                            null
                        )
                    )
                }
                editBiddingImmediatePrice.setText(immediatePrice)
                if (immediatePrice != "") {
                    tvImmediatePriceWon.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.nero,
                            null
                        )
                    )
                }
                tvBiddingEndingDate.text = setDateString(endData)
                tvBiddingEndingTime.text = setTimeString(endTime)
            }
        }
    }

    private fun setDateString(date: SellingCalendarEntity?): String {
        return if (date?.yearIdx == -1) {
            binding.tvBiddingEndingDate.setTextColorWithResourceCompat(R.color.nobel)
            "경매 마감 날짜"
        } else {
            binding.tvBiddingEndingDate.setTextColorWithResourceCompat(R.color.nero)
            binding.ivBiddingEndingDateDelete.visibility = View.VISIBLE
            "${yearList[date?.yearIdx!!]}년" + " ${monthList[date.monthIdx]}월" + " ${dayList[date.dayIdx]}일"
        }
    }

    private fun setTimeString(time: SellingTimeEntity?): String {
        return if (time?.dateIdx == -1) {
            binding.tvBiddingEndingTime.setTextColorWithResourceCompat(R.color.nobel)
            "경매 마감 시간"
        } else {
            binding.tvBiddingEndingTime.setTextColorWithResourceCompat(R.color.nero)
            binding.ivBiddingEndingTimeDelete.visibility = View.VISIBLE
            dayNightList[time?.dateIdx!!] + " ${hourList[time.hourIdx]}시" + " ${minuteList[time.minuteIdx]}분"
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

                Log.d("test file path", filePath.lastPathSegment!!)

                itemImgViewModel.uploadItemImg(filePath.lastPathSegment!! + ".png", file)
            }
        }
    }

    private fun observeImgUrl() {
        itemImgViewModel.itemUrl.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    context?.setLoadingDialog(true)
                    Log.d("img upload", "Loading img upload")
                }
                is ViewState.Success -> {
                    context?.setLoadingDialog(false)
                    Log.d("img upload", "Success img upload")
                    itemUrlImgList?.add(response.value!!)
                    itemImgAdapter.submitList(itemUrlImgList?.toList())
                    binding.tvImgCount.text = "${itemUrlImgList?.size}/10"
                    itemImgAdapter.onItemClicked = {
                        itemUrlImgList?.remove(it!!)
                        binding.tvImgCount.text = "${itemUrlImgList!!.size}/10"
                        itemImgAdapter.submitList(null)
                        itemImgAdapter.submitList(itemUrlImgList?.toList())
                    }
                }
                else -> {
                    context?.setLoadingDialog(false)
                    Log.d("img error", "Error img upload")
                    Log.d("why error", response.message.toString())
                }
            }
        }
    }

    private fun observeMerchandise() {
        merchandiseViewModel.addItemStatus.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    context?.setLoadingDialog(true)
                    Log.d("Add item", "Loading")
                }
                is ViewState.Success -> {
                    context?.setLoadingDialog(false)
                    Log.d("Add item", "Success")
                    if (response.value?.data?.addItem == null) {
                        Log.d("Add item", "Error")
                    } else {
                        Toast.makeText(requireContext(), "상품이 정상적으로 등록됐습니다.", Toast.LENGTH_LONG)
                            .show()
                        activity?.finish()
                    }
                }
                else -> {
                    context?.setLoadingDialog(false)
                    Log.d("Add item", "Error")
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
