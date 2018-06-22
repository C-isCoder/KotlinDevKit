@file:JvmName("Configuration")

package com.baichang.android.library.kotlin.config

class Configuration : IConfiguration {

  companion object {
    private lateinit var config: IConfiguration
    fun init(builder: IConfiguration) {
      this.config = builder
    }

    fun get() = config
  }

  override fun getApiDefaultHost(): String = config.getApiDefaultHost()
  override fun getApiLoadImage(): String = config.getApiLoadImage()
  override fun getApiDownloadFile(): String = config.getApiDownloadFile()
  override fun <T> getServiceApi(): Class<T> = config.getServiceApi()
  override fun getApiUploadFile(): String = config.getApiUploadFile()
  override fun getColorAccent(): Int = config.getColorAccent()

}