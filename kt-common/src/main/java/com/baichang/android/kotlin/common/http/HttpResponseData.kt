package com.baichang.android.kotlin.common.http

/**
 * Created by iCong on 09/01/2018.
 */
data class HttpResponseData(
  val code: Int,
  val msg: String,
  val data: Any
)
