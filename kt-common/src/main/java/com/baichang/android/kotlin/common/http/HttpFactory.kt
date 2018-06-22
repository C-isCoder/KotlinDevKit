package com.baichang.android.kotlin.common.http

import com.baichang.android.kotlin.common.config.Configuration
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS

/**
 * Created by iCong on 2017/6/26.
 */
object HttpFactory {
  private val httpClient = OkHttpClient.Builder()
      .connectTimeout(15, SECONDS)
      .readTimeout(15, TimeUnit.SECONDS)
      .writeTimeout(15, TimeUnit.SECONDS)
      .addInterceptor(HttpInterceptor_())
      .build()

  /**
   * 创建请求
   *
   * @param url 接口地址
   * @param clazz 接口类
   */
  fun <T> create(
    url: String,
    clazz: Class<T>
  ): T {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(httpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(HttpResultConverter_())
        .build()
        .create(clazz)
  }

  /**
   * 创建请求
   *
   * @param clazz 接口类
   */
  fun <T> create(
    clazz: Class<T>
  ): T {
    return Retrofit.Builder()
        .baseUrl(Configuration.get().getApiDefaultHost())
        .client(httpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(HttpResultConverter_())
        .build()
        .create(clazz)
  }

  /**
   * 创建请求  默认
   *
   */
  fun <T> create(): T {
    return Retrofit.Builder()
        .baseUrl(Configuration.get().getApiDefaultHost())
        .client(httpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(HttpResultConverter_())
        .build()
        .create(Configuration.get().getServiceApi())
  }

}