package com.alexk.bidit.domain.entity.item.connection

import com.alexk.bidit.domain.entity.item.ItemBaiscEntity

data class ItemEdgeEntity(
    var item : ItemBaiscEntity? = null,
    var cursor : String? = null
)
