package com.baichang.android.kotlin.common.http

import com.baichang.android.kotlin.common.config.Configuration
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by iCong on 06/01/2018.
 */
object UploadFactory {

  private val httpClient = OkHttpClient.Builder()
      .connectTimeout(1, TimeUnit.MINUTES)
      .readTimeout(1, TimeUnit.MINUTES)
      .writeTimeout(1, TimeUnit.MINUTES)
      .addInterceptor(UploadInterceptor())
      .build()

  /**
   * 创建上传请求
   *
   * @param url 上传接口
   * @param clazz 上传接口类
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
   * 创建上传请求
   *
   * @param clazz 上传接口类
   */
  fun <T> create(
    clazz: Class<T>
  ): T {
    return Retrofit.Builder()
        .baseUrl(Configuration.get().getApiUploadFile())
        .client(httpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(clazz)
  }

  /**
   * 获取默认的上传请求
   */
  fun <T> create(): T {
    return Retrofit.Builder()
        .baseUrl(Configuration.get().getApiUploadFile())
        .client(httpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Configuration.get().getServiceApi())
  }

}