package com.alexk.bidit.domain.entity.item.connection

import com.alexk.bidit.domain.entity.item.ItemBasicEntity

data class ItemConnectionEntity(
    var totalCount : Int? = null,
    var itemPageInfo : ItemPageInfoEntity? = null,
    var itemList : List<ItemBasicEntity>? = null,
)
