package com.baichang.android.kotlin.common.http

import com.baichang.android.library.kotlin.config.Configuration
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS

/**
 * Created by iCong on 2017/6/26.
 */
object HttpFactory {
  private val client = OkHttpClient.Builder()
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
        .client(client)
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
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(HttpResultConverter_())
        .build()
        .create(clazz)
  }

  /**
   * 创建请求
   *
   * @param url 接口地址
   */
//  fun <T> create(
//    url: String
//  ): T {
//    return Retrofit.Builder()
//        .baseUrl(url)
//        .client(client)
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//        .addConverterFactory(HttpResultConverter())
//        .build()
//        .create(Configuration<T>().get().apiClazz)
//  }

  /**
   * 获取默认的请求
   *
   */
//  fun <T> getInstance(): T {
//    return Retrofit.Builder()
//        .baseUrl(Configuration.get().getApiDefaultHost())
//        .client(client)
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//        .addConverterFactory(HttpResultConverter())
//        .build()
//        .create(Configuration<T>().get().apiClazz)
//  }
}