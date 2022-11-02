package com.alexk.bidit.common.util.value

object ApolloErrorConstant {
    const val NotSupportedParameters = "passing first and last are not supported"
    const val ModuleNotFound = "Module not found"
    const val ErrorInvalidBodyParameter = "Missing required parameter"
    const val ErrorNotMatchedPasswd = "Please check password"
    const val ErrorUserNotFound = "Could not find user"
    const val ErrorItemNotFound = "Could not find item"
    const val ErrorInvalidString = "Invalid String, it might be zero length"
    const val ErrorExceedStringLength = "Invalid String, it might be longer string than maxium length"
    const val ErrorInvalidPageInfo = "Invalid page number or size."
    const val ErrorNotMatchedArticle = "Invalid parent comment ID or article ID"
    const val ErrorInvalidToken = "Invalid token, it can be expired or wrong token"
    const val ErrorRequireAddUser = "Cannot find user information, Please add user first."
    const val ErrorNotFoundSocialUserInfo = "Cannot find Social user Info."
    const val ErrorCouldNotAdd = "Could not add item"
    const val ErrorDuplicatedItem = "Could not completed due to duplicated item"
    const val ErrorLowPriceBidding = "Could not bid low price than current."
    const val ErrorSameUserBidding = "Could not bid over your price."
    const val ErrorOwnItemBidding = "Not allowed to bid your item."
}