package com.baichang.android.library.kotlin.config

class Configuration {

  companion object {

    private lateinit var config: ConfigurationBuilder

    fun init(builder: ConfigurationBuilder) {
      config = builder
    }

    fun get(): ConfigurationBuilder = config
  }

  inner class ConfigurationBuilder {

    private var API_DEFAULT_HOST = ""
    private var API_UPLOAD_FILE = ""
    private var API_LOAD_IMAGE = ""
    private var API_DOWNLOAD_FILE = ""
    private var COLOR_ACCENT_RESOURCE = 0

    fun setApiDefaultHost(url: String) {
      API_DEFAULT_HOST = url
    }

    fun getApiDefaultHost(): String = API_DEFAULT_HOST

    fun setApiUploadFile(url: String) {
      API_UPLOAD_FILE = url
    }

    fun getApiUploadFile() = API_UPLOAD_FILE

    fun setApiLoadImage(url: String) {
      API_LOAD_IMAGE = url
    }

    fun getApiLoadImage() = API_UPLOAD_FILE

    fun setApiDownloadFile(url: String) {
      API_DOWNLOAD_FILE = url
    }

    fun getApiDownloadFile() = API_DOWNLOAD_FILE

    fun setColorAccent(id: Int) {
      COLOR_ACCENT_RESOURCE = id
    }

    fun getColorAccent() = COLOR_ACCENT_RESOURCE

  }
}