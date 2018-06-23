package com.baichang.android.kotlin.kit.http

import com.baichang.android.kotlin.kit.config.Configuration
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by iCong on 06/01/2018.
 */
object DownloadFactory {

  private val httpClient = OkHttpClient.Builder()
      .connectTimeout(3, TimeUnit.MINUTES)
      .readTimeout(3, TimeUnit.MINUTES)
      .writeTimeout(3, TimeUnit.MINUTES)
      .addInterceptor(DownloadInterceptor())
      .build()

  /**
   * 创建下载请求
   *
   * @param url 下载地址
   * @param clazz 下载接口类
   */
  fun <T> create(
    url: String,
    clazz: Class<T>
  ): T {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(httpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(clazz)
  }

  /**
   * 创建下载请求
   *
   * @param clazz 下载接口类
   */
  fun <T> create(
    clazz: Class<T>
  ): T {
    return Retrofit.Builder()
        .baseUrl(Configuration.getApiDownloadFile())
        .client(httpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(clazz)
  }

  /**
   * 获取默认的下载请求
   *
   */
  fun <T> create(): T {
    return Retrofit.Builder()
        .baseUrl(Configuration.getApiDownloadFile())
        .client(httpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Configuration.getServiceApi())
  }
}