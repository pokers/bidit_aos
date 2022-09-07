package com.alexk.bidit.domain.entity.item

data class ItemEntity(
    var id: Int?,
    var status: Int?,
    var sPrice : Int?,
    var cPrice : Int?,
    var viewCount : Int?,
    var title : String?,
    var createdAt : String?,
    var dueDate :String?,
    var itemImgList : MutableList<ItemImgEntity>
)