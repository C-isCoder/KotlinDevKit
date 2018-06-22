package com.baichang.android.kotlin.common.http

/**
 * Created by iCong on 2017/6/28.
 */
data class HttpResponse(
  val state: Int,
  val res: HttpResponseData?,
  val msg: String
)
