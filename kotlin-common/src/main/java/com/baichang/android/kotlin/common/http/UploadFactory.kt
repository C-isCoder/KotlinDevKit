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
object UploadFactory {

  private val client = OkHttpClient.Builder()
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
        .client(client)
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
        .baseUrl(Configuration<T>().get().API_UPLOAD_FILE)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(clazz)
  }

  /**
   * 创建上传请求
   *
   * @param url 上传接口
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
   * 获取默认的上传请求
   */
  fun <T> getInstance(): T {
    return Retrofit.Builder()
        .baseUrl(Configuration<T>().get().API_UPLOAD_FILE)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Configuration<T>().get().apiClazz)
  }

}