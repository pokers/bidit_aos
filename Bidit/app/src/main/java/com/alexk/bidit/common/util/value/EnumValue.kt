package com.alexk.bidit.common.util.value

enum class PostProcessType {
    MODIFY, DELETE
}

enum class ItemStatus(val status: Int) {
    REGISTED(0), ONGOING(1), SOLD(2), END(3), CANCEL(4)
}

enum class BidStatus(val status: Int) {
    VALID(0), INVALID(1)
}