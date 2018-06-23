package com.baichang.android.kotlin.kit.config

class Configuration {

  companion object : IConfiguration {
    private lateinit var config: IConfiguration
    private var isDebug = true
    fun init(
      config: IConfiguration,
      isDebug: Boolean = false
    ) {
      this.config = config
      this.isDebug = isDebug
    }

    fun isDebug(): Boolean = isDebug
    override fun getApiDefaultHost(): String = config.getApiDefaultHost()
    override fun getApiLoadImage(): String = config.getApiLoadImage()
    override fun getApiDownloadFile(): String = config.getApiDownloadFile()
    override fun getApiUploadFile(): String = config.getApiUploadFile()
    override fun getColorAccent(): Int = config.getColorAccent()
    override fun <T> getServiceApi(): Class<T> = config.getServiceApi()
  }

}