package com.alexk.bidit.domain.entity.item.connection

import com.alexk.bidit.domain.entity.item.ItemBasicEntity

data class ItemEdgeEntity(
    var item : ItemBasicEntity? = null,
    var cursor : String? = null
)
