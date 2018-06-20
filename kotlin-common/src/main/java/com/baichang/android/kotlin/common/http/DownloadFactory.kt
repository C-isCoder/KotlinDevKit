package com.baichang.android.kotlin.common.http

import com.baichang.android.library.kotlin.config.Configuration
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by iCong on 06/01/2018.
 */
object DownloadFactory {

  private val client = OkHttpClient.Builder()
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
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(clazz)
  }

  /**
   * 创建下载请求
   *
   * @param url 下载地址
   */
  fun <T> create(
    url: String
  ): T {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Configuration<T>().get().apiClazz)
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
        .baseUrl(Configuration<T>().get().API_DOWNLOAD_FILE)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(clazz)
  }

  /**
   * 获取默认的下载请求
   *
   */
  fun <T> getInstance(): T {
    return Retrofit.Builder()
        .baseUrl(Configuration<T>().get().API_DOWNLOAD_FILE)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Configuration<T>().get().apiClazz)
  }
}