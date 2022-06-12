package com.alexk.bidit.common.component

import com.alexk.bidit.R
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.alexk.bidit.databinding.ItemCategoryListBinding
import com.bumptech.glide.Glide


class CategoryListItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var _binding: ItemCategoryListBinding? = null
    private val binding: ItemCategoryListBinding get() = _binding!!

    init {
        _binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_category_list,
            this,
            false
        )
        addView(binding.root)
    }

    //카테고리 이름 지정
    fun setCategoryName(name: String) {
        binding.tvCategory.text = name
    }

    //첫 아이템을 지정
    fun setFirstSelectItem() {
        binding.tvCategory.apply {
            setTextColor(
                ResourcesCompat.getColor(
                    context.resources,
                    R.color.black,
                    null
                )
            )
            typeface = ResourcesCompat.getFont(context, R.font.notosans_kr_bold)
        }
    }

    //카테고리 클릭
    //마지막 매개변수가 클릭되는 아이템
    fun clickCategory(
        firstLeftItem: CategoryListItem,
        secondLeftItem: CategoryListItem,
        thirdLeftItem: CategoryListItem,
        fourthLeftItem: CategoryListItem,
        clickItem: CategoryListItem
    ) {
        firstLeftItem.binding.tvCategory.apply {
            setTextColor(ResourcesCompat.getColor(context.resources, R.color.nobel, null))
            typeface = ResourcesCompat.getFont(context, R.font.notosans_kr_medium)
        }

        secondLeftItem.binding.tvCategory.apply {
            setTextColor(ResourcesCompat.getColor(context.resources, R.color.nobel, null))
            typeface = ResourcesCompat.getFont(context, R.font.notosans_kr_medium)
        }

        thirdLeftItem.binding.tvCategory.apply {
            setTextColor(ResourcesCompat.getColor(context.resources, R.color.nobel, null))
            typeface = ResourcesCompat.getFont(context, R.font.notosans_kr_medium)
        }

        fourthLeftItem.binding.tvCategory.apply {
            setTextColor(ResourcesCompat.getColor(context.resources, R.color.nobel, null))
            typeface = ResourcesCompat.getFont(context, R.font.notosans_kr_medium)
        }

        clickItem.binding.tvCategory.apply {
            setTextColor(ResourcesCompat.getColor(context.resources, R.color.nobel, null))
            typeface = ResourcesCompat.getFont(context, R.font.notosans_kr_medium)
        }

        clickItem.binding.tvCategory.apply {
            setTextColor(
                ResourcesCompat.getColor(
                    context.resources,
                    R.color.black,
                    null
                )
            )
            typeface = ResourcesCompat.getFont(context, R.font.notosans_kr_bold)
        }
    }
}

