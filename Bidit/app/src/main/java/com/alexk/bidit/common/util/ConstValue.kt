package com.alexk.bidit.common.util

import com.alexk.bidit.GlobalApplication
import com.alexk.bidit.R

//SP Value
const val TOKEN = "Token"
const val PUSH_TOKEN = "UpdatePushToken"
const val KEYWORD = "keyword"

//URL
const val BASE_URL = "https://wypcpelqdbhlxgrexisgez7vba.appsync-api.ap-northeast-2.amazonaws.com/graphql/"
const val S3_BUCKET_URL = "bidit-itemimage/mvp_model"

//Apollo Error
const val NotSupportedParameters = "passing first and last are not supported"
const val ModuleNotFound = "Module not found"
const val ErrorInvalidBodyParameter = "Missing required parameter"
const val ErrorNotMatchedPasswd = "Please check password"
const val ErrorUserNotFound = "Could not find User"
const val ErrorItemNotFound = "Could not find item"
const val ErrorInvalidString = "Invalid String, it might be zero length"
const val ErrorExceedStringLength = "Invalid String, it might be longer string than maxium length"
const val ErrorInvalidPageInfo = "Invalid page number or size."
const val ErrorNotMatchedArticle = "Invalid parent comment ID or article ID"
const val ErrorInvalidToken = "Invalid token, it can be expired or wrong token"
const val ErrorRequireAddUser = "Cannot find User information, Please add user first."
const val ErrorNotFoundSocialUserInfo = "Cannot find Social User Info."
const val ErrorCouldNotAdd = "Could not add item"
const val ErrorDuplicatedItem = "Could not completed due to duplicated item"
const val ErrorLowPriceBidding = "Could not bid low price than current."
const val ErrorSameUserBidding = "Could not bid over your price."
const val ErrorOwnItemBidding = "Not allowed to bid your item."