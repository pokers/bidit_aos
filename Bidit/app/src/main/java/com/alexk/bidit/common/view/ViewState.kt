package com.alexk.bidit.common.view

//서버 통신 상태
sealed class ViewState<T>(
    val value: T? = null,
    val message: String? = null
) {
    //다른 클래스에서 바꾸는 것을 금지
    class Success<T>(data: T) : ViewState<T>(data)
    class Error<T>(message: String?, data: T? = null) : ViewState<T>(data, message)
    class Loading<T> : ViewState<T>()
}