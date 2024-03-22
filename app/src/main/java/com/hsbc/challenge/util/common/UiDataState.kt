package com.hsbc.challenge.util.common

import com.hsbc.challenge.view.ErrorText

sealed class UiDataState<T> {
    class Idle<T>: UiDataState<T>()
    class Loading<T>: UiDataState<T>()
    data class Error<T>(val error: ErrorText) : UiDataState<T>()
    data class Loaded<T>(val data: T): UiDataState<T>()
}