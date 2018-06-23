package com.baichang.android.kotlin.kit.http

data class HttpError(private val error: String) : Throwable(error)