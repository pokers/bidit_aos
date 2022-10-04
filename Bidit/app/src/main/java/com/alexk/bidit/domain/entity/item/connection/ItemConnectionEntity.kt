package com.alexk.bidit.domain.entity.item.connection

data class ItemConnectionEntity(
    var totalCount : Int? = null,
    var itemEdge : List<ItemEdgeEntity>? = null,
)
