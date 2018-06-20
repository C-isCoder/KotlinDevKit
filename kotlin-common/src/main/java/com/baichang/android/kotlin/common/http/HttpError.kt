package com.baichang.android.kotlin.common.http

data class HttpError(private val error: String) : Throwable(error)