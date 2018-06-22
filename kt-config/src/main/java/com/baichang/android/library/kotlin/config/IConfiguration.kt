package com.baichang.android.library.kotlin.config

interface IConfiguration {
  fun getApiDefaultHost(): String
  fun getApiLoadImage(): String
  fun getApiDownloadFile(): String
  fun getApiUploadFile(): String
  fun getColorAccent(): Int
  fun <T> getServiceApi(): Class<T>
}