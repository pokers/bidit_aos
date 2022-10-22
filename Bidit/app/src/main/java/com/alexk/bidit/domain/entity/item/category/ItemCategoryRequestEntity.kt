package com.alexk.bidit.domain.entity.item.category

import com.alexk.bidit.type.CursorType

data class ItemCategoryRequestEntity(
    val deliveryType : Int,
    val minUsingTime : Int,
    val maxUsingTime : Int,
    val minStartPrice : Int,
    val maxStartPrice : Int,
    val minImmediatePrice : Int,
    val maxImmediatePrice : Int,
    val categoryId : Int,
    val cursorType: CursorType
)
