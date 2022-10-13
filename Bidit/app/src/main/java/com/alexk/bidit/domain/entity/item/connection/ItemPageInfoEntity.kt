package com.alexk.bidit.domain.entity.item.connection

data class ItemPageInfoEntity(
    var startCursor : String? = null,
    var endCursor : String? = null,
    var hasNextPage : Boolean?,
    var hasPrevPage : Boolean?
)